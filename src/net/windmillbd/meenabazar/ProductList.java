package net.windmillbd.meenabazar;

import java.util.ArrayList;
import java.util.List;

import net.windmillbd.meenabazar.models.ProductInfo;
import net.windmillbd.meenabazar.models.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ProductList extends ActionBarActivity {

	private ProgressDialog pDialog;

	private static String url = "http://opencart.windmillinfotech.com/index.php?route=feed/web_api/products&key=123456";

	ListView aListView;
	List<String> displayContent;
	List<ProductInfo> productlist;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_list);

		aListView = (ListView) findViewById(R.id.product_list_products);
		displayContent = new ArrayList<String>();
		productlist = new ArrayList<ProductInfo>();

		new GetProducts().execute();
		
		aListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				//Toast.makeText(ProductList.this, productlist.get(position).getProductName() + "\n" + productlist.get(position).getProductId(), Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(ProductList.this, ProductDetails.class);

				i.putExtra("getProductId", productlist.get(position).getProductId().toString());
				startActivity(i);
			}
		});
	}

	private class GetProducts extends AsyncTask<Void, Void, Void> {

		JSONArray productsArray = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ProductList.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			ServiceHandler sh = new ServiceHandler();
			String id = getIntent().getExtras().getString("categoryID");

			List<NameValuePair> pair = new ArrayList<NameValuePair>();
			pair.add(new BasicNameValuePair("category_id", id));

			String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST, pair);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					productsArray = jsonObj.getJSONArray("products");
					ProductInfo someProductInfo = null;
					JSONObject productObject = null;

					for (int i = 0; i < productsArray.length(); i++) {
						productObject = productsArray.getJSONObject(i);

						String productId = productObject.getString("id");
						String productName = productObject.getString("name");
						String productDescription = productObject
								.getString("description");
						double productUnitPrice = productObject
								.getDouble("price");
						double specialUnitPricce = 0.0;

						if (!productObject.getString("special").equals("null")) {
							specialUnitPricce = productObject
									.getDouble("special");
						}

						String productThumb = productObject.getString("thumb");
						int rating = productObject.getInt("rating");

						someProductInfo = new ProductInfo(productId,
								productName, productDescription,
								productUnitPrice, specialUnitPricce,
								productThumb, rating);
						
						productlist.add(someProductInfo);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			pDialog.dismiss();
			System.out.println("Hello: " + productlist.size());
			
			for (ProductInfo aProductInfo : productlist) {
				System.out.println("Hello: " + aProductInfo.getProductName());
				displayContent.add(aProductInfo.getProductName());
			}

			ArrayAdapter<String> displayAdapter = new ArrayAdapter<String>(
					ProductList.this, android.R.layout.simple_list_item_1,
					displayContent);

			aListView.setAdapter(displayAdapter);			

		}

	}

}
