package net.windmillbd.meenabazar;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import net.windmillbd.meenabazar.models.JsonMaker;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetails extends ActionBarActivity {

	private static final String SINGLE_PRODUCT_URL = "http://opencart.windmillinfotech.com/index.php?route=feed/web_api/product&key=123456";

	ProgressDialog aProgressDialog;
	String productId;
	ImageView imgProductImage;
	TextView txtPrice;
	TextView txtDescription;

	/********* Declaration for product Details **************/

	String productName = null;
	String manufacturer = null;
	String productModel = null;
	String imageUrl = null;
	String description = null;
	int rating = 0;
	double unitPrice = 0.0;
	double specialUnitPrice = 0.0;

	Bitmap productImage = null;

	/*******************************************************/

	void Initialize() {
		productId = getIntent().getExtras().getString("getProductId");
		imgProductImage = (ImageView) findViewById(R.id.product_details_imgProductImage);
		txtPrice = (TextView) findViewById(R.id.product_details_txtPrice);
		txtPrice.setTypeface(null, Typeface.BOLD);
		txtDescription = (TextView) findViewById(R.id.product_details_txtProductDescription);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_details);

		Initialize();

		new SingleProductGetter().execute(productId);

	}

	class SingleProductGetter extends AsyncTask<String, Void, Void> {

		JSONObject responseJSONObject;
		JSONObject aJSONObject;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			aProgressDialog = new ProgressDialog(ProductDetails.this);
			aProgressDialog.setMessage("Please wait...");
			aProgressDialog.setIndeterminate(false);
			aProgressDialog.setCanceledOnTouchOutside(false);
			aProgressDialog.setCancelable(false);
			aProgressDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub

			HttpResponse response = null;
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(SINGLE_PRODUCT_URL);

			List<NameValuePair> pair = new ArrayList<NameValuePair>();
			pair.add(new BasicNameValuePair("product_id", params[0]));

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(pair));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				response = httpClient.execute(httpPost);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			JsonMaker aJsonMaker = new JsonMaker(response);
			responseJSONObject = aJsonMaker.GetJSONObject();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//aProgressDialog.dismiss();

			boolean success = false;

			try {
				success = responseJSONObject.getBoolean("success");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (success) {
				try {
					aJSONObject = responseJSONObject.getJSONObject("product");
					productName = aJSONObject.getString("name");
					manufacturer = aJSONObject.getString("manufacturer");
					productModel = aJSONObject.getString("model");
					imageUrl = aJSONObject.getString("image");
					description = aJSONObject.getString("description");
					rating = aJSONObject.getInt("rating");
					unitPrice = aJSONObject.getDouble("price");

					if (!aJSONObject.getString("special").equals("null")) {
						specialUnitPrice = aJSONObject.getDouble("special");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Abir: " + productName + " " + imageUrl);

			} else {
				Toast.makeText(ProductDetails.this, "Currently not available!",
						Toast.LENGTH_SHORT).show();
			}
			
			
			new DownloadImageTask().execute(imageUrl);

		}

	}
	
	class DownloadImageTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			try {
				InputStream in = new java.net.URL(params[0]).openStream();
				productImage = BitmapFactory.decodeStream(in);
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			aProgressDialog.dismiss();
			String price = String.valueOf(unitPrice);
			price = price + "/-";
			
			imgProductImage.setImageBitmap(productImage);
			txtPrice.setText(price);
			description = description.replace("<p>", "");
			description = description.replace("</p>", "");
			txtDescription.setText(description);
		}

	}


}
