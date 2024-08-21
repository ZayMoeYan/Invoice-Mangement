package com.jdc.invoice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.invoice.domain.entity.User;
import com.jdc.invoice.domain.entity.User.Role;
import com.jdc.invoice.domain.repo.UserRepo;

@Component
public class UserInitializer {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Transactional
	@EventListener(classes = ContextRefreshedEvent.class)
	public void initializerAdmin() {
		var user = new User();
		if(userRepo.count() == 0) {
			user.setName("Admin");
			user.setLoginId("admin");
			user.setPassword(passwordEncoder.encode("admin"));
			user.setEmail("admin@gmail.com");
			user.setPhone("09-754936740");
			user.setRole(Role.Admin);
			user.setStatus(true);
			userRepo.save(user);
		}
	}
	
}
