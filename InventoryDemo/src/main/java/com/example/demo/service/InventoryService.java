package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Inventory;
import com.example.demo.repository.InventoryRepository;

@Service
public class InventoryService {
	@Autowired
	private InventoryRepository  invRepo;

	public List<Inventory> getAllInventoryDetails() {
		// TODO Auto-generated method stub
		return invRepo.findAll();
	}

	public Inventory saveInventoryInfo(Inventory i) {
		// TODO Auto-generated method stub
		return invRepo.save(i);
	}

	public Inventory getInventoryInfo(Integer invId) {
		// TODO Auto-generated method stub
		return invRepo.findById(invId).get();
	}

	public Inventory updateInventoryInfo(Inventory i) {
		// TODO Auto-generated method stub
		return invRepo.save(i);
	}

	public Inventory deleteInventoryInfo(Integer invId) {
		// TODO Auto-generated method stub
		Optional<Inventory> d = invRepo.findById(invId);
		invRepo.deleteById(invId);
		
		return d.get();
	}
	

}
