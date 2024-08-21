package com.jdc.invoice.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "INVOICE")
@SequenceGenerator(name = "INVOICE_SEQ", allocationSize = 1)
public class Invoice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "INVOICE_SEQ", strategy = GenerationType.SEQUENCE)
	private int id;
	@Column(nullable = false)
	private String category;
	@Column(nullable = false)
	private LocalDate date;

	@ManyToOne(optional = true)
	private Customer customer;

	@ManyToOne(optional = true)
	private User user;

	@OneToMany(mappedBy = "invoice", orphanRemoval = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<InvoiceDetail> invoiceDetails;

	public Invoice() {
		invoiceDetails = new ArrayList<InvoiceDetail>();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<InvoiceDetail> getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}
	
	public String getMessage() {
		return "No Invoice ";
	}

}
