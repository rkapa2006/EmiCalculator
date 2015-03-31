package net.emi.calculator;

import static net.emi.calculator.EmiCalculationUtil.roundOff;

public class EquatedMonthlyInstallment {
	
	private int serialNo;
	private double principalAmount;
	private double interestAmount;
	
	public EquatedMonthlyInstallment(int serialNo, double principalAmount,
			double interestAmount) {
		this.serialNo = serialNo;
		this.principalAmount = principalAmount;
		this.interestAmount = interestAmount;
	}
	
	public EquatedMonthlyInstallment() {
	}

	public static EquatedMonthlyInstallment of(int serialNo,
			double principalAmount, double interestAmount) {
		return new EquatedMonthlyInstallment(serialNo, principalAmount,
				interestAmount);
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}

	public void setInterestAmount(double interestAmount) {
		this.interestAmount = interestAmount;
	}

	public int getSerialNo() {
		return this.serialNo;
	}
	
	public double getPrincipalAmount() {
		return this.principalAmount;
	}
	
	public double getInterestAmount() {
		return this.interestAmount;
	}
	
	public double getTotalAmount() {
		return roundOff(principalAmount + interestAmount, 2);
	}


	@Override
	public String toString() {
		return "MonthlyPayment [serialNo=" + serialNo + ", principalAmount="
				+ principalAmount + ", interestAmount=" + interestAmount
				+ " totalAmount=" + roundOff(getTotalAmount(), 2) + "]";
	}

}
