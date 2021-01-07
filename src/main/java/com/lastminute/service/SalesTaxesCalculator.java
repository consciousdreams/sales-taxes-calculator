package com.lastminute.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.lastminute.model.Product;
import com.lastminute.model.TotalDetail;

public class SalesTaxesCalculator {

	private static final BigDecimal GENERAL_TAX_RATE = BigDecimal.valueOf(0.1);
	private static final BigDecimal IMPORT_TAX_RATE = BigDecimal.valueOf(0.05);
	private static final BigDecimal ROUND_INCREMENT = BigDecimal.valueOf(0.05);
	private static final String[] NOT_TAXABLE_PRODUCTS = { "book", "chocolate", "headache" };

	/**
	 * This method retrieves the total amount and taxes.
	 *
	 * @param List<Product> a list of Product
	 * @return TotalDetail the result with total amount and taxes.
	 */
	public TotalDetail getTotalDetail(List<Product> productList) {

		// check null safe
		if (productList == null) {
			// A specific exception can be thrown.
			// For the test purpose it is not implemented
			return new TotalDetail(BigDecimal.ZERO, BigDecimal.ZERO);
		}

		// Initialize fields
		BigDecimal taxes = BigDecimal.ZERO;
		BigDecimal total = BigDecimal.ZERO;

		for (Product product : productList) {

			// check if a product is valid (not null check)
			if (!isValid(product)) {

				BigDecimal productTaxes = BigDecimal.ZERO;

				// add normal tax
				if (isTaxable(product.getName())) {
					productTaxes = productTaxes.add(product.getPrice().multiply(GENERAL_TAX_RATE));
				}

				// add importation taxes
				if (isImported(product.getName())) {
					productTaxes = productTaxes.add(product.getPrice().multiply(IMPORT_TAX_RATE));
				}

				// add rounded taxes to the total taxes amount
				taxes = taxes.add(round(productTaxes));

				// add price to the total amount (not taxed yet)
				total = total.add(product.getPrice());
			}

		}

		// add taxes to find the total amount
		total = total.add(taxes);

		// return the total detail
		return new TotalDetail(taxes, total);
	}

	private BigDecimal round(BigDecimal value) {
		return value.divide(ROUND_INCREMENT, 0, RoundingMode.UP).multiply(ROUND_INCREMENT);
	}

	private boolean isImported(String name) {
		return name.contains("imported");
	}

	private boolean isTaxable(String name) {
		boolean taxable = true;
		for (String notTaxableProduct : NOT_TAXABLE_PRODUCTS) {
			if (name.contains(notTaxableProduct)) {
				taxable = false;
				break;
			}
		}
		return taxable;
	}

	private boolean isValid(Product product) {
		return product == null || product.getName() == null || product.getPrice() == null;
	}

}
