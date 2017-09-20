package com.libertymutual.goforcode.spark.app.controllers;

import org.mindrot.jbcrypt.BCrypt;

import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.AutoCloseableDb;
import com.libertymutual.goforcode.spark.app.utilities.MustacheRenderer;

import spark.*;

public class UserController {

	public static final Route newForm = (Request req, Response res) -> {
		return MustacheRenderer.getInstance().render("user/newUser.html", null);
	};
		
			
	public static final Route create = (Request req, Response res) -> {
		String encryptedPassword = BCrypt.hashpw(req.queryParams("password"), BCrypt.gensalt());
		User user = new User(
				req.queryParams("email"), 
				encryptedPassword,
				req.queryParams("firstName"),
				req.queryParams("lastName")
		);
		try (AutoCloseableDb db = new AutoCloseableDb()) {
			user.saveIt();
			req.session().attribute("currentUser", user);
			res.redirect("/");
			return "";
		}
	};
}
