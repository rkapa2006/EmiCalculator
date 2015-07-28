package net.emi.calculator.web;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents loan input model
 */
@Getter
@Setter
public class MortgageModel {
	
	/** Principal amount */
	private Double principalAmount;

	/** Annual percentage rate of interest */
	private Double interestRate;

	/** Loan term in number of months */
	private Integer term;
}
