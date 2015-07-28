package net.emi.calculator.web;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public class MortgageInputForm extends FormLayout {
	
	private static final long serialVersionUID = -8811022699667069181L;

	@PropertyId("principalAmount")
	private final TextField principalAmount;

	@PropertyId("interestRate")
	private final TextField interestRate;

	@PropertyId("term")
	private final TextField term;

	private final Button computeButton;

	public MortgageInputForm() {
		
		principalAmount = new TextField("Loan Amount ($):", "");
		interestRate = new TextField("Interest Rate (%):", "");
		term = new TextField("Loan Term (Months):", "");

		computeButton = new Button("Compute");

		this.addComponents(principalAmount, interestRate, term,
				computeButton);

		iterator().forEachRemaining(
				component -> component.setWidth(100.0f, Unit.PERCENTAGE));

		initUI();
	}

	private void initUI() {
		principalAmount.setInputPrompt("Enter loan Amount in $");
		principalAmount.setNullRepresentation("");
		principalAmount.setRequired(true);
		principalAmount.setRequiredError("Amount cannot be empty!");
		principalAmount.addValidator(new DoubleRangeValidator(
				"Amount cannot or too high!", Double.valueOf(1.0),
				Double
						.valueOf(9999999.99)));

		interestRate.setInputPrompt("Enter % anual rate of interest ");
		interestRate.setNullRepresentation("");
		interestRate.setRequired(true);
		interestRate.setRequiredError("Rate can not be empty or too high!");
		interestRate.addValidator(new DoubleRangeValidator(
				"Rate can not be too high", Double.valueOf(1.0), Double
						.valueOf(9999999.99)));

		term.setInputPrompt("Enter loan term in # of months");
		term.setNullRepresentation("");
		term.setRequired(true);
		term.setRequiredError("Term can not be empty");
		term.addValidator(new IntegerRangeValidator("Term can not be too high",
				1, 999));


		this.setMargin(true);
		this.setSpacing(true);
		// this.setSizeFull();
	}

	public void setComputeClickListener(ClickListener clickListener) {
		this.computeButton.addClickListener(clickListener);
	}
}
