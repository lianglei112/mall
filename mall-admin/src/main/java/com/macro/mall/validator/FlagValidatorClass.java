package com.macro.mall.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * @author lianglei
 * @version 1.0
 * @date 2023/6/21 22:17
 * @deprecated 状态约束校验器
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator, Integer> {

    private String[] values;


    @Override
    public void initialize(FlagValidator constraintAnnotation) {
        this.values = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        boolean isValid = false;
        if (value == null) {
            //当状态为空时使用默认值
            return true;
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(String.valueOf(value))) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}
