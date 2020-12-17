package by.itechart.retailers.annotation;

import by.itechart.retailers.annotation.validator.UpcValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UpcValidator.class)
public @interface Upc {
    String message() default "UPC does not match the pattern";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}