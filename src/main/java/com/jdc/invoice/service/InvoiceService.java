package com.jdc.invoice.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jdc.invoice.domain.entity.Customer_;
import com.jdc.invoice.domain.entity.Invoice;
import com.jdc.invoice.domain.entity.InvoiceDetail;
import com.jdc.invoice.domain.entity.InvoiceDetail_;
import com.jdc.invoice.domain.entity.Invoice_;
import com.jdc.invoice.domain.form.InvoiceEditForm;
import com.jdc.invoice.domain.repo.InvoiceDetailRepo;
import com.jdc.invoice.domain.repo.InvoiceRepo;
import com.jdc.invoice.domain.repo.UserRepo;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepo invoiceRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private InvoiceDetailRepo invoiceDetailRepo;

	public InvoiceEditForm findById(Integer id) {
		return invoiceRepo.findById(id).map(InvoiceEditForm::new).orElseThrow();
	}

	@Transactional
	public int save(InvoiceEditForm form) {
			
		var invoice = form.getInvoiceForm().getId() == 0 ? new Invoice() : invoiceRepo.findById(form.getInvoiceForm().getId()).orElseThrow();
		var loginId = SecurityContextHolder.getContext().getAuthentication().getName();
		var user = userRepo.findOneByLoginId(loginId).orElseThrow();
		
		
		invoice.setCategory(form.getInvoiceForm().getCategory());
		invoice.setDate(form.getInvoiceForm().getDate());
		invoice.setUser(user);
		invoice.setCustomer(form.getInvoiceForm().getCustomer());
		
		invoiceRepo.save(invoice);
		
		for(var formItem : form.getDetailForm()) {
			var item = formItem.getId() == 0 ? new InvoiceDetail() : invoiceDetailRepo.findById(formItem.getId()).orElseThrow();
		
			if(formItem.isDeleted()) {
				invoiceDetailRepo.delete(item);
				continue;
			}
			
			item.setBrand(formItem.getBrand());
			item.setItem(formItem.getItem());
			item.setQuantity(formItem.getQuantity());
			item.setUnitPrice(formItem.getUnitPrice());
			item.setInvoice(invoice);
			
			invoiceDetailRepo.save(item);
		}
		
		return invoice.getId();
	}

	public Page<InvoiceDetail> search(LocalDate dateFrom, LocalDate dateTo, String category, String customer, String item,
			Optional<Integer> page, Optional<Integer> size) {
		
		var pageInfo = PageRequest.of(page.orElse(0), size.orElse(8)).withSort(Sort.by("invoice.date").descending());
		
		Specification<InvoiceDetail> spec = Specification.where(null);
		
		if(null != dateFrom) {
			spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(InvoiceDetail_.invoice).get(Invoice_.date), dateFrom));
		}
		
		if(null != dateTo) {
			spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get(InvoiceDetail_.invoice).get(Invoice_.date), dateTo));
		}
		
		if(StringUtils.hasLength(category)) {
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(InvoiceDetail_.invoice).get(Invoice_.category)), category.toLowerCase().concat("%")));
		}
		
		if(StringUtils.hasLength(customer)) {
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(InvoiceDetail_.invoice).get(Invoice_.customer).get(Customer_.name)), customer.toLowerCase().concat("%")));
		}

		if(StringUtils.hasLength(item)) {
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(InvoiceDetail_.item)), item.toLowerCase().concat("%")));
		}
		
		return invoiceDetailRepo.findAll(spec, pageInfo);
	}

	@Transactional
	public void deletebyId(int id) {
		invoiceRepo.delete(invoiceRepo.findById(id).get());
	}
	
}







