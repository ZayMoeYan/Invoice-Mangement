package com.jdc.invoice.domain.form;

import java.util.ArrayList;
import java.util.List;

import com.jdc.invoice.domain.entity.Invoice;

public class InvoiceEditForm {

	private InvoiceForm invoiceForm;
	private List<InvoiceDetailForm> detailForm;
	
	
	public InvoiceEditForm(Invoice entity) {
		invoiceForm = new InvoiceForm();
		invoiceForm.setId(entity.getId());
		invoiceForm.setCategory(entity.getCategory());
		invoiceForm.setDate(entity.getDate());
		invoiceForm.setCustomer(entity.getCustomer());
		invoiceForm.setUser(entity.getUser());
		
		detailForm = new ArrayList<InvoiceDetailForm>(
				entity.getInvoiceDetails().stream().map(a -> {
					var item = new InvoiceDetailForm();
					item.setBrand(a.getBrand());
					item.setId(a.getId());
					item.setItem(a.getItem());
					item.setQuantity(a.getQuantity());
					item.setUnitPrice(a.getUnitPrice());
					return item;
				}).toList());
	}
	
	public InvoiceEditForm() {
		invoiceForm = new InvoiceForm();
		detailForm = new ArrayList<>();
	}
	
	public int getTotal() {
		return detailForm.stream().mapToInt(item -> item.getUnitPrice() * item.getQuantity()).sum();
	}
	
	public int getTotalQuantity() {
		return detailForm.stream().mapToInt(item -> item.getQuantity()).sum();
	}

	public InvoiceForm getInvoiceForm() {
		return invoiceForm;
	}

	public void setInvoiceForm(InvoiceForm invoiceForm) {
		this.invoiceForm = invoiceForm;
	}

	public List<InvoiceDetailForm> getDetailForm() {
		return detailForm;
	}

	public void setDetailForm(List<InvoiceDetailForm> detailForm) {
		this.detailForm = detailForm;
	}

	public String queryParam() {
		return invoiceForm.getId() == 0 ? "" : "id=%s".formatted(invoiceForm.getId());
	}

	public void clear() {
		invoiceForm = new InvoiceForm();
		detailForm.clear();
	}
	
	public List<InvoiceDetailForm> getValidItems() {
		return detailForm.stream().filter(item -> !item.isDeleted()).toList();
	}

	

}
