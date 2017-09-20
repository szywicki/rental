package com.libertymutual.goforcode.spark.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.AutoCloseableDb;
import com.libertymutual.goforcode.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class ApartmentController {

	public static final Route details = (Request req, Response res) -> {
		String idAsString = req.params("id");
		int id = Integer.parseInt(idAsString);
		
		try (AutoCloseableDb db = new AutoCloseableDb()) {
		Apartment apartment = Apartment.findById(id);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("apartment", apartment);
		return MustacheRenderer.getInstance().render("apartment/details.html", model);
		}
	};
	public static final Route newForm = (Request req, Response res)-> {
		return MustacheRenderer.getInstance().render("/apartment/newForm.html", null);
	};
	
	public static final Route create = (Request req, Response res) -> {
		try (AutoCloseableDb db = new AutoCloseableDb()) {
		Apartment apartment = new Apartment(
				Integer.parseInt(req.queryParams("rent")), 
				Integer.parseInt(req.queryParams("numberofBedrooms")),
				Double.parseDouble(req.queryParams("numberofBathrooms")),
				Integer.parseInt(req.queryParams("squareFootage")),
				req.queryParams("address"),
				req.queryParams("city"),
				req.queryParams("state"),
				req.queryParams("zipCode")
		);
			apartment.saveIt();
			res.redirect("/");
			return "";
		}
	};
}
