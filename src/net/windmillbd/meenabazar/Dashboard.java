package net.windmillbd.meenabazar;

import net.windmillbd.meenabazar.shared_preferences.SessionManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Dashboard extends ActionBarActivity{

	ImageButton btnProductCategory;
	ImageButton btnCustomerFeedback; // Temporary Logout
	
	
	void Initialize()
	{
		btnProductCategory = (ImageButton) findViewById(R.id.dashboard_imgbtnProductCatagory);
		btnCustomerFeedback = (ImageButton) findViewById(R.id.dashboard_imgbtnCustomerFeedback);
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dashboard);
		
		Initialize();
		
		btnProductCategory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Dashboard.this, ProductCategory.class);
				startActivity(i);					
			}
		});
		
		btnCustomerFeedback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				SessionManager session = new SessionManager(getApplicationContext());
				session.LogoutCustomer();
				finish();
				
			}
		});

	}

}
