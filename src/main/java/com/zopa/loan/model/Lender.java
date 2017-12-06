package com.zopa.loan.model;

import java.math.BigDecimal;

/**
 * <p>
 * Class which representing a Lender.
 * </p>
 * 
 * @author Mina Mansour
 */
public class Lender {

	private String name;
	private BigDecimal rate;
	private Integer available;

	public Lender(String name, BigDecimal rate, Integer available) {
		this.name = name;
		this.rate = rate;
		this.available = available;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public Integer getAvailable() {
		return available;
	}
}
