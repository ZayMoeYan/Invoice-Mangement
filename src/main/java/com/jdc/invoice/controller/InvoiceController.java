package com.jdc.invoice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jdc.invoice.domain.entity.Customer;
import com.jdc.invoice.domain.form.InvoiceDetailForm;
import com.jdc.invoice.domain.form.InvoiceEditForm;
import com.jdc.invoice.domain.form.InvoiceForm;
import com.jdc.invoice.service.CustomerService;
import com.jdc.invoice.service.InvoiceService;
import com.jdc.invoice.utils.Pagination;

@Controller
@RequestMapping("user/invoice")
@SessionAttributes("invoiceEditForm")
public class InvoiceController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping
	public String index(
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String customer,
			@RequestParam(required = false) String item,
			Optional<Integer> page,
			Optional<Integer> size,
			ModelMap model
			) {
		var invoiceDetailPage = invoiceService.search(dateFrom, dateTo, category, customer, item, page, size);
		var params = new HashMap<String, String>();
		params.put("dateFrom", null == dateFrom ? "" :dateFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		params.put("dateTo", null == dateTo ? "" : dateTo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		params.put("category", null == category ? "" : category);
		params.put("category", null == customer ? "" : customer);
		params.put("category", null == item ? "" : item);
		
		var pager = Pagination.builder()
				.url("/user/invoice")
				.page(invoiceDetailPage)
				.params(params)
				.build();
		
		model.put("pager", pager);
		model.put("invoiceDetailList", invoiceDetailPage.getContent());
		return "invoice-list";
	}
	

	@ModelAttribute(name = "invoiceEditForm")
	InvoiceEditForm invoiceEditForm(@RequestParam(required = false) Integer id) {
		if(null != id) {
			return invoiceService.findById(id);
		}
		
		return new InvoiceEditForm();
	}
	
	@GetMapping("edit")
	public String edit(
			@ModelAttribute(name = "invoiceEditForm") InvoiceEditForm form,
			@RequestParam(required = false) Integer id
			) {
		
		if(null != id && form.getInvoiceForm().getId() != id) {
			var result = invoiceService.findById(id);
			form.setInvoiceForm(result.getInvoiceForm());
			form.setDetailForm(result.getDetailForm());	
		}
		
		return "invoice-edit";
	}
	
	@ModelAttribute(name = "invoiceDetailForm")
	InvoiceDetailForm invoiceDetailForm() {
		return new InvoiceDetailForm();
	}
	
	@PostMapping("addItem")
	public String addItem(
			@ModelAttribute(name = "invoiceEditForm") InvoiceEditForm form,
			@ModelAttribute(name = "invoiceDetailForm") InvoiceDetailForm detailForm) {
		
		form.getDetailForm().add(detailForm);
		
		return "redirect:/user/invoice/edit?%s".formatted(form.queryParam());
	}
	
	@GetMapping("remove")
	public String deleteItem(
			@ModelAttribute(name = "invoiceEditForm") InvoiceEditForm form,
			@RequestParam int index
			) {
		
		var item = form.getDetailForm().get(index);
		
		if(item.getId() == 0) {
			form.getDetailForm().remove(index);
		}else {
			item.setDeleted(true);
		}
		
		return "redirect:/user/invoice/edit?%s".formatted(form.queryParam());
	}
	
	@ModelAttribute(name = "invoiceForm")
	InvoiceForm invoiceForm(@ModelAttribute(name = "invoiceEditForm") InvoiceEditForm form) {
		return form.getInvoiceForm();
	}
	
	@PostMapping("save")
	public String save(
			@ModelAttribute(name = "invoiceEditForm") InvoiceEditForm form,
			@ModelAttribute(name = "invoiceForm") InvoiceForm invoiceForm
			) {
	
		var customer = customerService.findById(form.getInvoiceForm().getCustomer().getId());
		
		form.getInvoiceForm().setCustomer(customer);
		form.getInvoiceForm().setCategory(invoiceForm.getCategory());
		form.getInvoiceForm().setDate(invoiceForm.getDate());	
		
		var id = invoiceService.save(form);
		form.clear();
		return "redirect:/user/invoice/detail?id=%s".formatted(id);
	}
	
	@GetMapping("detail")
	public String detail(
			@ModelAttribute(name = "invoiceEditForm") InvoiceEditForm form,
			@RequestParam int id, Model model) {
		var invoice = invoiceService.findById(id);
		
		form.setInvoiceForm(invoice.getInvoiceForm());
		form.setDetailForm(invoice.getDetailForm());
		
		model.addAttribute("invoice", invoice);
		return "invoice-detail";
	}
	
	@ModelAttribute("customerList")
	List<Customer> loadCustomers() {
		return customerService.findAll();
	}
	
	@GetMapping("delete")
	public String delete(@RequestParam int id) {
		invoiceService.deletebyId(id);
		return "redirect:/user/invoice";
	}
	
}

























