package com.cmict.auth.configure.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @Author: lichenxin
 * @Date: 2021/5/26
 * 存储手机和密码
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    //账号
    private final Object principal;
    //手机验证码
    private Object credentials;
    //验证码key
    private String key;
    //验证码
    private String code;

    public MobileAuthenticationToken(String mobile,String mobileCode,String key,String code) {
        super(null);
        this.principal = mobile;
        this.credentials = mobileCode;
        this.key = key;
        this.code = code;
        setAuthenticated(false);
    }
    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    public MobileAuthenticationToken(Object principal, Object credentials,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }
    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return code;
    }
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
