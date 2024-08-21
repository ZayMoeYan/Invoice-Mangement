package com.jdc.invoice.domain.repo;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.jdc.invoice.domain.entity.Customer;

public interface CustomerRepo extends JpaRepositoryImplementation<Customer, Integer>{

}
