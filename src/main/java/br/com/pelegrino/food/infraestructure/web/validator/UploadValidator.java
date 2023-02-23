package br.com.pelegrino.food.infraestructure.web.validator;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.com.pelegrino.food.util.FileType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UploadValidator implements ConstraintValidator<UploadConstraint, MultipartFile> {

	private List<FileType> acceptedFileTypes;
	
	@Override
	public void initialize(UploadConstraint constraintAnnotation) {
		acceptedFileTypes = Arrays.asList(constraintAnnotation.accepFileTypes());
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		if (multipartFile == null) {
			return true;
		}

		for (FileType fileType : acceptedFileTypes) {
			if (fileType.sameof(multipartFile.getContentType())) {
				return true;
			}
		}
		
		return false;
	}
}
