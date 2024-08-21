package com.jdc.invoice.utils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class InvoiceAppExceptionController {

	@ExceptionHandler(InvoiceAppException.class)
	public String handle(InvoiceAppException e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("message", e.getMessage());
		return "redirect:/";
	}
	
}
