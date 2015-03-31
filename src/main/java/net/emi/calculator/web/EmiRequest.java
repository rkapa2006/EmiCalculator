package net.emi.calculator.web;

/**
 * Represents web request corresponding the json request object
 */
public class EmiRequest {
	
	private double mortgageAmount;
	private double annualCostOfMortgage;
	private int mortgageTerm;

	public double getMortgageAmount() {
		return mortgageAmount;
	}

	public void setMortgageAmount(double mortgageAmount) {
		this.mortgageAmount = mortgageAmount;
	}

	public double getAnnualCostOfMortgage() {
		return annualCostOfMortgage;
	}

	public void setAnnualCostOfMortgage(double annualCostOfMortgage) {
		this.annualCostOfMortgage = annualCostOfMortgage;
	}

	public int getMortgageTerm() {
		return mortgageTerm;
	}

	public void setMortgageTerm(int mortgageTerm) {
		this.mortgageTerm = mortgageTerm;
	}

}
