package com.libertymutual.goforcode.spark.app.controllers;

import java.util.Map;

import org.javalite.common.JsonHelper;

import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.AutoCloseableDb;
import com.libertymutual.goforcode.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;
import spark.Route;

public class UserApiController {

	public static final Route newForm = (Request req, Response res) -> {
		return MustacheRenderer.getInstance().render("/signup", null);
	};
	
	public static final Route create = (Request req, Response res) -> {
		String json = req.body();
		Map map = JsonHelper.toMap(json);
		User user = new User();
		user.fromMap(map);
		
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			user.saveIt();
			res.status();
			return user.toJson(true);
		}
	};
}
