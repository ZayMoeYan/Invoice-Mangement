package com.jdc.invoice.domain.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	@Column(nullable = false)
	private String building;
	@Column(nullable = false)
	private String township;
	@Column(nullable = false)
	private String city;

	@MapsId
	@OneToOne(optional = false)
	@PrimaryKeyJoinColumn
	private Customer customer;

	public Address() {
	}

	public Address(Customer customer) {
		super();
		this.building = customer.getAddress().getBuilding();
		this.township = customer.getAddress().getTownship();
		this.city = customer.getAddress().getCity();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getTownship() {
		return township;
	}

	public void setTownship(String township) {
		this.township = township;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
