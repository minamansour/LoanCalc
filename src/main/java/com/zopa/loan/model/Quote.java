package com.zopa.loan.model;

import java.math.BigDecimal;

/**
 * <p>
 * Class which representing a Quote.
 * </p>
 *
 * <p>
 * A Quote represents the information given to the borrower.
 * </p>
 *
 * @author Mina Mansour
 */
public class Quote {

	private BigDecimal rate;
	private BigDecimal monthlyRepayment;
	private BigDecimal totalRepayment;

	public Quote(BigDecimal rate, BigDecimal monthlyRepayment, BigDecimal totalRepayment) {
		this.rate = rate;
		this.monthlyRepayment = monthlyRepayment;
		this.totalRepayment = totalRepayment;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getMonthlyRepayment() {
		return monthlyRepayment;
	}

	public void setMonthlyRepayment(BigDecimal monthlyRepayment) {
		this.monthlyRepayment = monthlyRepayment;
	}

	public BigDecimal getTotalRepayment() {
		return totalRepayment;
	}

	public void setTotalRepayment(BigDecimal totalRepayment) {
		this.totalRepayment = totalRepayment;
	}
}
