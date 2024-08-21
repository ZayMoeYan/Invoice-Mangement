package com.jdc.invoice.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.invoice.domain.entity.Customer;
import com.jdc.invoice.domain.entity.Customer.Level;
import com.jdc.invoice.service.CustomerService;

@Controller
@RequestMapping("user/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@GetMapping
	public String list(
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String phone,
			@RequestParam(required = false) String keyword,
			ModelMap model
			) {
		var customerList = customerService.search(dateFrom, dateTo, name, phone, keyword);
		model.put("customerList", customerList);
		
		return "customer-list";
	}
	
	@GetMapping("edit")
	public String edit() {
		return "customer-edit";
	}
	
	@PostMapping
	public String save(@ModelAttribute Customer customer) {
		customerService.save(customer);
		return "redirect:/user/customer";
	}
	
	@ModelAttribute(name = "customer")
	Customer customer() {
		return new Customer();
	}
	
	@ModelAttribute(name = "customerLevels")
	Level[] loadLevel() {
		return Level.values();
	}
}
