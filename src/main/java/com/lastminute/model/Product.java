package com.lastminute.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

	private BigDecimal price;
	private String name;

}
