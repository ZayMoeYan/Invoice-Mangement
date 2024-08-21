package com.jdc.invoice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jdc.invoice.domain.repo.UserRepo;

@Service
public class UserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		return repo.findOneByLoginId(username)
				.map(user -> User.withUsername(username)
				.password(user.getPassword())
				.authorities(AuthorityUtils.createAuthorityList(user.getRole().name()))
				.disabled(!user.isStatus())
				.accountExpired(!user.isStatus())
				.build()).orElseThrow(() -> new UsernameNotFoundException("There is no account with loginId %s".formatted(username)));
	}

}
