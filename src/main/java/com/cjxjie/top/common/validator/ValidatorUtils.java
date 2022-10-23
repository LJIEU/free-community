package com.cjxjie.top.common.validator;

import com.cjxjie.top.common.exception.CustomizeException;
import com.cjxjie.top.common.utils.Constant;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 *
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author Mark sunlightcs@gmail.com
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws CustomizeException  校验不通过，异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws CustomizeException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new CustomizeException(msg.toString());
        }
    }

    public static void validateEntity(Object object, Constant.CloudService type) {
        validateEntity(object, type.getValidatorGroupClass());
    }
}
