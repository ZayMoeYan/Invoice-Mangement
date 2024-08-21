package com.jdc.invoice.domain.repo;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.jdc.invoice.domain.entity.Invoice;

public interface InvoiceRepo extends JpaRepositoryImplementation<Invoice, Integer>{

}
