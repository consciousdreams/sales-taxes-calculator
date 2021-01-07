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
		List<Product> productList = new ArrayList<>();
		productList.add(new Product(BigDecimal.valueOf(12.49), "book at 12.49"));
		productList.add(new Product(BigDecimal.valueOf(14.99), "music CD at 14.99"));
		productList.add(new Product(BigDecimal.valueOf(0.85), "chocolate bar at 0.85"));

		TotalDetail totalDetail = calculator.getTotalDetail(productList);
		assertNotNull(totalDetail);
		assertEquals(0, BigDecimal.valueOf(1.50).compareTo(totalDetail.getTaxes()));
		assertEquals(0, BigDecimal.valueOf(29.83).compareTo(totalDetail.getTotal()));
	}

	@Test
	public void checkTotalDetailTest_2() {
		List<Product> productList = new ArrayList<>();
		productList.add(new Product(BigDecimal.valueOf(10.00), "imported box of chocolates"));
		productList.add(new Product(BigDecimal.valueOf(47.50), "imported bottle of perfume"));

		TotalDetail totalDetail = calculator.getTotalDetail(productList);
		assertNotNull(totalDetail);
		assertEquals(0, BigDecimal.valueOf(7.65).compareTo(totalDetail.getTaxes()));
		assertEquals(0, BigDecimal.valueOf(65.15).compareTo(totalDetail.getTotal()));
	}

	@Test
	public void checkTotalDetailTest_3() {
		List<Product> productList = new ArrayList<>();
		productList.add(new Product(BigDecimal.valueOf(27.99), "imported bottle of perfume"));
		productList.add(new Product(BigDecimal.valueOf(18.99), "bottle of perfume"));
		productList.add(new Product(BigDecimal.valueOf(9.75), "packet of headache pills"));
		productList.add(new Product(BigDecimal.valueOf(11.25), "box of imported chocolates"));

		TotalDetail totalDetail = calculator.getTotalDetail(productList);
		assertNotNull(totalDetail);
		assertEquals(0, BigDecimal.valueOf(6.70).compareTo(totalDetail.getTaxes()));
		assertEquals(0, BigDecimal.valueOf(74.68).compareTo(totalDetail.getTotal()));
	}

}
