package net.windmillbd.meenabazar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Dashboard extends ActionBarActivity{

	ImageButton btnProductCategory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dashboard);
		
		btnProductCategory = (ImageButton) findViewById(R.id.dashboard_imgbtnProductCatagory);
		
		btnProductCategory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Dashboard.this, ProductCategory.class);
				startActivity(i);
			}
		});

	}

}
