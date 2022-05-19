package com.cmict.auth;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.text.ParseException;

/**
 * @Author: lichenxin
 * @Date: 2021/2/20
 */
public class Test {

    public static void main(String[] args) throws ParseException {
        String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiMSIsInVzZXJfbmFtZSI6ImNtaWN0Iiwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTYxMzg5MzY4NCwiYXV0aG9yaXRpZXMiOlsidXNlcjp2aWV3IiwidXNlcjp1cGRhdGUiLCJ1c2VyOmFkZCIsInVzZXI6ZGVsZXRlIl0sImp0aSI6IjY3YTFhZGI1LTBjOWYtNGIxMS04YTgyLWYxOGM3NjY4MTcyNiIsImNsaWVudF9pZCI6ImNtaWN0In0.81maosKm_s-CdF9AiG0joJfNK_PYMjsY6hFz2h-7Pis";
        SignedJWT sjwt = SignedJWT.parse(token);
        String s = sjwt.getPayload().toString();
        System.out.println(s);


    }
}
