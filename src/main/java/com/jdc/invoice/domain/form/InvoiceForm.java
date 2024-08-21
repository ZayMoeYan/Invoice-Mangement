package com.jdc.invoice.domain.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.jdc.invoice.domain.entity.Customer;
import com.jdc.invoice.domain.entity.User;

public class InvoiceForm {

	private int id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	private String category;
	private User user;
	private Customer customer;

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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
