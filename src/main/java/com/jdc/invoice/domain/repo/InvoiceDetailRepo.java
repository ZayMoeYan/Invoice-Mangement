package com.jdc.invoice.domain.repo;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.jdc.invoice.domain.entity.InvoiceDetail;

public interface InvoiceDetailRepo extends JpaRepositoryImplementation<InvoiceDetail, Integer>{

}
