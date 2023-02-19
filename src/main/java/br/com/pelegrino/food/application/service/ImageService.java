package br.com.pelegrino.food.application.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.pelegrino.food.util.IOUtils;

@Service
public class ImageService {
	
	@Value("${food.files.logotipo}")
	private String logotiposDir;
	
	public void uploadLogotipo(MultipartFile multipartFile, String fileName) {
		try {
			IOUtils.copy(multipartFile.getInputStream(), fileName, logotiposDir);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		
		}
	}
}
