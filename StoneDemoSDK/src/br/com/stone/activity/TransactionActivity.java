package br.com.stone.activity;

import br.com.stone.classes.StartTransaction;
import br.com.stone.stonedemosdk.R;
import br.com.stone.utils.GenericMethods;
import br.com.stone.utils.MaskAmount;
import br.com.stone.utils.SubAcquire;
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

	private int getChecked() {
		int position = 0;

		if (debitRadioButton.isChecked())
			position = 1; // debit
		else
			position = 2; // credit
		
		return position;
	}
	
	private AccountType accountType;
	
	private AccountType getType() { return accountType; }

	private void setType(AccountType accountType) { this.accountType = accountType; }
	
	private int getAccountType() {
		
		int accountType;

		if (debitRadioButton.isChecked())
			accountType = 1; // debit
		else
			accountType = 2; // credit
		
		return accountType;
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
	
	protected void onStop() {
		super.onStop();
		this.finish();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.enviarButton:
			
			Integer orderId = null;
			Integer animationStart = R.anim.fade_in;
			Integer animationEnd   = R.anim.fade_out;
			Integer automaticTransaction;
			String transactionAmount = null;
			
			// If a sub adquitente , fill with the data that is requested . If not, pass the parameter to null.
			//SubAcquire subAcquire = new SubAcquire();
			//subAcquire.setSaleAffiliationKey("Your saleAfiilitonKey");
			//subAcquire.setTradeName("Test Inc.");
			//subAcquire.setDocumentNumber("11111111111111");
			//subAcquire.setAddress("Street Test, 100, Center - RJ");
						
			if(orderIdEditText.getText().toString() != null && !orderIdEditText.getText().toString().equals(""))
			{
				orderId = valueCheck(Integer.parseInt(orderIdEditText.getText().toString()));
			}
			
			if(amountEditText.getText().toString() != null && !amountEditText.getText().toString().equals(""))
			{
				transactionAmount = amountEditText.getText().toString();
			}
			
			if (autoFlagCheckedTextView.isChecked()) 
			{	
				automaticTransaction = 1;
			}	
			else
			{
				automaticTransaction = 2;
			}	
						
			Integer typeOfInstallment    = GenericMethods.getTypeOfInstallment(parcelTypeSpinner.getSelectedItem().toString());
			Integer numberOfInstallments = getNumberOfInstallments(numberOfInstallmentsSpinner.getSelectedItem().toString());
			Integer accountType = AccountType.getByValue(getAccountType()).intValue;
			
			StartTransaction.sendTransactionToStoneApplication(this,                         // Context context
															   transactionAmount,  		     // String transactionAmount
															   accountType,				     // Integer accountType
															   numberOfInstallments, 	     // Integer numberOfInstallments
															   typeOfInstallment,	 	     // Integer typeOfInstallment
															   orderId,						 // Integer orderId
															   automaticTransaction,		 // Integer automatic sending of the transaction
															   animationStart,				 // Integer animationStart	
															   animationEnd, 				 // Integer animationEnd	
															   null);			             // Object subAcquire if an sub acquirer. If not pass the parameter to null
															   // if autoFlagCheckedTextView == 1, the User needs to confirm
															   // if autoFlagCheckedTextView == 2, the User need not confirm
			
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
	
	public enum AccountType {
		
		CHECKING(1),
		CREDIT(2),
		SAVINGS(3);

		public final int intValue;

		AccountType(int valueOption) {
			intValue = valueOption;
		}

		public int getValue() {
			return intValue;
		}

		public static AccountType getByValue(int value) {
			for(AccountType accountType: AccountType.values())
				if(accountType.getValue() == value)
					return accountType;
			
			return null;
		}
	}
}
