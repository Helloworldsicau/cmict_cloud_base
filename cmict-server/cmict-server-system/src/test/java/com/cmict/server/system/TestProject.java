/*
package com.cmict.server.system;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmict.server.system.feign.HornClient;
import com.cmict.server.system.feign.cmiot.CmiotAuthorization;
import com.cmict.server.system.feign.cmiot.TokenResult;
import com.cmict.server.system.feign.response.AccessToken;
import com.cmict.server.system.feign.response.HornMedia;
import com.cmict.server.system.feign.response.HornResponse;
import com.cmict.server.system.manager.VideoManager;
import com.cmict.server.system.properties.CmiotProperties;
import com.cmict.server.system.properties.ServerSystemProperties;
import com.cmiot.chinamobile.user.CmiotApplyOauthService;
import com.cmiot.chinamobile.user.CmiotAuthorizedOauthService;
import com.cmiot.chinamobile.user.oauth.util.HttpUtil;
import org.apache.http.client.HttpClient;
import org.codehaus.jackson.map.util.ISO8601Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

*/
/*
*
 * @Author: lichenxin
 * @Date: 2021/3/22
 *
 * 测试大喇叭
*//*





@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProject {

     @Autowired
      private HornClient hornClient;

     @Autowired
     private ServerSystemProperties properties;

     @Autowired
     private CmiotAuthorization cmiotAuthorization;

     @Autowired
     private VideoManager videoManager;
     //APP1   key
     String appKey ="402347c281d94ac7a2c298926e9852b4";
     //APP1   secret
     String appSecret = "7ceb0653815c4cf3af20a31693f2a9f6";
     //账户
     String account = "15108320943";
     //密码
     String pwd = "Cmict0317";
     //APP2 key
     String authorizedAppKey = "7ea413bc52a946898693f089e0b00994";

     String URL = "https://cmdlb.eastcmiot.com/openapi/horn-business-server";

     @Test
     public  void test1(){
          // 0 登录

          //1 一体化注册登录
          JSONObject jsonObject = CmiotAuthorizedOauthService.
                  outerAppLoginAndRegister( appKey,  appSecret,  account,  pwd);
          System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>"+jsonObject);
          String userToken = jsonObject.getJSONObject("data").getJSONObject("CmiotUserInfo").getString("userToken");
          String openId = jsonObject.getJSONObject("data").getJSONObject("CmiotUserInfo").getString("openId");
          String refreshToken = jsonObject.getJSONObject("data").getJSONObject("CmiotUserInfo").getString("refreshToken");
          //2 授权大喇叭应用
          JSONObject json = CmiotApplyOauthService.externalAppAuthorized(appKey, appSecret, authorizedAppKey, userToken, openId, refreshToken);
          JSONObject data = json.getJSONObject("data");
          //两个小时有效
          String oauthToken = data.getString("oauthToken");
          System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + json);
          //进行大喇叭平台鉴权
          JSONObject  paramJson = new JSONObject();
          paramJson.put("appKey",appKey);
          paramJson.put("openId",openId);
          paramJson.put("oauthToken",oauthToken);
          String s = HttpUtil.doPost("https://cmdlb.eastcmiot.com/openapi/authorize",paramJson);
          System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+s);
          JSONObject oauthObj = JSONObject.parseObject(s);
          String accessToken = oauthObj.getJSONObject("data").getString("accessToken");
          //获取媒资
          String resource = HttpRequest.get(URL + "/media/resource?type=10")
                  .header("accessToken", accessToken)
                  .execute().body();
          System.out.println(resource);

     }
     @Test
      public void test2(){
          TokenResult tokenResult = cmiotAuthorization.applyAuthorization();
          System.out.println("--------------------------------"+tokenResult);
          HornResponse<AccessToken> authorize = hornClient.authorize(tokenResult);
          System.out.println("==============================="+authorize);
          HornResponse<List<HornMedia>> mediaResource = hornClient.getMediaResource(10);
          System.out.println(mediaResource);
     }
     //查询所有监控点
     @Test
     public void test3(){
          String camerasResources = videoManager.getCamerasResources(1, 100);
          System.out.println("-----------" + camerasResources);
     }
     @Test
     public void test4(){
          String camerasUrl = videoManager.getCamerasUrl("a7a4fb18bfb54589887e07d4cc6a1f3d", "rtmp");
          System.out.println(camerasUrl);
     }
     //查询所有区域
     @Test
     public void test5(){
      videoManager.getNodesByParams("camera", 1, 100);
     }
     @Test
      public void test6(){
           String encodeDeviceList = videoManager.getEncodeDeviceList(1, 100);

      }
      @Test
      public void test8(){
          String deviceStatus = videoManager.getDeviceStatus(Arrays.asList("5a5d29c6c61944fcb521d69f1770e992"));

      }
}

*/
