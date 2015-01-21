package br.com.stone.activity;

import br.com.stone.classes.StartTypedTransaction;
import br.com.stone.stonedemosdk.R;
import br.com.stone.utils.GenericMethods;
import br.com.stone.utils.MaskAmount;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TypedSaleActivity extends Activity implements OnClickListener {
	
	String externalPackage;

	EditText amount;
	EditText date;
	EditText pan;
	EditText cvv;

	Spinner typeParcels;
	Spinner numberOfParcels;
	
	Button sendTransaction;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.typed_activity);
		
		instanceViews();
	}

	private void instanceViews() {
		
		amount = (EditText) findViewById(R.id.amountTypedEditText);
		pan    = (EditText) findViewById(R.id.panEditText);  // max 19 characters
		date   = (EditText) findViewById(R.id.dateEditText); // format: MM/yyyy
		cvv    = (EditText) findViewById(R.id.cvvEditText);  // max 4 characters
		
		MaskAmount ma = new MaskAmount(amount);
		amount.addTextChangedListener(ma);
		
		numberOfParcels = (Spinner) findViewById(R.id.spinnerNumberParcels);
		typeParcels     = (Spinner) findViewById(R.id.spinnerTypeParcel);
		
		sendTransaction = (Button) findViewById(R.id.ecommerceButton);
		sendTransaction.setOnClickListener(this);
		
		instanceSpinners();
	}

	private void instanceSpinners() {
		
		ArrayAdapter<CharSequence> numParcelasAdapter = ArrayAdapter.createFromResource(this, R.array.num_parcelas,
																						android.R.layout.simple_spinner_item);

		numParcelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numberOfParcels.setAdapter(numParcelasAdapter);
		
		ArrayAdapter<CharSequence> parcelTypeAdapter = ArrayAdapter.createFromResource(this, R.array.tipo_parcelas,
																						android.R.layout.simple_spinner_item);

		parcelTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeParcels.setAdapter(parcelTypeAdapter);
		
		
	}

	public void onClick(View v) {
		
		// send informations to Stone Application process the payment
		StartTypedTransaction.sendTypedTransactionToStoneApplication(getApplicationContext(), 
																	 amount.getText().toString(), pan.getText().toString(),
																	 cvv.getText().toString(), date.getText().toString(),
																	 Integer.parseInt(numberOfParcels.getSelectedItem().toString()),
																	 GenericMethods.getTypeOfParcel(typeParcels.getSelectedItem().toString()));
		
	}

}
