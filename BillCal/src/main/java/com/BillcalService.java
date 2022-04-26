package com;

import model.Billcal;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Billcal")

public class BillcalService {

	Billcal billObj = new Billcal();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return billObj.readBillInfo();
	}

	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBill(@FormParam("accNumber") String accNumber, @FormParam("name") String name,
			@FormParam("units") int units, @FormParam("date") String date) {
		
		
		double total = 0;
		if(units <= 60) {
			total = units * 10 + 100; 
		}
		else if (units <=90) {
			total = 60 * 10 + (units - 60) * 13 + 150;
		}
		else if (units <=120) {
			total = 60 * 10 + 30 * 13 + (units - 90) * 17 + 200;
		}
		else if (units > 120) {
			total = 60 * 10 + 30 * 13 + 30 * 17 + (units - 120) * 20 + 250;
		}
		String output = billObj.insertBill(accNumber, name, units, date, total);
		return output;
	}
	
	
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBillInfo(String billData) {
		// Convert the input string to a JSON object
		JsonObject billObject = new JsonParser().parse(billData).getAsJsonObject();
		// Read the values from the JSON object
		String id = billObject.get("id").getAsString();
		String accNumber = billObject.get("accNumber").getAsString();
		String name = billObject.get("name").getAsString();
		int units = billObject.get("units").getAsInt();
		String date = billObject.get("date").getAsString();
		double total = billObject.get("total").getAsDouble();
		
		total = 0;
		if(units <= 60) {
			total = units * 10 + 100; 
		}
		else if (units <=90) {
			total = 60 * 10 + (units - 60) * 13 + 150;
		}
		else if (units <=120) {
			total = 60 * 10 + 30 * 13 + (units - 90) * 17 + 200;
		}
		else if (units > 120) {
			total = 60 * 10 + 30 * 13 + 30 * 17 + (units - 120) * 20 + 250;
		}
		
		
		String output = billObj.updateBillInfo(id,accNumber, name, units, date, total);
		return output;
	}
	
	
	
	
	

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletBillInfo(String billData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(billData, "", Parser.xmlParser());

		// Read the value from the element <id>
		String id = doc.select("id").text();
		String output = billObj.deletBillInfo(id);
		return output;
	}
	
}
