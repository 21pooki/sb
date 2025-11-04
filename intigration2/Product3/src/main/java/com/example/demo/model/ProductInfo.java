package com.example.demo.model;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
//@Table(name = "Product1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo 
{
	@Id
	private Integer prodId;
	private String prodName;
	private String prodDesc;
}


