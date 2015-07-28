package net.emi.calculator.web;

import java.text.NumberFormat;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import net.emi.calculator.EmiCalculator;
import net.emi.calculator.EquatedMonthlyInstallment;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;

@Title("EmiCalculator")
@Theme("tests-valo-flat")
public class EmiCaculatorUI extends UI {

	private static final long serialVersionUID = 8349580612517086495L;

	private EmiCalculator emiCalculator = new EmiCalculator();
	private Grid details;
	private Panel summaryComp;

	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout content = new VerticalLayout();
		content.setMargin(new MarginInfo(false, true, false, true));

		content.setSpacing(true);

		content.addComponent(createAboutComponent());
		content.addComponent(createHeaderComponent());
		content.addComponent(createAndBindContent());
		content.addComponent(createSummaryContent());
		content.addComponent(createDetailContent());

		content.iterator().forEachRemaining(
				component -> component.setWidth(100.0f, Unit.PERCENTAGE));

		setContent(content);
	}

	private Component createAboutComponent() {
		HorizontalLayout layout = new HorizontalLayout();
		String about = "About";

		Label aboutLabel = new Label(applyAboutTheme(about), ContentMode.HTML);
		String aboutDesc = "This page is built with <u>Vaadin Web Framework</u> created by <a href='https://vaadin.com/home'>Vaadin Inc.,</a>"
				+ " Drop a note @ rkapa2006@yahoo.com\n for feature requests."
				+ " Go to <a href='https://github.com/rkapa2006/EmiCalculator'> this website's Git Hub</a> to look at the source and clone it if you want.";
		aboutLabel.setDescription(aboutDesc);
		aboutLabel.addStyleName("v-align-right");

		String what = "What is this?";

		String whatDesc = "This is a simple fixed rate mortgage calculator."
				+ " It computes and shows the monthly emi with"
				+ " principal and interest component break up along with overall summary,"
				+ " for given principal amount, interest rate and loan term.";
		Label whatLabel = new Label(applyAboutTheme(what), ContentMode.HTML);
		whatLabel.setDescription(whatDesc);

		layout.addComponents(whatLabel, aboutLabel);
		whatLabel.addStyleName("v-align-left");
		layout.setMargin(false);
		layout.setSpacing(false);
		return layout;
	}

	private Component createHeaderComponent() {
		String headerCaption = "Mortgage Calculator (Fixed Rate)";
		Label headerComp = new Label(applyAppHeaderTheme(headerCaption),
				ContentMode.HTML);
		headerComp.addStyleName("v-align-center");
		headerComp.setSizeFull();

		return headerComp;
	}

	private String applyAboutTheme(String headerCaption) {
		return "<span style='font-size: 0.8em; color:blue'>"
				+ headerCaption + "</span>";
	}

	private String applyAppHeaderTheme(String headerCaption) {
		return "<span style='font-size: 1.4em; text-decoration: underline; font-weight: bold;'>"
				+ headerCaption + "</span>";
	}

	private String applySectionHeaderTheme(String sectionCaption) {

		return "<span style='font-size: 1.3em;font-weight: bold;'>"
				+ sectionCaption + "</span>";
	}

	private String applySectionDetailTheme(String sectionCaption) {

		return "<span style='font-size: 1.1em;font-weight: bold;'>"
				+ sectionCaption + "</span>";
	}

	private Component createSummaryContent() {
		summaryComp = new Panel(applySectionHeaderTheme("Summary"));

		return summaryComp;
	}

	private Panel createDetailContent() {
		Panel detailPane = new Panel(
				applySectionHeaderTheme("Mortgage Details"));

		details = new Grid();
		details.setSizeFull();
		details.setImmediate(true);

		detailPane.setContent(details);
		return detailPane;
	}

	private Component createAndBindContent() {
		Panel contentPanel = new Panel(
				applySectionHeaderTheme("Enter mortgage info"));

		contentPanel.setCaptionAsHtml(true);
		MortgageInputForm mortgageInput = new MortgageInputForm();
		mortgageInput.iterator().forEachRemaining(
						component -> ((AbstractComponent) component)
								.setImmediate(true));
		BeanFieldGroup<MortgageModel> mortgageInputGroup = bind(mortgageInput);
		mortgageInputGroup.getFields().forEach(
						field -> ((AbstractField<?>) field)
								.setValidationVisible(false));

		mortgageInput.setComputeClickListener((clickEvent) -> {
			try {
				if (mortgageInputGroup.isValid()) {
					mortgageInputGroup.commit();
					computeEmi(mortgageInputGroup);
				} else {
					mortgageInputGroup.getFields().forEach(
							field -> ((AbstractField<?>) field)
									.setValidationVisible(true));
							Notification
									.show("Validation Error",
											"Invalid Input in one of the fields, hover over the mouse on the invalid field to display it",
							Notification.Type.HUMANIZED_MESSAGE);
				}
					} catch (Exception exception) {
						exception.printStackTrace();
			}
		});

		contentPanel.setContent(mortgageInput);

		return contentPanel;
	}

	private void showSummary(EquatedMonthlyInstallment total,
			NumberFormat formatter) {
		String summary = "   Total Principal: "
				+ formatter.format(total.getPrincipalAmount())
				+ ",    Total Interest: "
				+ formatter.format(total.getInterestAmount())
				+ ",    Total Amount: "
				+ formatter.format(total.getTotalAmount());
		Label summaryLabel = new Label(applySectionDetailTheme(summary),
				ContentMode.HTML);
		HorizontalLayout layout = new HorizontalLayout();
		layout.addComponent(summaryLabel);
		layout.setMargin(true);
		layout.setSpacing(true);
		summaryComp.setContent(layout);
	}

	private void computeEmi(BeanFieldGroup<MortgageModel> mortgageInputGroup) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		BeanItem<MortgageModel> mortgageItem = mortgageInputGroup
				.getItemDataSource();
		List<EquatedMonthlyInstallment> emiList = emiCalculator
				.compute(mortgageItem.getBean());

		EquatedMonthlyInstallment total = emiList.remove(0);

		showSummary(total, formatter);
		BeanItemContainer<EquatedMonthlyInstallment> emiBeanItemContainer = new BeanItemContainer<>(
				EquatedMonthlyInstallment.class, emiList);

		details.setContainerDataSource(emiBeanItemContainer);
		
		String slno = "serialNo";
		String principalAmount = "principalAmount";
		String interestAmount = "interestAmount";
		String totalAmount = "totalAmount";
		details.setColumnOrder(slno, principalAmount, interestAmount,
				totalAmount);
		
		details.getColumn(slno).setExpandRatio(10);
		details.getColumn(principalAmount).setExpandRatio(100);
		details.getColumn(interestAmount).setExpandRatio(100);
		details.getColumn(totalAmount).setExpandRatio(100);
		
		details.getColumn(slno).setHeaderCaption("SlNo.");
		details.getColumn(principalAmount).setHeaderCaption("Principal Amount($)");
		details.getColumn(interestAmount).setHeaderCaption("Interest Amount($)");
		details.getColumn(totalAmount).setHeaderCaption("Total Amount($)");

		details.getColumn(principalAmount).setRenderer(new NumberRenderer(formatter));
		details.getColumn(interestAmount).setRenderer(new NumberRenderer(formatter));
		details.getColumn(totalAmount).setRenderer(new NumberRenderer(formatter));


		details.setCellStyleGenerator(cellref -> {
			return "v-align-right";
		});

	}

	private BeanFieldGroup<MortgageModel> bind(
			MortgageInputForm mortgageInputForm) {
		BeanFieldGroup<MortgageModel> mortgageInputGroup = new BeanFieldGroup<>(
				MortgageModel.class);

		mortgageInputGroup.buildAndBindMemberFields(mortgageInputForm);
		mortgageInputGroup.setItemDataSource(new MortgageModel());

		return mortgageInputGroup;
	}

	@WebServlet(description = "EMI Calculator", urlPatterns = { "/*" })
	@VaadinServletConfiguration(ui = EmiCaculatorUI.class, productionMode = true)
	public static class ExpensesTrackerUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 3756822839912375131L;
	}

}
