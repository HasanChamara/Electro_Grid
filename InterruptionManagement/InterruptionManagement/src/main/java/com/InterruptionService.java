package com;

import model.Interruption;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Interruptions")

public class InterruptionService {
	

	Interruption interruptionObj = new Interruption();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readInterruptions() {
		return interruptionObj.readInterruptions();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertInterruption(@FormParam("intAriaCode") String intAriaCode, @FormParam("intAriaName") String intAriaName,
			@FormParam("intDate") String intDate, @FormParam("intTime") String intTime) {
		String output = interruptionObj.insertInterruption(intAriaCode, intAriaName, intDate, intTime);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateInterruption(String interruptionData) {
		// Convert the input string to a JSON object
		JsonObject interruptionObject = new JsonParser().parse(interruptionData).getAsJsonObject();
		// Read the values from the JSON object
		String intID = interruptionObject.get("intID").getAsString();
		String intAriaCode = interruptionObject.get("intAriaCode").getAsString();
		String intAriaName = interruptionObject.get("intAriaName").getAsString();
		String intDate = interruptionObject.get("intDate").getAsString();
		String intTime = interruptionObject.get("intTime").getAsString();
		String output = interruptionObj.updateInterruption(intID, intAriaCode, intAriaName, intDate, intTime);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteInterruption(String interruptionData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(interruptionData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String intID = doc.select("intID").text();
		String output = interruptionObj.deleteInterruption(intID);
		return output;
	}

}