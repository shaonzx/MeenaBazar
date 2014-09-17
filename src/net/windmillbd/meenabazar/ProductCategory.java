package net.windmillbd.meenabazar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.windmillbd.meenabazar.models.CategoryInfo;
import net.windmillbd.meenabazar.models.ServiceHandler;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProductCategory extends ActionBarActivity {

	private ProgressDialog pDialog;

    private static String url = "http://opencart.windmillinfotech.com/index.php?route=feed/web_api/categories&key=123456";

    private static final String TAG_CATEGORIES = "categories";
    private static final String TAG_CATEGORY_ID = "category_id";
    private static final String TAG_NAME = "name";

    JSONArray categories = null;
    
    ListView aListView;
    List<String> displayContent;
    List<CategoryInfo> categorylist;

    ArrayList<HashMap<String, String>> category_List;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);
        
        aListView = (ListView) findViewById(R.id.category_list_categories);
        displayContent = new ArrayList<String>();
        categorylist = new ArrayList<CategoryInfo>();
 
        new GetCategories().execute();
    }

    private class GetCategories extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ProductCategory.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();
 

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    categories = jsonObj.getJSONArray(TAG_CATEGORIES);

                    CategoryInfo info = null;
                    
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject c = categories.getJSONObject(i);
                         
                        String categoryID = c.getString(TAG_CATEGORY_ID);
                        String name = c.getString(TAG_NAME);
                        
                        info = new CategoryInfo(name, categoryID);
                        categorylist.add(info);
 
                        HashMap<String, String> category = new HashMap<String, String>();

                        category.put(TAG_CATEGORY_ID, categoryID);
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
            
    		ArrayAdapter<String> displayAdapter = new ArrayAdapter<String>(ProductCategory.this, android.R.layout.simple_list_item_1, displayContent);
    		
    		aListView.setAdapter(displayAdapter);
    		
    		aListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent i = new Intent(ProductCategory.this, ProductList.class);
					
					i.putExtra("categoryID",categorylist.get(position).getCategoryId());
					startActivity(i);
				}
			});

        }
 
    }
    
}

