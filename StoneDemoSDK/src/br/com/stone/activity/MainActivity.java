package br.com.stone.activity;

import br.com.stone.methods.CancellationResponse;
import br.com.stone.methods.TransactionResponse;
import br.com.stone.stonedemosdk.R;
import br.com.stone.utils.PinpadObject;
import br.com.stone.xml.ReturnOfCancellationXml;
import br.com.stone.xml.ReturnOfTransactionXml;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	Button transaction;
	Button cancellation;
	Button ecommerce;
	Button print;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		instanceViews();
	}

	private void instanceViews() {
		
		transaction  = (Button) findViewById(R.id.transationButton);
		cancellation = (Button) findViewById(R.id.cancellationButton);
		ecommerce    = (Button) findViewById(R.id.ecommerceButton);
		print    	 = (Button) findViewById(R.id.printButton);
		
		transaction.setOnClickListener(this);
		cancellation.setOnClickListener(this);
		ecommerce.setOnClickListener(this);
		print.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		
		Intent intent;

		switch (v.getId()) {
		
		case R.id.transationButton:
			intent = new Intent(getApplicationContext(), TransactionActivity.class);
			startActivity(intent);
			break;

		case R.id.cancellationButton:
			intent = new Intent(getApplicationContext(), CancellationActivity.class);
			startActivity(intent);
			break;
			
		case R.id.ecommerceButton:
			intent = new Intent(getApplicationContext(), TypedSaleActivity.class);
			startActivity(intent);
			break;
			
		case R.id.printButton:
			intent = new Intent(getApplicationContext(), PrintActivity.class);
			startActivity(intent);
			break;
			
		default:
			break;
		}
	}
	
	protected void onResume() {
		super.onResume();
		getExternalInformations();
	}
	
	public void getExternalInformations() {

		Bundle backActivity = getIntent().getExtras();

		if(backActivity != null) {
			
			String xmlTransaction  = backActivity.getString("xmlTransaction");
			String xmlCancellation = backActivity.getString("xmlCancellation");
			
			// if a transaciton
			if(xmlTransaction != null && !xmlTransaction.equals("")) {
				
				ReturnOfTransactionXml mReturnOfTransactionXml = new ReturnOfTransactionXml();
				mReturnOfTransactionXml = TransactionResponse.getTransaction(this, xmlTransaction, backActivity);
				
				Log.i("sdk_stone",
						"\n\n======== Dados recebidos SDK ========"
						+ "\nValor               : " + mReturnOfTransactionXml.amount
						+ "\nARN                 : " + mReturnOfTransactionXml.arn
						+ "\nParcelas            : " + mReturnOfTransactionXml.parcel
						+ "\nBandeira            : " + mReturnOfTransactionXml.flag
						+ "\nCA                  : " + mReturnOfTransactionXml.ca
						+ "\nStatus              : " + mReturnOfTransactionXml.status
						+ "\nData                : " + mReturnOfTransactionXml.date
						+ "\nAmountOfInst.       : " + mReturnOfTransactionXml.amountOfInstallments
						+ "\nDemandId            : " + mReturnOfTransactionXml.demandId
						+ "\nTipo da transação   : " + mReturnOfTransactionXml.transactionType);
			}
			
			//if a cancellation
			if(xmlCancellation != null && !xmlCancellation.equals("")){
				
				ReturnOfCancellationXml mReturnOfCancellationXml = new ReturnOfCancellationXml();
				mReturnOfCancellationXml = CancellationResponse.getCancellation(this, xmlCancellation, backActivity);
				
				Log.i("sdk_stone",
						"\n\n======== Dados recebidos SDK ========"
						+ "\nCA      : " + mReturnOfCancellationXml.ca
						+ "\nARN     : " + mReturnOfCancellationXml.arn
						+ "\nStatus  : " + mReturnOfCancellationXml.status);
				
				
			}
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == 99) {
			
			Bundle mBundle = data.getExtras();
			PinpadObject pinpad = (PinpadObject) mBundle.getSerializable("pinpad");
			Toast.makeText(this, pinpad.toString(), Toast.LENGTH_LONG).show();
			
		}
		
		
	}

}
