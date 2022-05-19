package com.cmict.selector;

import com.cmict.configure.CmictAuthExceptionConfigure;
import com.cmict.configure.LettuceRedisConfigure;
import com.cmict.configure.OAuth2FeignConfigure;
import com.cmict.configure.ServerProtectConfigure;
import com.cmict.untils.SpringContextUtil;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
public class CloudApplicationSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                CmictAuthExceptionConfigure.class.getName(),
                OAuth2FeignConfigure.class.getName(),
                ServerProtectConfigure.class.getName(),
                LettuceRedisConfigure.class.getName(),
                SpringContextUtil.class.getName()
        };
    }
}
