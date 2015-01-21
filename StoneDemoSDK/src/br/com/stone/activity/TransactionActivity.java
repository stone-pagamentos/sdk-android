package br.com.stone.activity;

import br.com.stone.classes.StartTransaction;
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
import android.widget.RadioButton;
import android.widget.Spinner;

public class TransactionActivity extends Activity {

	Button   enviar;
	EditText amount;
	EditText demandId;

	RadioButton debito;
	RadioButton credito;

	Spinner parcelTypeSpinner;
	Spinner numberOfParcelSpinner;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_activity);

		instanceViews();
	}

	private void instanceViews() {
		
		instanceSpinners();

		debito   = (RadioButton) findViewById(R.id.radio0);
		credito  = (RadioButton) findViewById(R.id.radio1);
		amount   = (EditText) 	 findViewById(R.id.valorEditText);
		demandId = (EditText) 	 findViewById(R.id.demandIdEditText);
		enviar   = (Button)   	 findViewById(R.id.enviarButton);

		MaskAmount ma = new MaskAmount(amount);
		amount.addTextChangedListener(ma);

		enviar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				Integer demand = null; 
				
				if(!demandId.getText().toString().equals(""))
					demand = valueCheck(Integer.parseInt(demandId.getText().toString()));
				
				Integer parcelType     = GenericMethods.getTypeOfParcel(parcelTypeSpinner.getSelectedItem().toString());
				Integer numberOfParcel = getNumberOfParcel(numberOfParcelSpinner.getSelectedItem().toString());
				
				StartTransaction.sendTransactionToStoneApplication(getApplicationContext(),      // Context context
																   amount.getText().toString(),  // String amount
																   getChecked(),				 // int typeOfPurchase
																   numberOfParcel, 	             // Integer numberOfParcels
																   parcelType,	 			     // Integer typeParcels
																   demand );         			 // Integer demandId
				
				TransactionActivity.this.finish();
			}
		});
	}

	private int getChecked() {
		int position = 0;

		if (debito.isChecked())
			position = 1; // debito
		else
			position = 2; // credito
		
		return position;
	}

	private int getNumberOfParcel(String parcel){
		return Integer.parseInt(parcel);
	}

	
	private Integer valueCheck(Integer valueToCheck){
		Integer value = null;
		
		if(valueToCheck != null && !valueToCheck.equals("") )
			value = valueToCheck;
		
		return value;
	}
	
	private void instanceSpinners() {

		parcelTypeSpinner = (Spinner) findViewById(R.id.tipoParcelamento);
		ArrayAdapter<CharSequence> parcelTypeAdapter = ArrayAdapter.createFromResource(this, R.array.tipo_parcelas,
																						android.R.layout.simple_spinner_item);

		parcelTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		parcelTypeSpinner.setAdapter(parcelTypeAdapter);

		numberOfParcelSpinner = (Spinner) findViewById(R.id.numParcelas);
		ArrayAdapter<CharSequence> numParcelasAdapter = ArrayAdapter.createFromResource(this, R.array.num_parcelas,
																						android.R.layout.simple_spinner_item);

		numParcelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numberOfParcelSpinner.setAdapter(numParcelasAdapter);
	}
	
	protected void onStop() {
		super.onStop();
		this.finish();
	}
}
