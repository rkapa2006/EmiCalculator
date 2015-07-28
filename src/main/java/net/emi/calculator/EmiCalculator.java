package net.emi.calculator;

import static net.emi.calculator.EmiCalculationUtil.formatPayment;

import java.util.ArrayList;
import java.util.List;

import net.emi.calculator.web.MortgageModel;

public class EmiCalculator {

	public static void main(String... args) {
	    		
		EmiCalculator emiCalculator = new EmiCalculator();
		
		double amount = 100_000;
		double rate = 4;
		int months = 48;
		
		List<EquatedMonthlyInstallment> paymentList = emiCalculator
				.calculateEmi(amount,
				rate, months);
		
		for (EquatedMonthlyInstallment EquatedMonthlyInstalment : paymentList) {
			EquatedMonthlyInstallment payment = formatPayment(EquatedMonthlyInstalment);
			System.out.println(payment);
		}
	}
	
	/**
	 * Computes the list of emi payments for the term.
	 * 
	 * The first element with serial no 0 indicates totals.
	 * 
	 * @param emiModel
	 *            the request object
	 */
	public List<EquatedMonthlyInstallment> compute(MortgageModel emiModel) {
		List<EquatedMonthlyInstallment> paymentList = new ArrayList<>();

		double amount = emiModel.getPrincipalAmount();
		if (amount <= 0)
			throw new RuntimeException("Mortgage Amount required");

		double rate = emiModel.getInterestRate();
		if (rate <= 0)
			throw new RuntimeException("Cost of mortgage required");

		int numberOfMonths = emiModel.getTerm();
		if (numberOfMonths <= 0)
			throw new RuntimeException("Mortgage term required");

		List<EquatedMonthlyInstallment> payments = calculateEmi(amount, rate,
				numberOfMonths);

		for (EquatedMonthlyInstallment installment : payments) {
			paymentList.add(formatPayment(installment));
		}

		return paymentList;
	}

	/**
	 * Calculates the monthly payments of a mortgage
	 * 
	 * EMI = (P * R * (1 + R) ** N) / ( (1 + R) ** N - 1)
	 * 
	 * Where P is Mortgage Amount, R - % annual cost of mortgage, N is Term in
	 * number of months
	 * 
	 * @param amount
	 *            the mortgage amount.
	 * 
	 * @param rate
	 *            % annual cost on mortgage amount.
	 * 
	 * @param numberOfMonths
	 *            the term in number of months the mortgage amount is cleared.
	 * 
	 */
	public List<EquatedMonthlyInstallment> calculateEmi(double amount,
			double rate,
			int numberOfMonths) {

		List<EquatedMonthlyInstallment> paymentList = new ArrayList<>();

		rate = rate / (12 * 100);
		
		double emi = computeEMI(amount, rate, numberOfMonths);
		
		for (int i = 1; i <= numberOfMonths; i++) {
			
			double interestAmount = computeInterest(amount, rate);
			double principalAmount = emi - interestAmount;
			
			amount = amount - principalAmount;
			
			paymentList.add(EquatedMonthlyInstallment.of(i, principalAmount,
					interestAmount));
			
		}
		
		paymentList.add(0, getPaymentTotals(paymentList));
		
		return paymentList;
	}
	
	private double computeInterest(double amount, double rate) {
		double interest =  amount * rate;
		
		return interest;
	}
	
	private EquatedMonthlyInstallment getPaymentTotals(
			List<EquatedMonthlyInstallment> paymentList) {
		double principalAmount = 0;
		double interestAmount = 0;
		int serialNo= 0;
		
		for (EquatedMonthlyInstallment payment : paymentList) {
			serialNo = payment.getSerialNo();
			principalAmount += payment.getPrincipalAmount();
			interestAmount += payment.getInterestAmount();
		}
		
		return EquatedMonthlyInstallment.of(++serialNo, principalAmount,
				interestAmount);
	}
	
	private double computeEMI(double amount, double rate, int numberOfMonths) {
		
		double factor = Math.pow(1 + rate, numberOfMonths);
			
		double numerator = amount * rate * factor;
		double denominator = factor - 1;
		
		double emi = numerator / denominator;
		
		return emi;
	}
	
	
}
