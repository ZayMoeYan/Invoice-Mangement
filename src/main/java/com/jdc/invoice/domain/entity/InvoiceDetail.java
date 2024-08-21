package com.jdc.invoice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "INVOICE_DETAIL")
@SequenceGenerator(name = "INVOICE_DETAIL_SEQ", allocationSize = 1)
public class InvoiceDetail {

	@Id
	@GeneratedValue(generator = "INVOICE_DETAIL_SEQ", strategy = GenerationType.SEQUENCE)
	private int id;
	@Column(nullable = false)
	private String item;
	@Column(nullable = false)
	private int quantity;
	@Column(nullable = false)
	private int unitPrice;
	@Column(nullable = false)
	private String brand;

	@ManyToOne(optional = true)
	private Invoice invoice;

	public int getTotal() {
		return unitPrice * quantity;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
