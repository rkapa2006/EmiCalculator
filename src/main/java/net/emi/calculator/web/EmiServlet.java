package net.emi.calculator.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.emi.calculator.EmiCalculator;
import net.emi.calculator.EquatedMonthlyInstallment;

/**
 * EMI Servlet
 */
@WebServlet(description = "EMI Calculator", urlPatterns = { "/emiCalculator" })
public class EmiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
 
		System.out.println("Received the request: ");
        BufferedReader requestReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";

        if(requestReader != null){
            json = requestReader.readLine();
        }

		System.out.println("The json:\n" + json);
 
		EmiPaymentConverter paymentConverter = new EmiPaymentConverter();
		EmiRequest emiRequest = paymentConverter.readEmiRequest(json);

		EmiCalculator emiCalculator = new EmiCalculator();
		List<EquatedMonthlyInstallment> paymentList = emiCalculator
				.compute(emiRequest);
 
		response.setContentType("application/json");

		paymentConverter.writePaymentList(new PrintWriter(System.out),
				paymentList);
 
		PrintWriter writer = new PrintWriter(response.getOutputStream());
		paymentConverter.writePaymentList(writer,
				paymentList);
    }
}

