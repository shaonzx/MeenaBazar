package net.windmillbd.meenabazar.models;

public class CategoryInfo {

	String name;
	String id;
	
	public CategoryInfo(String name, String id){
		this.name = name;
		this.id = id;
	}
	
	public String getCategoryName(){
		return name;
	}
	
	public String getCategoryId(){
		return id;
	}
}
