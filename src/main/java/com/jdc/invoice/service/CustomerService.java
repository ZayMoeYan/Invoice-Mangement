package com.jdc.invoice.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jdc.invoice.domain.entity.Address;
import com.jdc.invoice.domain.entity.Address_;
import com.jdc.invoice.domain.entity.Customer;
import com.jdc.invoice.domain.entity.Customer_;
import com.jdc.invoice.domain.entity.Invoice;
import com.jdc.invoice.domain.entity.Invoice_;
import com.jdc.invoice.domain.repo.CustomerRepo;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Transactional
	public void save(Customer cus) {
		var customer = new Customer(cus);
		var address = new Address(cus);
		address.setCustomer(customer);
		customer.setAddress(address);
		customerRepo.save(customer);
	}

	public List<Customer> search(LocalDate dateFrom, LocalDate dateTo, String name, String phone, String keyword) {
		
		Specification<Customer> spec = Specification.where(null);
		
		if(null != dateFrom) {
			spec = spec.and((root, query, cb) -> {
				Join<Customer, Invoice> invoiceJoin = root.join(Customer_.invoices, JoinType.INNER);
				return cb.greaterThanOrEqualTo(invoiceJoin.get(Invoice_.date), dateFrom);
			});			
		}
		
		if(null != dateTo) {
			spec = spec.and((root, query, cb) -> {
				Join<Customer, Invoice> invoiceJoin = root.join(Customer_.invoices, JoinType.INNER);
				return cb.lessThanOrEqualTo(invoiceJoin.get(Invoice_.date), dateTo);
			});
		}
		
		if(StringUtils.hasLength(name)) {
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(Customer_.name)), name.toLowerCase().concat("%")));
		}
		
		if(StringUtils.hasLength(phone)) {
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(Customer_.name)), name.toLowerCase().concat("%")));
		}
		
		if(StringUtils.hasLength(keyword)) {
			spec = spec.and((root, query, cb) -> {
				
				var townshipLike = cb.like(cb.lower(root.get(Customer_.address).get(Address_.township)), keyword.toLowerCase().concat("%"));
				
				var cityLike = cb.like(cb.lower(root.get(Customer_.address).get(Address_.city)), keyword.toLowerCase().concat("%"));
				
				return cb.or(townshipLike, cityLike);
			});
		}
		
		return customerRepo.findAll(spec);
	}

	public List<Customer> findAll() {	
		return customerRepo.findAll();
	}

	public Customer findById(int id) {
		return customerRepo.findById(id).orElseThrow();
	}
}
















