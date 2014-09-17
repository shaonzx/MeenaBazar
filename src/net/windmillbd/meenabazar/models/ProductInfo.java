package net.windmillbd.meenabazar.models;

public class ProductInfo {

	String name;
	String id;
	String productDescription;
	double unitPrice;
	double specialUnitPrice;
	String productThumbunailUrl;
	int rating;

	public ProductInfo(String id, String name, String productDescription,
			double unitPrice, double specialPrice, String productThumbunailUrl,
			int rating) {

		this.name = name;
		this.id = id;
		this.productDescription = productDescription;
		this.unitPrice = unitPrice;
		this.specialUnitPrice = specialPrice;
		this.productThumbunailUrl = productThumbunailUrl;
		this.rating = rating;

	}

	public String getProductName() {
		return name;
	}

	public String getProductId() {
		return id;
	}

	public String getProductDescription() {
		return productDescription;
	}
	
	public double getProductUnitPrice(){
		return unitPrice;
	}
	
	public double getProductSpecialUnitPrice() {
		return specialUnitPrice;
	}
	
	public String getProductThumbUrl() {
		return productThumbunailUrl;
	}
	
	public int getProductRating() {
		return rating;
	}

}
