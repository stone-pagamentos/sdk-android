package br.com.stone.activity;

import android.app.Activity;
import android.os.Bundle;
import br.com.stone.classes.StartConnection;

public class ConnectionActivity extends Activity {
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		instanceViews();
	}

	private void instanceViews() {
		StartConnection.sendConnectionToStoneApplication(getApplicationContext());
	}
	
	protected void onStop() {
		super.onStop();
		ConnectionActivity.this.finish();
	}

}
