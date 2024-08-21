package com.jdc.invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jdc.invoice.domain.entity.User;
import com.jdc.invoice.domain.entity.User.Role;
import com.jdc.invoice.domain.form.ChangePasswordForm;
import com.jdc.invoice.service.UserService;

@Controller
public class SecurityController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String login() {
		
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if(null != auth && auth.getAuthorities().stream().anyMatch(
				a -> a.getAuthority().equals(Role.Admin.name()) || a.getAuthority().equals(Role.Member.name()))) {
			return "redirect:/user/invoice";
		}
		
		return "login";
	}
	
	@GetMapping("sign-up")
	public String showSignUp() {
		return "sign-up";
	}
	
	@ModelAttribute("user")
	public User loadUser() {
		return new User();
	}
	
	@PostMapping
	public String signUp(@ModelAttribute(name = "user") User user) {
		userService.save(user);
		return "redirect:/";
	}
	
	@PostMapping("user/change-pass")
	public String changePass(
			@ModelAttribute ChangePasswordForm form
			) {
		userService.changePassword(form);
		return "redirect:/";
	}
}















