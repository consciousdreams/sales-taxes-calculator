package com.lastminute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lastminute.model.Product;
import com.lastminute.model.TotalDetail;
import com.lastminute.service.SalesTaxesCalculator;

public class AppTest {

	private SalesTaxesCalculator calculator = new SalesTaxesCalculator();

	@Test
	public void checkTotalDetailTest_1() {
		// Initialize product list
		List<Product> productList = new ArrayList<>();
		productList.add(new Product(BigDecimal.valueOf(12.49), "book at 12.49"));
		productList.add(new Product(BigDecimal.valueOf(14.99), "music CD at 14.99"));
		productList.add(new Product(BigDecimal.valueOf(0.85), "chocolate bar at 0.85"));

		// calculate the detail
		TotalDetail totalDetail = calculator.getTotalDetail(productList);

		// check not null
		assertNotNull(totalDetail);

		// check the taxes for single product
		assertEquals(0, BigDecimal.valueOf(12.49).compareTo(productList.get(0).getPriceWithTaxes()));
		assertEquals(0, BigDecimal.valueOf(16.49).compareTo(productList.get(1).getPriceWithTaxes()));
		assertEquals(0, BigDecimal.valueOf(0.85).compareTo(productList.get(2).getPriceWithTaxes()));

		// check total taxes and total price
		assertEquals(0, BigDecimal.valueOf(1.50).compareTo(totalDetail.getTotalTaxes()));
		assertEquals(0, BigDecimal.valueOf(29.83).compareTo(totalDetail.getTotalPrice()));
	}

	@Test
	public void checkTotalDetailTest_2() {
		// Initialize product list
		List<Product> productList = new ArrayList<>();
		productList.add(new Product(BigDecimal.valueOf(10.00), "imported box of chocolates"));
		productList.add(new Product(BigDecimal.valueOf(47.50), "imported bottle of perfume"));

		// calculate the detail
		TotalDetail totalDetail = calculator.getTotalDetail(productList);

		// check not null
		assertNotNull(totalDetail);

		// check the taxes for single product
		assertEquals(0, BigDecimal.valueOf(10.50).compareTo(productList.get(0).getPriceWithTaxes()));
		assertEquals(0, BigDecimal.valueOf(54.65).compareTo(productList.get(1).getPriceWithTaxes()));

		// check total taxes and total price
		assertEquals(0, BigDecimal.valueOf(7.65).compareTo(totalDetail.getTotalTaxes()));
		assertEquals(0, BigDecimal.valueOf(65.15).compareTo(totalDetail.getTotalPrice()));
	}

	@Test
	public void checkTotalDetailTest_3() {
		// Initialize product list
		List<Product> productList = new ArrayList<>();
		productList.add(new Product(BigDecimal.valueOf(27.99), "imported bottle of perfume"));
		productList.add(new Product(BigDecimal.valueOf(18.99), "bottle of perfume"));
		productList.add(new Product(BigDecimal.valueOf(9.75), "packet of headache pills"));
		productList.add(new Product(BigDecimal.valueOf(11.25), "box of imported chocolates"));

		// calculate the detail
		TotalDetail totalDetail = calculator.getTotalDetail(productList);

		// check not null
		assertNotNull(totalDetail);

		// check the taxes for single product
		assertEquals(0, BigDecimal.valueOf(32.19).compareTo(productList.get(0).getPriceWithTaxes()));
		assertEquals(0, BigDecimal.valueOf(20.89).compareTo(productList.get(1).getPriceWithTaxes()));
		assertEquals(0, BigDecimal.valueOf(9.75).compareTo(productList.get(2).getPriceWithTaxes()));
		assertEquals(0, BigDecimal.valueOf(11.85).compareTo(productList.get(3).getPriceWithTaxes()));

		// check total taxes and total price
		assertEquals(0, BigDecimal.valueOf(6.70).compareTo(totalDetail.getTotalTaxes()));
		assertEquals(0, BigDecimal.valueOf(74.68).compareTo(totalDetail.getTotalPrice()));
	}

}
