package com.springboot.example.service;

import java.util.List;

import org.springframework.data.domain.Page;

import  com.springboot.example.model.Vehicle;

public interface VehicleService {
	List<Vehicle> getAllVehicles();
	void saveVehicle(Vehicle vehicle);
	Vehicle getVehicleById(long id);
	void deleteVehicleById(long id);
	Page<Vehicle> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
