package com.example.demo.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Inventory;
import com.example.demo.model.Price;
import com.example.demo.model.Product;
import com.example.demo.model.ProductInfo;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService 
{
	
	@Autowired
	private  ProductRepository pr;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public ProductInfo saveProductInfo(ProductInfo p)
	{
		
		return pr.save(p);
		
	}
	
	public List<ProductInfo> getProductInfo()
	{
		return pr.findAll();
	}
	
	public Product getProductById(Integer prod_Id)
	{
		ProductInfo pi = pr.findById(prod_Id).get();
		Price price = restTemplate.getForObject("http://localhost:8082/price/" + prod_Id , Price.class);
		Inventory inventory = restTemplate.getForObject("http://localhost:8083/inventory/" + prod_Id , Inventory.class);
		return new Product(pi.getProdId() , pi.getProdName() , pi.getProdDesc() , price.getDiscPrice() , inventory.getInStock());
	}
	
	/*public List<ProductInfo> updateProductInfo(Product p)
	{
		ProductInfo p1 = pr.findById(p.getProdId()).get();
		p1.setProdName(p.getProdName());
		p1.setProdDesc(p.getProdDesc());
		p1.setProdPrice(p.getProdPrice());
		p1.setProdInStock(p.isProdInStock());
		
		pr.save(p1);
		return pr.findAll();
	}*/
	

	public List<ProductInfo> deleteProductInfo(Integer prod_Id) 
	{
	    pr.deleteById(prod_Id);
	    return pr.findAll();
	}
	

}
