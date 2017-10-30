package br.com.stone.activity;

import br.com.stone.classes.StartCancellation;
import br.com.stone.stonedemosdk.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CancellationActivity extends Activity implements OnClickListener{
	
	EditText arnEditText;
	EditText caEditText;
	Button   sendButton;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cancellation_activity);
		
		instanceViews();
	}

	private void instanceViews() {
		
		arnEditText = (EditText) findViewById(R.id.arnEditText);
		caEditText  = (EditText) findViewById(R.id.caEditText);
		sendButton  = (Button)   findViewById(R.id.sendButton);
		
		sendButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		String arn = arnEditText.getText().toString();
		String ca  = caEditText.getText().toString();
		
		if (arn != null && !arn.equals("") && ca != null && !ca.equals("")) 
			StartCancellation.sendCancellationToStoneApplication(getApplicationContext(), arn, ca);
		else 
			Toast.makeText(getApplicationContext(), "Algum campo está vazio e nenhum dos dois podem estar vazios.", 1).show();
		
	}
	
	protected void onStop() {
		super.onStop();
		CancellationActivity.this.finish();
	}

}
