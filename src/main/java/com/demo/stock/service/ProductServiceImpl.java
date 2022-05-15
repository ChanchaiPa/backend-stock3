package com.demo.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.stock.controller.request.ProductRequest;
import com.demo.stock.exception.ProductNotFoundException;
import com.demo.stock.model.Product;
import com.demo.stock.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private StorageService storageService;	
	
	@Autowired
	private ProductRepository productRepo;

	
	
	@Override
	public List<Product> getAllProduct() {
		return productRepo.findAll(Sort.by(Sort.Direction.ASC, "pid"));
	}

	@Override
	public Product getProductByID(int id) {
		Product product = productRepo.findById(id).orElse(null);
		if (product == null)
			throw new ProductNotFoundException("ID " + id + " not found");
		else
			return product;
	}

	
	
	@Override
	public Product addProduct(ProductRequest productReq) {
		String fileName = storageService.store(productReq.getFile());
		Product product = new Product();
		//product.setId   ( productReq.getCode() );
		product.setPname ( productReq.getName() );
		product.setImage( fileName );
		product.setPrice( productReq.getPrice() );
		product.setStock( productReq.getStock() );
		return productRepo.save(product);
	}

	
	
	@Override
	public Product updateProduct(ProductRequest productReq) {
		String fileName = "";
		if (productReq.getFile()!=null && !productReq.getFile().isEmpty()) {
			fileName = storageService.store(productReq.getFile());
		}
		
		int code = productReq.getCode();
		Product product = productRepo.findById( code ).orElseThrow( ()->new ProductNotFoundException("ID " + code + " not found") );
		product.setPname ( productReq.getName() );
		if(!fileName.equals(""))
			product.setImage( fileName );
		product.setPrice( productReq.getPrice() );
		product.setStock( productReq.getStock() );
		return productRepo.save(product);
	}

	
	
	@Override
	public void deleteProduct(int id) {
		try {
			Product product = productRepo.findById(id).orElse(null);
			if (product != null && product.getImage().length()>0)
				storageService.delete(product.getImage());
			productRepo.deleteById(id);
		}
		catch(Exception e) {
			throw new ProductNotFoundException("ID " + id + " not found");
		}	
	}



	@Override
	public Product getProductByName(String name) {
		return productRepo.findByPname(name).orElseThrow( ()->new ProductNotFoundException("Name " + name + " not found") );			
	}

	@Override
	public List<Product> getProductByStock(int stock) {
		return productRepo.findByStockGreaterThanEqual(stock);			
	}

	@Override
	public List<Product> searchProductByName(String name) {
		return productRepo.findByPnameContaining(name);
	}

	@Override
	public List<Product> queryProductbyPrice(int price) {
		return productRepo.queryByPrice(price);
	}	

	
}
