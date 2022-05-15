package com.demo.stock.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	public void init();
	public String store(MultipartFile file);
	public void delete(String fileName);
}
