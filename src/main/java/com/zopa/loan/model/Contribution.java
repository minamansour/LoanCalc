package com.zopa.loan.model;

/**
 * <p>
 * Class which representing a Contribution.
 * </p>
 *
 * <p>
 * When calculating a {@link Quote}, the calculation is based on a list of
 * {@code Contribution}.
 * </p>
 *
 * <p>
 * A Contribution contains a single {@link Lender} and the contribution amount.
 * </p>
 * 
 * @author Mina Mansour
 */

public class Contribution {

	private Lender lender;
	private Integer amount;

	public Contribution(Lender lender, int amount) {
		this.lender = lender;
		this.amount = amount;
	}

	public Lender getLender() {
		return lender;
	}

	public void setLender(Lender lender) {
		this.lender = lender;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
