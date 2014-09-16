package net.windmillbd.meenabazar.shared_preferences;

import net.windmillbd.meenabazar.Login;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

	SharedPreferences pref;
	Editor editor;
	Context _context;
	
	int PRIVATE_MODE = 0;
	
	static final String PREF_NAME = "pharmaSolve";
	static final String IS_LOGIN = "isLoggedIn";
	static final String KEY_CUSTOMER_ID = "customerId";
	
	
	
	public SessionManager(Context context)
	{
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	public void CreateLoginSession(String customerId)
	{		
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_CUSTOMER_ID, customerId);
		editor.commit();
	}
	
	public String GetCustomerIdFromSharedPreferences()
	{
		String customerId = pref.getString(KEY_CUSTOMER_ID, null);
		return customerId;
	}
	
	public boolean IsLoggedIn()
	{
		return pref.getBoolean(IS_LOGIN, false);
	}
	
	public void LogoutCustomer()
	{
		// Clear All SharedPreferences Data
		editor.clear();
		editor.commit();
		
		// After Logout Redirect User to Login Screen
		Intent i = new Intent(_context, Login.class);
		
		// Closing All the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// New Flag to Start New Activity
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Start Activity to Show the Login Screen
		_context.startActivity(i);
	}
}
