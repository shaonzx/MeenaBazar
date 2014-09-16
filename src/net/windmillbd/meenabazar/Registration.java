package net.windmillbd.meenabazar;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends Activity {
	
	ProgressDialog aProgressDialog;
	
	Button btnCountinue;
	EditText edtFirstName, edtLastName, edtEmail, edtTelephone, edtFax, edtCompany
			, edtAddressOne, edtAddressTwo, edtCity, edtPostCode, edtPassword, 
			edtConfirmPassword;
	
	TextView txtFirstName, txtLastName, txtEmail, txtAddressOne,
			 txtCity, txtPostCode, txtPassword, txtConfirmPassword;
	
	Spinner spnrCountry, spnrRegion;
	
	RadioGroup rdogrpNewsLetter;
	
	
	RadioButton selectedRdoButton;
	
	CheckBox chkCheckTerms;
	
	static final String REGISTRATION_URL= "http://opencart.windmillinfotech.com/index.php?route=feed/web_api/addcustomers&key=123456";
	
	String firstName, lastName, email, telephone, fax, 
			company, addressOne, addressTwo, city, postCode,
			country, region, password, confirmPassword;
	int newsletter;
	
	void Initialize()	
	{
		
		txtFirstName = (TextView) findViewById(R.id.registration_txtFirstName);
		txtLastName = (TextView) findViewById(R.id.registration_txtLastName);
		txtEmail = (TextView) findViewById(R.id.registration_txtEmail);
		txtAddressOne = (TextView) findViewById(R.id.registration_txtAddress1);
		txtCity = (TextView) findViewById(R.id.registration_txtCity);
		txtPostCode = (TextView) findViewById(R.id.registration_txtPostCode);
		txtPassword = (TextView) findViewById(R.id.registration_txtPassword);
		txtConfirmPassword = (TextView) findViewById(R.id.registration_txtPasswordConfirm);
		
		edtFirstName = (EditText) findViewById(R.id.registration_edtFirstName);
		edtLastName = (EditText) findViewById(R.id.registration_edtLastName);
		edtEmail = (EditText) findViewById(R.id.registration_edtEmail);
		edtTelephone = (EditText) findViewById(R.id.registration_edtTelephone);
		edtFax = (EditText) findViewById(R.id.registration_edtFax);
		edtCompany = (EditText) findViewById(R.id.registration_edtCompany);
		edtAddressOne = (EditText) findViewById(R.id.registration_edtAddress1);
		edtAddressTwo = (EditText) findViewById(R.id.registration_edtAddress2);
		edtCity = (EditText) findViewById(R.id.registration_edtCity);
		edtPostCode = (EditText) findViewById(R.id.registration_edtPostCode);
		edtPassword = (EditText) findViewById(R.id.registration_edtPassword);
		edtConfirmPassword = (EditText) findViewById(R.id.registration_edtPasswordConfirm);
		
		
		spnrCountry = (Spinner) findViewById(R.id.registration_spnrCountry);
		spnrRegion = (Spinner) findViewById(R.id.registration_spnrRegionState);
		
		rdogrpNewsLetter = (RadioGroup) findViewById(R.id.registration_rdoGrpNewsLetter);

		
		chkCheckTerms = (CheckBox) findViewById(R.id.registration_chkTerm);
		
		btnCountinue = (Button) findViewById(R.id.registration_btnContinue);
		firstName = "";
		lastName = "";
		email = "";
		telephone = "";
		fax = "";
		company = "";
		addressOne = "";
		addressTwo = "";
		city = "";
		postCode = "";
		company = "";
		region = "";
		password = "";
		confirmPassword = "";
		newsletter = 0;
		
		btnCountinue.setEnabled(false);
		
		
	}

	boolean IsValidEmail(CharSequence target) {
	    if (target == null) {
	        return false;
	    } else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	    }
	}
	
	public boolean ValidateRegistrationForm()
	{
		boolean isInputsValid = true;
		

		
		if(firstName.length() == 0)
		{
			Toast.makeText(Registration.this, "First name can not be blank", Toast.LENGTH_SHORT).show();
			txtFirstName.setTextColor(Color.RED);
			isInputsValid = false;
		}else{
			txtFirstName.setTextColor(Color.WHITE);
		}
		
		if(lastName.length() == 0)
		{
			Toast.makeText(this, "Last name can not be blank", Toast.LENGTH_SHORT).show();
			txtLastName.setTextColor(Color.RED);
			isInputsValid = false;
		}else{
			txtLastName.setTextColor(Color.WHITE);
		}
		
		if(!IsValidEmail(email))
		{
			Toast.makeText(this, "Email is not in format", Toast.LENGTH_SHORT).show();
			txtEmail.setTextColor(Color.RED);
			isInputsValid = false;		
		}else{
			txtEmail.setTextColor(Color.WHITE);
		}
		
		
		if(addressOne.length() == 0)
		{
			Toast.makeText(this, "Address can not be blank", Toast.LENGTH_SHORT).show();
			txtAddressOne.setTextColor(Color.RED);
			isInputsValid = false;
		}else{
			txtAddressOne.setTextColor(Color.WHITE);
		}
		
		if(city.length() == 0)
		{
			Toast.makeText(this, "City can not be blank", Toast.LENGTH_SHORT).show();
			txtCity.setTextColor(Color.RED);
			isInputsValid = false;
		}else{
			txtCity.setTextColor(Color.WHITE);
		}
		
		if(postCode.length() == 0)
		{
			Toast.makeText(this, "Post Code can not be blank", Toast.LENGTH_SHORT).show();
			txtPostCode.setTextColor(Color.RED);
			isInputsValid = false;
		}else{
			txtPostCode.setTextColor(Color.WHITE);
		}
		
		
		
		if(password.length() < 1)
		{
			Toast.makeText(this, "Minimum password length is 6 Digits", Toast.LENGTH_SHORT).show();
			txtPassword.setTextColor(Color.RED);
			isInputsValid = false;
		}else{
			txtPassword.setTextColor(Color.WHITE);
		}
		
		if(password.length() == 0)
		{
			Toast.makeText(this, "Password can not be blank", Toast.LENGTH_SHORT).show();
			txtPassword.setTextColor(Color.RED);
			isInputsValid = false;
		}		
		
		if(!password.equals(confirmPassword))
		{
			Toast.makeText(this, "Password confirmation doesn’t match password", Toast.LENGTH_SHORT).show();
			txtConfirmPassword.setTextColor(Color.RED);
			isInputsValid = false;
		}else{
			txtConfirmPassword.setTextColor(Color.WHITE);
		}
		
		return isInputsValid;		
	}
	
	
	void SetValuesFromUI()
	{
		firstName = edtFirstName.getText().toString();
		lastName = edtLastName.getText().toString();
		email = edtEmail.getText().toString();
		telephone = edtTelephone.getText().toString();
		fax = edtFax.getText().toString();
		company = edtCompany.getText().toString();
		addressOne = edtAddressOne.getText().toString();
		addressTwo = edtAddressTwo.getText().toString();
		city = edtCity.getText().toString();
		postCode = edtPostCode.getText().toString();
		password = edtPassword.getText().toString();
		confirmPassword = edtConfirmPassword.getText().toString();
		
		int selectedRadioButtonIndex = rdogrpNewsLetter.getCheckedRadioButtonId();				
		selectedRdoButton = (RadioButton) findViewById(selectedRadioButtonIndex);
		
		String rdoText = selectedRdoButton.getText().toString();
		
		if(rdoText.equals("Yes"))
			newsletter = 1;
		else
			newsletter = 0;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		Initialize();
		
		chkCheckTerms.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(chkCheckTerms.isChecked())
				{
					btnCountinue.setEnabled(true);
				}else{
					btnCountinue.setEnabled(false);
				}
			}
		});
		
		btnCountinue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				SetValuesFromUI();
				
				if(ValidateRegistrationForm())
				{
					//Toast.makeText(Registration.this, "Succsess!", Toast.LENGTH_SHORT).show();
					new AttemptRegister().execute();
				}				
			}
		});
	}
	
	class AttemptRegister extends AsyncTask<Void, Void, Void>{
		
		
		JSONObject aJSONObject;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();			
			aProgressDialog = new ProgressDialog(Registration.this);
			aProgressDialog.setMessage("Registering...");
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
			HttpPost httpPost = new HttpPost(REGISTRATION_URL);
			
			List<NameValuePair> pair = new ArrayList<NameValuePair>();
			pair.add(new BasicNameValuePair("firstname", firstName));
			pair.add(new BasicNameValuePair("lastname", lastName));
			pair.add(new BasicNameValuePair("email", email));
			pair.add(new BasicNameValuePair("telephone", telephone));
			pair.add(new BasicNameValuePair("fax", fax));
			pair.add(new BasicNameValuePair("company", company));
			pair.add(new BasicNameValuePair("address_1", addressOne));
			pair.add(new BasicNameValuePair("address_2", addressTwo));
			pair.add(new BasicNameValuePair("city", city));
			pair.add(new BasicNameValuePair("postcode", postCode));
			pair.add(new BasicNameValuePair("password", password));
			pair.add(new BasicNameValuePair("newsletter", String.valueOf(newsletter)));
			
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
			aProgressDialog.dismiss();
			System.out.println("Wind: " + aJSONObject.toString());
			
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);		
						
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
				Intent i = new Intent(Registration.this, Login.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
			}else{
				Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show();
			}
			
			
		}
		
		
		
	}
	
	
}
