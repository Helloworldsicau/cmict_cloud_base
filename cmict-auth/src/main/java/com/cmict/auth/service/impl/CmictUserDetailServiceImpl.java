package com.cmict.auth.service.impl;

import com.cmict.auth.manager.UserManager;
import com.cmict.auth.service.CmictUserDetailService;
import com.cmict.entity.CmictAuthUser;
import com.cmict.entity.CurrentUser;
import com.cmict.entity.system.SystemUser;
import com.cmict.exception.CmictException;
import com.cmict.untils.CmictUtil;
import com.cmict.untils.DateUtil;
import com.cmict.untils.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;

import static com.cmict.auth.exception.ExceptionEnum.*;

/**
 * @Author: lichenxin
 * @Date: 2021/2/15
 */
@Service
public class CmictUserDetailServiceImpl implements CmictUserDetailService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserManager userManager;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = userManager.findByName(username);
        if (systemUser != null) {
            //用户首次登录强制修改密码
            Date lastLoginTime = systemUser.getLastLoginTime();
            boolean matches = passwordEncoder.matches(SystemUser.DEFAULT_PASSWORD, systemUser.getPassword());
            if (lastLoginTime == null && matches) {
                systemUser.setStatus(String.valueOf(PASSWORD_UN_UPDATE.getCode()));
            } else {
                systemUser.setStatus(String.valueOf(PASSWORD_UPDATE.getCode()));
            }
            String permissions = userManager.findUserPermissions(systemUser.getUsername());
            CmictAuthUser authUser = new CmictAuthUser(systemUser.getUsername(), systemUser.getPassword(), true, true, true, true,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));

            BeanUtils.copyProperties(systemUser, authUser);
            //清楚用户缓存，防止用户修改完密码，标识还是未修改
            RedisTokenStore redisTokenStore = SpringContextUtil.getBean(RedisTokenStore.class);
            Collection<OAuth2AccessToken> userToken = redisTokenStore.findTokensByClientIdAndUserName("cmict", systemUser.getUsername());
            for (OAuth2AccessToken oAuth2AccessToken : userToken) {
                String value = oAuth2AccessToken.getValue();
                OAuth2RefreshToken refreshToken = oAuth2AccessToken.getRefreshToken();
                redisTokenStore.removeAccessToken(value);
                redisTokenStore.removeRefreshToken(refreshToken);
            }
            return authUser;
        } else {
            throw new CmictException(USERNAME_PASSWORD_ERROR);
        }
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) {
        SystemUser systemUser = userManager.findByMobile(mobile);
        if (systemUser != null) {
            String permissions = userManager.findUserPermissions(systemUser.getUsername());
            boolean notLocked = false;
            if (StringUtils.equals(SystemUser.STATUS_VALID, systemUser.getStatus()))
                notLocked = true;
            CmictAuthUser authUser = new CmictAuthUser(systemUser.getUsername(), systemUser.getPassword(), true, true, true, notLocked,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));

            BeanUtils.copyProperties(systemUser, authUser);
            return authUser;
        } else {
            throw new CmictException(PHONE_ERROR);
        }
    }
}
