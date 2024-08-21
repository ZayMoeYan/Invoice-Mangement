package com.jdc.invoice.domain.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.jdc.invoice.domain.entity.User;

public interface UserRepo extends JpaRepositoryImplementation<User, Integer>{

	Optional<User> findOneByLoginId(String loginId);
	
	@Query(value = "select u from User u where u.role = 'Member'")
	List<User> findAllByRoleMember();
	
}
