package net.windmillbd.meenabazar;

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
		setContentView(R.layout.launcher);
		
		/*ImageButton btnProductCategory;
		
		btnProductCategory = (ImageButton) findViewById(R.id.dashboard_imgbtnProductCatagory);
		
		btnProductCategory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LauncherActivity.this, ProductCategory.class);
				startActivity(i);
			}
		});*/
		
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
