package com.cmict.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.cmict.entity.CmictConstant;
import com.cmict.entity.CmictResponse;
import com.cmict.gateway.properties.CmictGatewayProperties;
import com.cmict.service.RedisService;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static com.cmict.gateway.exception.ExceptionEnum.*;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 * 问题：因为是restFul风格接口 URL 没有 add del 相关资源 path只有 user 没有User/add 所以网关控制只能判断是否
 * 有user权限  可以根据post get put 添加相对应的名词以达到控制效果 但有一定局限性
 */
@Slf4j
@Component
public class GatewayRequestFilter implements GlobalFilter {

    @Autowired
    private CmictGatewayProperties properties;
    @Autowired
    private RedisService redisService;

    private AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //增删查改接口进行权限验证  暂时注释 等前端完成做详细验证
     /*       Mono<Void> checkRoleUriMono = checkRoleUri(request, response);
            if(checkRoleUriMono !=null){
                return checkRoleUriMono;
        }*/
        // 禁止客户端的访问资源逻辑
        Mono<Void> checkForbidUriResult = checkForbidUri(request, response);
        if (checkForbidUriResult != null) {
            return checkForbidUriResult;
        }
        //转发日志
        printLog(exchange);
        byte[] token = Base64Utils.encode((CmictConstant.ZUUL_TOKEN_VALUE).getBytes());
        ServerHttpRequest build = request.mutate().header(CmictConstant.ZUUL_TOKEN_HEADER, new String(token)).build();
        ServerWebExchange newExchange = exchange.mutate().request(build).build();
        return chain.filter(newExchange);
    }
    private Mono<Void> checkForbidUri(ServerHttpRequest request, ServerHttpResponse response) {
        String uri = request.getPath().toString();
        boolean shouldForward = true;
        String forbidRequestUri = properties.getForbidRequestUri();
        String[] forbidRequestUris = StringUtils.splitByWholeSeparatorPreserveAllTokens(forbidRequestUri, ",");
        if (forbidRequestUris != null && ArrayUtils.isNotEmpty(forbidRequestUris)) {
            for (String u : forbidRequestUris) {
                if (pathMatcher.match(u, uri)) {
                    shouldForward = false;
                }
            }
        }
        if (!shouldForward) {
            return makeResponse(response, CmictResponse.failed(ACCESS_DENIED));
        }
        return null;
    }

    private Mono<Void> makeResponse(ServerHttpResponse response, CmictResponse cmictResponse) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONObject.toJSONString(cmictResponse).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    private void printLog(ServerWebExchange exchange) {
        URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        LinkedHashSet<URI> uris = exchange.getAttribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        URI originUri = null;
        if (uris != null) {
            originUri = uris.stream().findFirst().orElse(null);
        }
        if (url != null && route != null && originUri != null) {
            log.info("转发请求：{}://{}{} --> 目标服务：{}，目标地址：{}://{}{}，转发时间：{}",
                    originUri.getScheme(), originUri.getAuthority(), originUri.getPath(),
                    route.getId(), url.getScheme(), url.getAuthority(), url.getPath(), LocalDateTime.now()
            );
        }
    }

    //对访问角色的资源进行判断  资源没有对应角色禁止访问
    private Mono<Void> checkRoleUri(ServerHttpRequest request, ServerHttpResponse response){
        boolean shouldCheck = true;
        Map<Object, Object> pathRole = redisService.hmget("Path_Role");
        String uri = request.getPath().toString();
        String forbidRequestUri = properties.getAllowRequestUri();
        String[] forbidRequestUris = StringUtils.splitByWholeSeparatorPreserveAllTokens(forbidRequestUri, ",");
        if (forbidRequestUris != null && ArrayUtils.isNotEmpty(forbidRequestUris)) {
            for (String u : forbidRequestUris) {
                //如果是允许访问的资源 跳过
                if (pathMatcher.match(u, uri)) {
                    shouldCheck = false;
                }
            }
            if(shouldCheck){
                //进行资源验证
                List<Integer> roles = (List<Integer>) pathRole.get(uri);
               if(roles == null ){
                   return makeResponse(response, CmictResponse.failed(NO_PERMISSION));
               }
                String token = request.getHeaders().getFirst("Authorization");
                String realToken = token.replace("Bearer", "");
                try {
                    JWSObject jwsObject = JWSObject.parse(realToken);
                    String role = jwsObject.getPayload().toJSONObject().getAsString("role");
                    if(!roles.contains(Integer.valueOf(role))){
                        return makeResponse(response, CmictResponse.failed(NO_PERMISSION));
                    }
                } catch (ParseException e) {
                    return makeResponse(response, CmictResponse.failed(TOKEN_ERROR));
                }
            }
        }
        return null;
    }
}
