package net.windmillbd.meenabazar;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

import net.windmillbd.meenabazar.models.JsonMaker;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	
	Button btnLogin;
	EditText edtEmail, edtPassword;
	String email,password;
	
	ProgressDialog aProgressDialog;
	static final String LOGIN_URL= "http://opencart.windmillinfotech.com/index.php?route=feed/web_api/login&key=123456";
	
	void Initialize()
	{
		btnLogin = (Button) findViewById(R.id.login_btnLogin);
		edtEmail = (EditText) findViewById(R.id.login_edtEmail);
		edtPassword = (EditText) findViewById(R.id.login_edtPassword);
		email="";
		password="";
	}
	
	void SetValuesFromUI()
	{
		email = edtEmail.getText().toString();
		password = edtPassword.getText().toString();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);
		Initialize();
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent i = new Intent(Login.this, Dashboard.class);
				//startActivity(i);
				SetValuesFromUI();
				
				new AttemptLogin().execute();
			}
		});
	}
	
	class AttemptLogin extends AsyncTask<Void, Void, Void>{
		
		
		JSONObject aJSONObject;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();			
			aProgressDialog = new ProgressDialog(Login.this);
			aProgressDialog.setMessage("Loging In...");
			aProgressDialog.setIndeterminate(false);
			aProgressDialog.setCanceledOnTouchOutside(false);
			aProgressDialog.setCancelable(false);
			aProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			HttpResponse response = null;
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(LOGIN_URL);
			
			List<NameValuePair> pair = new ArrayList<NameValuePair>();
			pair.add(new BasicNameValuePair("email", email));
			pair.add(new BasicNameValuePair("password", password));
			
			//System.out.println("Wind: " + pair.toString());
			
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
			
			JsonMaker someJsonMaker = new JsonMaker(response);
			aJSONObject = someJsonMaker.GetJSONObject();
			
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			aProgressDialog.dismiss();				
			
			//System.out.println("Wind: " + aJSONObject.toString());
			
			String message = null;
			boolean isSuccess = false;
			
			try {
				message = aJSONObject.getString("message");
				isSuccess = aJSONObject.getBoolean("success");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(isSuccess)
			{
				Intent i = new Intent(Login.this, Dashboard.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
			}else{
				Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
			}
			
			
		}
		
		
		
	}
	
}
