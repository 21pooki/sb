package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Inventory;
//import com.example.demo.model.Product;
import com.example.demo.service.InventoryService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class InventoryController {
	@Autowired
	InventoryService is;
	
	@GetMapping("/inventory")
	public List<Inventory> getAllInventoryDetails()
	{
		return is.getAllInventoryDetails();
	}
	

	@GetMapping("/inventory/{invId}")
	public Inventory getInventoryInfo(@PathVariable Integer invId)
	{
		return is.getInventoryInfo(invId);
	}
	
	@DeleteMapping("/inventory/delete/{invId}")
	public Inventory deleteInventoryInfo(@PathVariable Integer invId)
	{
		return is.deleteInventoryInfo(invId);
	}
	
	
	@PostMapping("/inventory/add")
	public Inventory saveInventoryInfo(@RequestBody Inventory i)
	{
		return is.saveInventoryInfo(i);
	}
	
	@PutMapping("inventory/update")
	public Inventory updateInventoryInfo(@RequestBody Inventory i)
	{
		return is.updateInventoryInfo(i);
	}

}


