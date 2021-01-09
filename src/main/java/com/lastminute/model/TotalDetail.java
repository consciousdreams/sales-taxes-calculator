package com.lastminute.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalDetail {

	private BigDecimal totalTaxes;
	private BigDecimal totalPrice;

}
