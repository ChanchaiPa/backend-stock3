package com.demo.stock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.stock.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	Optional<Product> findByPname(String name);
	List<Product> findByPnameContaining(@Param("name") String name);  //***** @Param("name") for fix bux hibernate
	List<Product> findByStockGreaterThanEqual(int stock);
	
	@Query("select p from Product p where p.price>= :price")  //JPQL, varchar compare not use ''
	//@Query(value = "select * from product where price>= :price", nativeQuery = true) //SQL
	List<Product> queryByPrice(int price);
}
