package com.libertymutual.goforcode.spark.app.controllers;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.JsonHelper;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import static spark.Spark.notFound;
import static spark.Spark.post;

import java.util.Map;

import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.AutoCloseableDb;

import spark.Request;
import spark.Response;
import spark.Route;

public class ApartmentApiController {

	public static final Route index = (Request req, Response res) -> {
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			LazyList<Apartment> apartments = Apartment.where("is_active = ?", true);
			res.header("Content-Type", "application/json");
			return apartments.toJson(true);
		}
	};

	public static final Route mine = (Request req, Response res) -> {
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			User currentUser = req.session().attribute("currentUser");
			LazyList<Apartment> myApartments = Apartment.where("user_id = ?", currentUser.getId());
			res.header("Content-Type", "application/json");
			return myApartments.toJson(true);
		}
	};

	public static final Route details = (Request req, Response res) -> {
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			String idAsString = req.params("id");
			int id = Integer.parseInt(idAsString);
			Apartment apartment = Apartment.findById(id);
			if (apartment != null) {
				res.header("Content-Type", "application/json");
				return apartment.toJson(true);
			}
			notFound("Did not find that");
			return "";
		}
	};

	public static final Route create = (Request req, Response res) -> {
		String json = req.body();
		Map map = JsonHelper.toMap(json);
		Apartment apartment = new Apartment();
		apartment.fromMap(map);
		
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			User currentUser = req.session().attribute("currentUser");
			apartment.set("is_Active", true);
			currentUser.add(apartment);
			apartment.saveIt();
			res.status(201);
			return apartment.toJson(true);
		}
	};
	
	public static final Route deactivate = (Request req, Response res) -> {
		String idAsString = req.params("id");
		int id = Integer.parseInt(idAsString);

		try (AutoCloseableDb db = new AutoCloseableDb()) {
			Apartment apartment = Apartment.findById(id);
			User currentUser = req.session().attribute("currentUser");
			apartment.set("is_active", false);
			apartment.saveIt();
			return apartment.toJson(true);
			}
		};
}
