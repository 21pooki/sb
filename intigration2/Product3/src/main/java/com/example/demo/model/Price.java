package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price 
{
	@Id
	private Integer priceId;
	private Integer prodId;
	
	private Integer originalPrice;
	private Integer discPrice;
}
