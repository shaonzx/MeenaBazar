package net.windmillbd.meenabazar;

import net.windmillbd.meenabazar.shared_preferences.SessionManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class LauncherActivity extends Activity{
	
	ImageButton btnLogin, btnRegister;
	
	void Initialize()
	{
		btnLogin = (ImageButton) findViewById(R.id.launcher_imgbtnLogin);
		btnRegister = (ImageButton) findViewById(R.id.launcher_imgbtnRegister);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		SessionManager session = new SessionManager(getApplicationContext());
		if(session.IsLoggedIn())
		{
			Intent intentToDashboard = new Intent(this, Dashboard.class);
			intentToDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intentToDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intentToDashboard);
			
		}else{		
			
			setContentView(R.layout.launcher);	
			Initialize();
			
			btnLogin.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(LauncherActivity.this, Login.class);
					startActivity(i);
				}
			});
			
			btnRegister.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(LauncherActivity.this, Registration.class);
					startActivity(i);
				}
			});			
		}
		
		

	}	
}
