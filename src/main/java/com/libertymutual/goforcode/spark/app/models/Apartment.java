package com.libertymutual.goforcode.spark.app.models;




import org.javalite.activejdbc.Model;

public class Apartment extends Model{

	
	public Apartment() {}
	
	public Apartment(int rent, int numberOfBedrooms, double numberOfBathrooms, int squareFootage, String address, String city,
			String state, String zipCode, int numberOfLikes, boolean isActive) {
		
		setRent(rent);
		setNumberOfBedrooms(numberOfBedrooms);
		setSquareFootage(squareFootage);
		setNumberOfBathrooms(numberOfBathrooms);
		setAddress(address);
		setCity(city);
		setState(state);
		setZipCode(zipCode);
		setNumberOfLikes(numberOfLikes);
		setIsActive(isActive);
	}
	
	
	public void setIsActive(boolean isActive) {
		set("is_active", isActive);
		
	}
	
	public boolean getIsActive() {
		return getBoolean("is_active");
	}

	public void setNumberOfLikes(int numberOfLikes) {
		set("number_of_likes", numberOfLikes);
		
	}
	
	public int getNumberOfLikes() {
		return getInteger("number_of_likes");
	}

	public int getNumberOfBedrooms() {
		return getInteger("number_of_bedrooms");
	}

	public void setNumberOfBedrooms(short numberOfBedrooms) {
		set("number_of_bedrooms",numberOfBedrooms);
	}

	public int getSquareFootage() {
		return getInteger("square_footage");
	}

	public void setSquareFootage(int squareFootage) {
		set("square_footage", squareFootage);
	}

	public double getNumberOfBathrooms() {
		return getDouble("number_of_bathrooms");
	}

	public void setNumberOfBathrooms(double numberOfBathrooms) {
		set("number_of_bathrooms", numberOfBathrooms);
	}

	public String getAddress() {
		return getString("address");
	}

	public void setAddress(String address) {
		set("address", address);
	}

	public String getCity() {
		return getString("city");
	}

	public void setCity(String city) {
		set("city",city);
	}

	public String getState() {
		return getString("state");
	}

	public void setState(String state) {
		set("state", state);
	}

	public String getZipCode() {
		return getString("zip_code");
	}

	public void setZipCode(String zipCode) {
		set("zip_code", zipCode);
	}

	
	public int getRent() {
		return getInteger("rent");
	}

	public void setRent(int rent) {
		set("rent", rent);
	}

	public void setNumberOfBedrooms(int numberOfBedrooms) {
		set("number_of_bedrooms", numberOfBedrooms);
	}
	
	public int getUser_Id() {
		return getInteger("user_id");
	}

}
