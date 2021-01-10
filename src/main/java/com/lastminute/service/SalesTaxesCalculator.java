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
	private static final String IMPORTED_PRODUCT = "imported";

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
		BigDecimal totalTaxes = BigDecimal.ZERO;
		BigDecimal totalPrice = BigDecimal.ZERO;

		for (Product product : productList) {

			// check if a product is valid (not null check)
			if (!isValid(product)) {
				BigDecimal productTaxes = BigDecimal.ZERO;

				// calculate normal tax
				if (isTaxable(product.getName())) {
					productTaxes = productTaxes.add(product.getPrice().multiply(GENERAL_TAX_RATE));
				}

				// calculate importation taxes
				if (isImported(product.getName())) {
					productTaxes = productTaxes.add(product.getPrice().multiply(IMPORT_TAX_RATE));
				}

				// round taxes
				productTaxes = round(productTaxes);

				// calculate single price with taxes
				product.setPriceWithTaxes(productTaxes.add(product.getPrice()));

				// add taxes to the total taxes amount
				totalTaxes = totalTaxes.add(productTaxes);

				// add single product price with taxes to the total price
				totalPrice = totalPrice.add(product.getPriceWithTaxes());
			}

		}

		// return the total detail
		return new TotalDetail(totalTaxes, totalPrice);
	}

	private BigDecimal round(BigDecimal value) {
		return value.divide(ROUND_INCREMENT, 0, RoundingMode.UP).multiply(ROUND_INCREMENT);
	}

	private boolean isImported(String name) {
		return name.contains(IMPORTED_PRODUCT);
	}

	private boolean isTaxable(String name) {
		for (String notTaxableProduct : NOT_TAXABLE_PRODUCTS) {
			if (name.contains(notTaxableProduct)) {
				return false;
			}
		}
		return true;
	}

	private boolean isValid(Product product) {
		return product == null || product.getName() == null || product.getPrice() == null;
	}

}
