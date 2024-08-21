package com.jdc.invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.invoice.domain.entity.User.Role;
import com.jdc.invoice.service.UserService;

@Controller
@RequestMapping("admin/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping
	public String users(
			@RequestParam(required = false) Role role,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String phone,
			@RequestParam(required = false) String email,
			ModelMap model
			) {
		var userList = userService.search(role, name, phone, email);
		model.put("userList", userList);
		return "user-list";
	}
	
	@ModelAttribute(name = "roles")
	Role[] loadRole() {
		return Role.values();
	}
	
	@GetMapping("change")
	public String change(@RequestParam int id) {
		userService.changeStatus(id);
		return "redirect:/admin/users";
	}
}
