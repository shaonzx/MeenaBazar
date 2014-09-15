package net.windmillbd.meenabazar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.windmillbd.meenabazar.models.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ProductList extends ActionBarActivity{
	
	private ProgressDialog pDialog;

    private static String url = "http://opencart.windmillinfotech.com/index.php?route=feed/web_api/products&key=123456";

    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PRODUCT_ID = "id";
    private static final String TAG_NAME = "name";

    JSONArray products = null;
    
    ListView aListView;
    List<String> displayContent;

    ArrayList<HashMap<String, String>> product_List;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);
        
        aListView = (ListView) findViewById(R.id.product_list_products);
        displayContent = new ArrayList<String>();
 
        new GetCategories().execute();
    }

    private class GetCategories extends AsyncTask<Void, Void, Void> {
 
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

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET, pair);
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    products = jsonObj.getJSONArray(TAG_PRODUCTS);

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);
                         
                        String categoryId = c.getString(TAG_PRODUCT_ID);
                        String name = c.getString(TAG_NAME);
 
                        HashMap<String, String> category = new HashMap<String, String>();

                        category.put(TAG_PRODUCT_ID, categoryId);
                        category.put(TAG_NAME, name);
                        
                        displayContent.add(name);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            
    		ArrayAdapter<String> displayAdapter = new ArrayAdapter<String>(ProductList.this, android.R.layout.simple_list_item_1, displayContent);
    		
    		aListView.setAdapter(displayAdapter);
    		
        }
 
    }
    
}
