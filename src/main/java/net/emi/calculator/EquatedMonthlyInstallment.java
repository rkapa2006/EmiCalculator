package net.emi.calculator;

import static net.emi.calculator.EmiCalculationUtil.roundOff;
import lombok.Getter;

@Getter
public class EquatedMonthlyInstallment implements
		Comparable<EquatedMonthlyInstallment> {
	
	private int serialNo;

	private double principalAmount;

	private double interestAmount;
	
	private double totalAmount;

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
	
	public double getTotalAmount() {
		return roundOff(principalAmount + interestAmount, 2);
	}


	@Override
	public String toString() {
		return "MonthlyPayment [serialNo=" + serialNo + ", principalAmount="
				+ principalAmount + ", interestAmount=" + interestAmount
				+ " totalAmount=" + roundOff(getTotalAmount(), 2) + "]";
	}

	@Override
	public int compareTo(EquatedMonthlyInstallment other) {
		return Integer.valueOf(other.getSerialNo()).compareTo(this.serialNo);
	}

}
