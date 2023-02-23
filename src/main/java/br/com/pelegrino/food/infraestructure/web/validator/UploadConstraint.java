package br.com.pelegrino.food.infraestructure.web.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import br.com.pelegrino.food.util.FileType;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
@Constraint(validatedBy = UploadValidator.class)
public @interface UploadConstraint {

	String message() default "Arquivo Inválido";
	FileType[] accepFileTypes();
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
