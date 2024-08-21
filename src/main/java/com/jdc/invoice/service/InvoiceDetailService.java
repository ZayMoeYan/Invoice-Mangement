package com.jdc.invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jdc.invoice.domain.entity.InvoiceDetail;
import com.jdc.invoice.domain.repo.InvoiceDetailRepo;

@Service
public class InvoiceDetailService {

	@Autowired
	private InvoiceDetailRepo detailRepo;

	public InvoiceDetail findById(int id) {
		return detailRepo.findById(id).orElseThrow();
	}
	
}
