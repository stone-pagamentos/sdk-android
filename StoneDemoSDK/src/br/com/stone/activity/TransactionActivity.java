package br.com.stone.activity;

import br.com.stone.classes.StartTransaction;
import br.com.stone.objects.Transaction;
import br.com.stone.stonedemosdk.R;
import br.com.stone.utils.GenericMethods;
import br.com.stone.utils.MaskAmount;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class TransactionActivity extends Activity implements OnClickListener{

	Button   sendButton;
	EditText amountEditText;
	EditText orderIdEditText;

	RadioButton debitRadioButton;
	RadioButton creditRadioButton;

	Spinner parcelTypeSpinner;
	Spinner numberOfInstallmentsSpinner;
	
	CheckedTextView autoFlagCheckedTextView;
	
	Transaction mTransaction = new Transaction();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_activity);

		instanceViews();
	}

	private void instanceViews() {
		
		instanceSpinners();

		debitRadioButton  = (RadioButton) findViewById(R.id.radio0);
		creditRadioButton = (RadioButton) findViewById(R.id.radio1);
		amountEditText    = (EditText) findViewById(R.id.valorEditText);
		orderIdEditText   = (EditText) findViewById(R.id.demandIdEditText);
		sendButton        = (Button) findViewById(R.id.enviarButton);
		autoFlagCheckedTextView = (CheckedTextView) findViewById(R.id.autoFlagCheckedTextView);
		autoFlagCheckedTextView.setClickable(true);

		MaskAmount ma = new MaskAmount(amountEditText);
		amountEditText.addTextChangedListener(ma);

		autoFlagCheckedTextView.setOnClickListener(this);
		sendButton.setOnClickListener(this);
	}

	private int getRadioChecked() {
		
		if (debitRadioButton.isChecked())
			return mTransaction.DEBIT; // debit = 1
		else
			return mTransaction.CREDIT; // credit = 2
	}
	
	private int getNumberOfInstallments(String parcel){
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
		ArrayAdapter<CharSequence> parcelTypeAdapter = ArrayAdapter.createFromResource(this, R.array.type_installment,
																						android.R.layout.simple_spinner_item);

		parcelTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		parcelTypeSpinner.setAdapter(parcelTypeAdapter);

		numberOfInstallmentsSpinner = (Spinner) findViewById(R.id.numParcelas);
		ArrayAdapter<CharSequence> numberOfInstallmentsAdapter = ArrayAdapter.createFromResource(this, R.array.number_installments,
																						android.R.layout.simple_spinner_item);

		numberOfInstallmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numberOfInstallmentsSpinner.setAdapter(numberOfInstallmentsAdapter);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.enviarButton:
			
			Integer orderId   = null;
			Integer animStart = R.anim.fade_in;
			Integer animEnd   = R.anim.fade_out;
			String  transactionAmount = new String();
			
			if(orderIdEditText.getText().toString() != null && !orderIdEditText.getText().toString().equals(""))
				orderId = valueCheck(Integer.parseInt(orderIdEditText.getText().toString()));
			
			if(amountEditText.getText().toString() != null && !amountEditText.getText().toString().equals(""))
				transactionAmount = amountEditText.getText().toString();

			/*
			 * It's deprecated
			 * 
			 * StartTransaction.sendTransactionToStoneApplication(this,              // Context context
			 *												   transactionAmount,  	 // String transactionAmount
			 *												   accountType,			 // Integer accountType
			 *												   numberOfInstallments, // Integer numberOfInstallments
			 *												   typeOfInstallment,	 // Integer typeOfInstallment
			 *												   orderId,				 // Integer orderId
			 *												   automaticTransaction, // Integer automatic sending of the transaction
			 *												   animaStart,   		 // Integer animationStart	
			 *												   animaEnd); 	    	 // Integer animationEnd
			 *
			 * This is the old way to set a transaction to Stone Aplication
			 * we recommend you to use the method "startNewTransaction()" in StartTransaction
			 * as the following example:
			 * */

			// popule Transaction object
			mTransaction.setAmount(transactionAmount); 
			mTransaction.setTypeOfPurchase(getRadioChecked());
			mTransaction.setNumberOfInstalments(getNumberOfInstallments(numberOfInstallmentsSpinner.getSelectedItem().toString()));
			mTransaction.setTypeOfInstalment(parcelTypeSpinner.getSelectedItemPosition()); // 
			
			if(orderId != null)
				mTransaction.setDemandId(orderId);
			else
				mTransaction.setDemandId(0);
			
			if (!autoFlagCheckedTextView.isChecked()) 
				mTransaction.setNeededConfirm(true);
			else
				mTransaction.setNeededConfirm(false);
			
			StartTransaction.startNewTransaction(this, mTransaction, animStart, animEnd);
			
			TransactionActivity.this.finish();
			
			break;
			
		case R.id.autoFlagCheckedTextView:
			
			if(autoFlagCheckedTextView.isChecked())
				autoFlagCheckedTextView.setChecked(false);
			else
				autoFlagCheckedTextView.setChecked(true);
			
			break;

		default:
			break;
		}
	}
	
	protected void onStop() {
		super.onStop();
		this.finish();
	}
	
}
