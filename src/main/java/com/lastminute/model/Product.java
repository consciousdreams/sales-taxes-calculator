package com.lastminute.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Product {

	private BigDecimal price;
	private BigDecimal priceWithTaxes;
	private String name;

	public Product(BigDecimal price, String name) {
		this.price = price;
		this.name = name;
	}

}
