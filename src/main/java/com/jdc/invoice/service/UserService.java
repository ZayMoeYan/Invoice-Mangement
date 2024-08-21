package com.jdc.invoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jdc.invoice.domain.entity.User;
import com.jdc.invoice.domain.entity.User.Role;
import com.jdc.invoice.domain.form.ChangePasswordForm;
import com.jdc.invoice.domain.entity.User_;
import com.jdc.invoice.domain.repo.UserRepo;
import com.jdc.invoice.utils.InvoiceAppException;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));;
		userRepo.save(user);
	}

	public List<User> findAllMember() {
		return userRepo.findAllByRoleMember();
	}

	public List<User> search(Role role, String name, String phone, String email) {
		
		Specification<User> spec = Specification.where(null);
		
		if(null != role) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get(User_.role), role));
		}
		
		if(StringUtils.hasLength(name)) {
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(User_.name)), name.toLowerCase().concat("%")));
		}
		
		if(StringUtils.hasLength(phone)) {
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(User_.phone)), phone.toLowerCase().concat("%")));
		}

		if(StringUtils.hasLength(email)) {
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(User_.email)), email.toLowerCase().concat("%")));
		}
		
		return userRepo.findAll(spec);
	}

	@Transactional
	public void changeStatus(int id) {
		var user = userRepo.findById(id).orElseThrow();
		if(user.isStatus() == true) {
			user.setStatus(false);
		} else {
			user.setStatus(true);
		}
	}

	@Transactional
	public void changePassword(ChangePasswordForm form) {
		
		if(!StringUtils.hasLength(form.getOldPassword())) {
			throw new InvoiceAppException("Enter Old Password");
		}
		
		if(!StringUtils.hasLength(form.getOldPassword())) {
			throw new InvoiceAppException("Enter New Password");
		}
		
		if(form.getNewPassword().equals(form.getOldPassword())) {
			throw new InvoiceAppException("New Password must be different");
		}
		
		var user = userRepo.findOneByLoginId(form.getLoginId()).orElseThrow();
		
		if(!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
			throw new InvoiceAppException("Check Your Old Password");
		}
		
		user.setPassword(passwordEncoder.encode(form.getNewPassword()));
		
	}
	
	

}










