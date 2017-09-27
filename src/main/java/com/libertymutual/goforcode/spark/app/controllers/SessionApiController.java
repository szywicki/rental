package com.libertymutual.goforcode.spark.app.controllers;

import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.JsonHelper;
import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.AutoCloseableDb;

import spark.Request;
import spark.Response;
import spark.Route;

public class SessionApiController {

	public static Route create = (Request req, Response res) -> {
		String json = req.body();
		Map credentials = JsonHelper.toMap(json);
		String email = credentials.get("email").toString();
		String password = credentials.get("password").toString();
		res.header("Content-Type", "application/json");

		try (AutoCloseableDb db = new AutoCloseableDb()) {
			User user = User.findFirst("email = ?", email);
			System.out.println("User: " + user);
			if (user != null && BCrypt.checkpw(password, user.getPassword())) {
				req.session().attribute("currentUser", user);
				res.status(201);
				return user.toJson(true);
			}
		}
		res.status(200);
		return "{}";
	};

	public static final Route destroy = (Request req, Response res) -> {
		req.session().removeAttribute("currentUser");
		res.header("Content-Type", "application/json");
		res.status(200);
		return "{}";
	};
}
