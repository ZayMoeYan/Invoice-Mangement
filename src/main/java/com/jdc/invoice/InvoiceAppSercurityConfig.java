package com.jdc.invoice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jdc.invoice.domain.entity.User.Role;

@Configuration
@EnableWebSecurity
public class InvoiceAppSercurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/"));
		http.logout(form -> form.logoutUrl("/logout").logoutSuccessUrl("/") );
		
		http.authorizeHttpRequests(req -> {
			req.requestMatchers("/", "/login", "/sign-up").permitAll();
			req.requestMatchers("/user/**").hasAnyAuthority(Role.Admin.name(), Role.Member.name());
			req.requestMatchers("/admin/**").hasAnyAuthority(Role.Admin.name());
			req.anyRequest().authenticated();
		});

        http.exceptionHandling(handling -> handling.accessDeniedPage("/denied"));
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
