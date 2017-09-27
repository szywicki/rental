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
import com.libertymutual.goforcode.spark.app.controllers.SessionApiController;
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
		
		String encryptedPassword = BCrypt.hashpw("pw", BCrypt.gensalt());
		
		try (AutoCloseableDb db = new AutoCloseableDb()){
		User.deleteAll();
		User curtis = new User("s@z", encryptedPassword, "s", "z");
		curtis.saveIt();
		
		Apartment.deleteAll();
		Apartment a =new Apartment(6200, 1, 4.0, 350, "123 Main St", "San Francisco", "CA", "95125", 0, true);
		curtis.add(a);
		a.saveIt();
		
		
		Apartment b =new Apartment(1459, 5, 6, 4000, "123 Cowboy Way", "Houston", "TX", "77006", 1, true);
		curtis.add(b);
		b.saveIt();
		
		
		Apartment c =new Apartment(600, 3, 2, 1500, "123 Suburb Way", "Wausasu", "WI", "54401", 0, false);
		curtis.add(c);
		c.saveIt();
		
		}
		
		enableCORS("http://localhost:4200", "*", "*");
		
		path("/apartments", () -> {
			before("/new",  SecurityFilters.isAuthenticated);
			get("/new",  ApartmentController.newForm);
			
			before("/mine",  SecurityFilters.isAuthenticated);
			get("/mine", ApartmentController.index);
			get("/:id",  ApartmentController.details); 
			
			before("",  SecurityFilters.isAuthenticated);
			post("",     ApartmentController.create);
			
			before("/:id/like",   SecurityFilters.isAuthenticated);
			post("/:id/like", ApartmentController.likes);
			
			before("/:id/activations", SecurityFilters.isAuthenticated);
			post("/:id/activations", ApartmentController.activate);
			
			before("/:id/deactivations", SecurityFilters.isAuthenticated);
			post("/:id/deactivations", ApartmentController.deactivate);
		});
		
		get("/", 			   HomeController.index);
		get("/login", 		   SessionController.newForm);
		post("/login",         SessionController.create);
		get("/logout", 		   SessionController.destroy);
		post("/logout",         SessionController.destroy);
		post("/users",        UserController.create);
		get("/users/new",         UserController.newForm);
		
		path("/api", () -> {
			get ("/apartments/mine", ApartmentApiController.mine);
			get ("/apartments/:id", ApartmentApiController.details); 
			get ("/apartments", ApartmentApiController.index);
			post ("/apartments", ApartmentApiController.create);
			
			post("/sessions", SessionApiController.create);
			delete("/sessions/mine", SessionApiController.destroy);
		});
		
	}
	
	private static void enableCORS(final String origin, final String methods, final String headers) {

	    options("/*", (request, response) -> {

	        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
	        if (accessControlRequestHeaders != null) {
	            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
	        }

	        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
	        if (accessControlRequestMethod != null) {
	            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
	        }

	        return "OK";
	    });

	    before((request, response) -> {
	        response.header("Access-Control-Allow-Origin", origin);
	        response.header("Access-Control-Request-Method", methods);
	        response.header("Access-Control-Allow-Headers", headers);
	        response.header("Access-Control-Allow-Credentials", "true");
	        // Note: this may or may not be necessary in your particular application
	    });
	}
	
}
