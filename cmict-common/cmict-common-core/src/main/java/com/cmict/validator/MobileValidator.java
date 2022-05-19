package com.cmict.validator;

import com.cmict.annotation.IsMobile;
import com.cmict.entity.RegexpConstant;
import com.cmict.untils.CmictUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: lichenxin
 * @Date: 2021/2/17
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return CmictUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
