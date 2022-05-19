package com.cmict.activiti.feign.fallback;

import com.cmict.activiti.feign.UserClient;
import com.cmict.entity.CmictResponse;
import com.cmict.entity.system.ProvineUser;
import com.cmict.entity.system.SystemUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lichenxin
 * @Date: 2021/8/4
 */
@Component
@Slf4j
public class UserClientFallBack implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {

        return new UserClient() {
            @Override
            public CmictResponse getUserDetail(String username) {
               log.error(throwable.getMessage());
                return CmictResponse.failed(throwable.getMessage());
            }

            @Override
            public SystemUser getSystemUser(String username) {
                return new SystemUser();
            }

            @Override
            public CmictResponse getUserLeaders(long deptId) {
                log.error(throwable.getMessage());
                return CmictResponse.failed(throwable.getMessage());
            }

            @Override
            public List<ProvineUser> getUserByRoleId(long roleId) {
                return new ArrayList<ProvineUser>();
            }

            @Override
            public List<ProvineUser> getProvinceUser(String users) {
                return new ArrayList<ProvineUser>();
            }

            @Override
            public List<String> getUserRole(String userId) {
                return new ArrayList<>();
            }
        };
    }
}
