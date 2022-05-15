package com.demo.stock.service;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.demo.stock.exception.StorageException;

@Service
public class StorageServiceImpl implements StorageService {
	@Value("${app.upload.path}")
	private String path;
	
	private Path rootLocation;
	
	@Override
	public void init() {
		this.rootLocation = Paths.get(path);
		try {
			Files.createDirectory(rootLocation);
			System.out.println("StorageService int complete");
		}
		catch(FileAlreadyExistsException e) {
			//
		}
		catch(Exception e) {
			throw new StorageException("Can not init storage... " + e.getMessage());
		}
	}

	@Override
	public String store(MultipartFile file) {
		if (file==null || file.isEmpty())
			return null;
		
		String rawFileName = StringUtils.cleanPath(file.getOriginalFilename());
		String newFileName = UUID.randomUUID() + "." + rawFileName.substring(rawFileName.lastIndexOf(".")+1);
		try {
			Files.copy(file.getInputStream(), 
					this.rootLocation.resolve(newFileName), 
					StandardCopyOption.REPLACE_EXISTING);
		}
		catch(Exception e) {
			throw new StorageException("Can not write storage... " + e.getMessage());
		}
		return newFileName;
	}

	@Override
	public void delete(String fileName) {
		//Path deletePath = Paths.get(path + "/" + fileName);
		Path deletePath = FileSystems.getDefault().getPath(path, fileName);
		try { Files.deleteIfExists(deletePath); }
		catch(Exception e) { e.printStackTrace(); }
	}

}
