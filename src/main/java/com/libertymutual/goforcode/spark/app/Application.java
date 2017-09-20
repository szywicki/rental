package com.libertymutual.goforcode.spark.app;

import static spark.Spark.*;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.mindrot.jbcrypt.BCrypt;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.libertymutual.goforcode.spark.app.controllers.ApartmentApiController;
import com.libertymutual.goforcode.spark.app.controllers.ApartmentController;
import com.libertymutual.goforcode.spark.app.controllers.HomeController;
import com.libertymutual.goforcode.spark.app.controllers.SessionController;
import com.libertymutual.goforcode.spark.app.controllers.UserApiController;
import com.libertymutual.goforcode.spark.app.controllers.UserController;
import com.libertymutual.goforcode.spark.app.filters.SecurityFilters;
import com.libertymutual.goforcode.spark.app.models.Apartment;
import com.libertymutual.goforcode.spark.app.models.User;
import com.libertymutual.goforcode.spark.app.utilities.AutoCloseableDb;
import com.libertymutual.goforcode.spark.app.utilities.MustacheRenderer;

import spark.Request;
import spark.Response;

public class Application {
	
	public static void main(String[] args) {	
		
		String encryptedPassword = BCrypt.hashpw("password", BCrypt.gensalt());
		
		try (AutoCloseableDb db = new AutoCloseableDb()){
		User.deleteAll();
		User curtis = new User("curtis.schlak@theironyard.com", encryptedPassword, "Curtis", "Schlak");
		curtis.saveIt();
		
		Apartment.deleteAll();
		Apartment a =new Apartment(6200, 1, 4.0, 350, "123 Main St", "San Francisco", "CA", "95125");
		a.saveIt();
		curtis.add(a);
		
		Apartment b =new Apartment(1459, 5, 6, 4000, "123 Cowboy Way", "Houston", "TX", "77006");
		b.saveIt();
		curtis.add(b);
		
		Apartment c =new Apartment(600, 3, 2, 1500, "123 Suburb Way", "Wausasu", "WI", "54401");
		c.saveIt();
		curtis.add(c);
		}
		
		path("/apartments", () -> {
			before("/new",  SecurityFilters.isAuthenticated);
			get("/new",  ApartmentController.newForm);
			
			before("/mine",  SecurityFilters.isAuthenticated);
			get("/mine", ApartmentController.index);
			get("/:id",  ApartmentController.details); 
			
			before("",  SecurityFilters.isAuthenticated);
			post("",     ApartmentController.create);
		});
		
		get("/", 			   HomeController.index);
		get("/login", 		   SessionController.newForm);
		post("/login",         SessionController.create);
		get("/logout", 		   SessionController.destroy);
		post("/logout",         SessionController.destroy);
		post("/signup",        UserController.create);
		get("/signup",         UserController.newForm);
		
		path("/api", () -> {
			get ("/apartments/:id", ApartmentApiController.details); 
			post ("/apartments", ApartmentApiController.create);
		});
		
	}
	
}
