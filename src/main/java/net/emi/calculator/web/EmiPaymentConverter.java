package net.emi.calculator.web;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import net.emi.calculator.EquatedMonthlyInstallment;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json/Java and Java/Json Converter
 */
public class EmiPaymentConverter {

	public EmiRequest readEmiRequest(String emiRequestJson)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(emiRequestJson, EmiRequest.class);

	}

	public void writePaymentList(Writer jsonOutputStream,
			List<EquatedMonthlyInstallment> paymentList)
			throws JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		mapper.writeValue(jsonOutputStream, paymentList);
	}
}
