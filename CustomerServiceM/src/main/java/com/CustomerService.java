package com;

import model.Customer;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Customers")

public class CustomerService {
	
	Customer customerObj = new Customer();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readCustomers() {
		return customerObj.readCustomers();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertCustomer(@FormParam("cName") String cName, @FormParam("cAddress") String cAddress,
			@FormParam("cPhone") int cPhone, @FormParam("cEmail") String cEmail, @FormParam("cType") String cType) {
		String output = customerObj.insertCustomer(cName, cAddress, cPhone, cEmail, cType);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(String customerData) {
		// Convert the input string to a JSON object
		JsonObject customerObject = new JsonParser().parse(customerData).getAsJsonObject();
		// Read the values from the JSON object
		String cID = customerObject.get("cID").getAsString();
		String cName = customerObject.get("cName").getAsString();
		String cAddress = customerObject.get("cAddresse").getAsString();
		int cPhone = customerObject.get("cPhone").getAsInt();
		String cEmail = customerObject.get("cEmail").getAsString();
		String cType = customerObject.get("cType").getAsString();
		String output = customerObj.updateCustomer(cID, cName, cAddress, cPhone, cEmail, cType);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomer(String customerData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(customerData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String cID = doc.select("cID").text();
		String output = customerObj.deleteCustomer(cID);
		return output;
	}

}
