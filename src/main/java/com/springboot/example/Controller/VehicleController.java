package com.springboot.example.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.example.model.Vehicle;
import com.springboot.example.service.VehicleService;

@Controller
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
	// display list of vehicles
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewVehicleForm")
	public String showNewVehicleForm(Model model) {
		// create model attribute to bind form data
		Vehicle vehicle = new Vehicle();
		model.addAttribute("vehicle", vehicle);
		return "new_vehicle";
	}
	
	@PostMapping("/saveVehicle")
	public String saveVehicle(@ModelAttribute("vehicle") Vehicle vehicle) {
		// save vehicle to database
		vehicleService.saveVehicle(vehicle);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get vehicle from the service
		Vehicle vehicle = vehicleService.getVehicleById(id);
		
		// set vehicle as a model attribute to pre-populate the form
		model.addAttribute("vehicle", vehicle);
		return "update_vehicle";
	}
	
	@GetMapping("/deleteVehicle/{id}")
	public String deleteVehicle(@PathVariable (value = "id") long id) {
		
		// call delete vehicle method 
		this.vehicleService.deleteVehicleById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Vehicle> page = vehicleService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Vehicle> listVehicles = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listVehicles", listVehicles);
		return "index";
	}
}
