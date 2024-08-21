package com.jdc.invoice.domain.form;

public class InvoiceDetailForm {

	private int id;
	private String item;
	private String brand;
	private int unitPrice;
	private int quantity;
	private boolean deleted;

	public boolean isDeleted() {
		return deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDeleted(boolean deleted) {

		this.deleted = deleted;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
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

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
