package com.jdc.invoice.domain.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String phone;
	private String email;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Level level;

	@OneToOne(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.PERSIST)
	private Address address;

	@OneToMany(mappedBy = "customer", orphanRemoval = true)
	private List<Invoice> invoices;

	public Customer() {
	}

	public Customer(Customer customer) {
		super();
		this.name = customer.getName();
		this.phone = customer.getPhone();
		this.email = customer.getEmail();
		this.level = customer.getLevel();
	}

	public enum Level {
		Normal, Gold, Silver, Platinum, Diamond
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, email, id, invoices, level, name, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(address, other.address) && Objects.equals(email, other.email) && id == other.id
				&& Objects.equals(invoices, other.invoices) && level == other.level && Objects.equals(name, other.name)
				&& Objects.equals(phone, other.phone);
	}

}
