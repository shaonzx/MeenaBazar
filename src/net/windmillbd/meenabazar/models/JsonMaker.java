package net.windmillbd.meenabazar.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonMaker {

	HttpResponse aHttpResponse;

	public JsonMaker(HttpResponse someResponse) {
		aHttpResponse = someResponse;
	}

	public JSONObject GetJSONObject() {
		
		JSONObject resultJSONObject = null;
		
		
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(aHttpResponse
					.getEntity().getContent(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuilder builder = new StringBuilder();

		try {
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONTokener tokener = new JSONTokener(builder.toString());

		// JSONObject aJSONObject = null;

		try {
			resultJSONObject = new JSONObject(tokener);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		return resultJSONObject;
		
	}

}
