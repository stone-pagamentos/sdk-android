package br.com.stone.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MaskAmount implements TextWatcher{
	EditText etAmount;

	public MaskAmount(EditText etAmount) {
		this.etAmount = etAmount;
	}
	public void beforeTextChanged(CharSequence s, int start, int count, int after){}


	private String current = "";
	
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		if (!s.toString().equals(current)) {
			etAmount.removeTextChangedListener(this);

			String cleanString = s.toString().replaceAll("[R$ ,.]", "");

			double parsed;

			try {
				parsed = Double.parseDouble(cleanString);
			} catch (Exception e) {
				parsed = Double.parseDouble("0");
			}
			;

			Locale.setDefault(Locale.ENGLISH);

			NumberFormat df = NumberFormat.getCurrencyInstance();

			DecimalFormatSymbols dfs = new DecimalFormatSymbols();

//			dfs.setCurrencySymbol("R$ ");
			dfs.setCurrencySymbol("");
			dfs.setGroupingSeparator('.');
			dfs.setMonetaryDecimalSeparator(',');

			((DecimalFormat) df).setDecimalFormatSymbols(dfs);
			String formated = df.format(parsed / 100);

			current = formated;
			etAmount.setText(formated);
			etAmount.setSelection(formated.length());

			etAmount.addTextChangedListener(this);
		}
	}

	public void afterTextChanged(Editable s) {}

}
