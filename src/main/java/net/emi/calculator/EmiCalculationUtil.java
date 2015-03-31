package net.emi.calculator;

public class EmiCalculationUtil {
	
	public static double roundOff(double amount, int number) {
		int i = 0, val = 1;
		
		while(++i<= number)  val = val * 10;
		
		double roundedAmount = Math.round(amount * val) / (double) val;
		
		return roundedAmount;
	}

	public static EquatedMonthlyInstallment formatPayment(
			EquatedMonthlyInstallment payment) {
		return EquatedMonthlyInstallment.of(payment.getSerialNo(),
				roundOff(payment.getPrincipalAmount(), 2),
				roundOff(payment.getInterestAmount(), 2));

	}
}
