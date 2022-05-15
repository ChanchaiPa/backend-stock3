package com.demo.stock.service;

import java.util.List;

import com.demo.stock.controller.request.ProductRequest;
import com.demo.stock.model.Product;

public interface ProductService {
	List<Product> getAllProduct();
	Product getProductByID(int id);
	Product addProduct(ProductRequest productReq);
	Product updateProduct(ProductRequest productReq);
	void deleteProduct(int id);
	
	
	Product getProductByName(String name);
	List<Product> searchProductByName(String name);
	List<Product> getProductByStock(int stock);
	List<Product> queryProductbyPrice(int price);
}
