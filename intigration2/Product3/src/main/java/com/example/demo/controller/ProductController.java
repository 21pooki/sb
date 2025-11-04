package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Product;
import com.example.demo.model.ProductInfo;
import com.example.demo.service.ProductService;

@RestController
public class ProductController 
{
	@Autowired
	ProductService ps;
	
	/*@getMapping("/products")
	public */
	@PostMapping("/products/add")
	public ProductInfo saveProductInfo(@RequestBody ProductInfo p)
	{
		return ps.saveProductInfo(p);
	}
	
	@GetMapping("/products")
	public List<ProductInfo> getProductInfo()
	{
		return ps.getProductInfo();
	}
	
	@GetMapping("/products/{prodId}")
	public Product getProductById(@PathVariable Integer prodId)
	{
		return ps.getProductById(prodId);
	}
	
	/*@PutMapping("/products/update")
	public List<ProductInfo> updateProductInfo(@RequestBody Product p)
	{
		return ps.updateProductInfo(p);
	}*/
	
	@DeleteMapping("/products/delete/{prodId}")
	public List<ProductInfo> deleteProductInfo(@PathVariable Integer prodId)
	{
		return ps.deleteProductInfo(prodId);
	}
}
