package com.jdc.invoice.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public class Pagination {

	private String url;
	private int current;
	private int total;
	private boolean first;
	private boolean last;
	private List<Integer> pages;
	private Map<String, String> params;
	private List<Integer> sizes;


	public Pagination() {
	}
	
	public boolean isShow() {
		return pages.size() > 1;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String url;
		private int current;
		private int total;
		private boolean first;
		private boolean last;
		private Map<String, String> params;
		private List<Integer> sizes;

		public Pagination build() {
			return new Pagination(url, current, total, first, last, params, sizes);
		}

		public Builder url(String url) {
			this.url = url;
			return this;
		}

		public <T> Builder page(Page<T> page) {
			this.current = page.getNumber();
			this.total = page.getTotalPages();
			this.first = page.isFirst();
			this.last = page.isLast();
			return this;
		}

		public Builder current(int current) {
			this.current = current;
			return this;
		}

		public Builder total(int total) {
			this.total = total;
			return this;
		}

		public Builder first(boolean first) {
			this.first = first;
			return this;
		}

		public Builder last(boolean last) {
			this.last = last;
			return this;
		}

		public Builder params(Map<String, String> params) {
			this.params = params;
			return this;
		}

		public Builder sizes(List<Integer> sizes) {
			this.sizes = sizes;
			return this;
		}

	}

	public Pagination(String url, int current, int total, boolean first, boolean last, Map<String, String> params,
			List<Integer> sizes) {
		super();
		this.url = url;
		this.current = current;
		this.total = total;
		this.first = first;
		this.last = last;
		this.params = null == params ? new HashMap<String, String>() : params;
		this.sizes = null == sizes ? new ArrayList<Integer>() : sizes;
		
		pages = new ArrayList<Integer>();
		pages.add(current);
		
		while(pages.size() < 3 && pages.get(0) > 0) {
			pages.add(0, pages.get(0) - 1);
		}

		while(pages.size() < 4 && pages.get(pages.size() - 1) < total - 1) {
			pages.add(pages.get(pages.size() - 1) + 1);
		}
		
		while(pages.size() < 4 && pages.get(0) > 0) {
			pages.add(0, pages.get(0) - 1);
		}
	}

	public String getUrl() {
		return url;
	}

	public int getCurrent() {
		return current;
	}

	public int getTotal() {
		return total;
	}

	public boolean isFirst() {
		return first;
	}

	public boolean isLast() {
		return last;
	}

	public List<Integer> getPages() {
		return pages;
	}

	public String getParams() {
		return params.entrySet().stream().map(a -> "%s=%s".formatted(a.getKey(), a.getValue()))
				.reduce("", (x, y) -> "%s&%s".formatted(x, y));
				
	}

	public List<Integer> getSizes() {
		return sizes;
	}

}
