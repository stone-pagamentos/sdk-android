package br.com.stone.classes;

import br.com.stone.utils.StoneUseful;
import android.content.Context;
import android.content.Intent;

public class StartTypedTransaction extends StoneUseful{

	public static void sendTypedTransactionToStoneApplication(Context context, String amount, String PAN, String CVV,
															  String date, Integer numberOfParcels, Integer typeParcels) {

		try {
			String externalDate = date.replace("[/, -]", "");
			String thisPackage = context.getApplicationContext().getPackageName();
			
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(STONE_PACKAGE);
			intent.putExtra("action", SEND_TYPED_TRANSACTION);
			intent.putExtra("amount", amount.replace(",", "").replace(".", ""));
			intent.putExtra("externalPackage", thisPackage);
			intent.putExtra("numberOfParcels", numberOfParcels);
			intent.putExtra("typeParcels", typeParcels);
			intent.putExtra("pan", PAN);
			intent.putExtra("cvv", CVV);
			intent.putExtra("date", putbar(externalDate));
			context.startActivity(intent);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}

	}
	
	private static String putbar(String date) {
		
		String dateWithBar = date.substring(0, 2) + "/" + date.substring(2, date.length());
		
		return dateWithBar;
	}

}
