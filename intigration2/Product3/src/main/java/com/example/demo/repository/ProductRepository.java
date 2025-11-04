package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Product;
import com.example.demo.model.ProductInfo;


public interface ProductRepository  extends JpaRepository<ProductInfo , Integer>
{

}
