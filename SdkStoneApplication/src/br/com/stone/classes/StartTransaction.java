package br.com.stone.classes;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.stone.objects.Transaction;
import br.com.stone.utils.StoneUseful;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class StartTransaction extends StoneUseful {
	
	private static final String TAG_MY_TRANCASTION = "MY_TRANSACTION";

	@Deprecated
	public static void sendTransactionToStoneApplication(Activity activity, String amount, Integer typeOfPurchase,
														 Integer numberOfInstalments, Integer typeInstalment, Integer demandId, int  autoSending,
														 Integer animStart, Integer animEnd) {
		
		try {
			
			String thisPackage = activity.getApplicationContext().getPackageName();

			Intent intent = activity.getPackageManager().getLaunchIntentForPackage(STONE_PACKAGE);
			intent.putExtra("action", SEND_TRANSACTION);
			intent.putExtra("amount", amount.replace(",", "").replace(".", ""));   
			intent.putExtra("externalPackage", thisPackage);
			intent.putExtra("typeOfPurchase",  typeOfPurchase);  
			intent.putExtra("numberOfParcels", numberOfInstalments); 
			intent.putExtra("typeParcels", 	   typeInstalment);
			intent.putExtra("demandId", 	   demandId);
			intent.putExtra("autoSending", 	   autoSending);
			
			String fill = "\n============================================================\n";

			Log.v("sdk_stone", "Sending this informations: " 			  	 +
							   fill 									  	 +
							   "\nAmount..........:  " + amount 		  	 +
							   "\nExternalPackage.:  " + thisPackage 	  	 +
							   "\nTypeOfPurchase..:  " + typeOfPurchase   	 +
							   "\nNumberOfParcels.:  " + numberOfInstalments +
							   "\nTypeParcels.....:  " + typeInstalment	  	 +
							   "\nDemandId........:  " + demandId 		  	 +
							   "\nAutoSending.....:  " + autoSending      	 +
							   fill);

			activity.startActivity(intent);
			if(animStart != null && animEnd != null)
				activity.overridePendingTransition(animStart, animEnd);
			
		} catch (Exception e){
			// show toast if there's no application
			Toast.makeText(activity, "Aplicativo da Stone não foi encontrado nesse dispositivo.", Toast.LENGTH_LONG).show();
		}
	}
	
	public static void startNewTransaction(Activity activity, Transaction transaction, Integer animStart, Integer animEnd){
		
		String transactionJson = getTransactionJson(transaction);
		String thisPackage     = activity.getApplicationContext().getPackageName();
		
		try {
			
			if (transactionJson == null)
				throw new Exception("transactionJson is null");
			
			Intent intent = activity.getPackageManager().getLaunchIntentForPackage(STONE_PACKAGE);
			intent.putExtra("action", 		   SEND_TRANSACTION_V2);
			intent.putExtra("externalPackage", thisPackage);
			intent.putExtra("transactionJson", transactionJson);
			activity.startActivity(intent);
			
			Log.v(TAG_MY_TRANCASTION, transaction.toString());
			
			if(animStart != null && animEnd != null)
				activity.overridePendingTransition(animStart, animEnd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// get transaction and return a json to send
	private static String getTransactionJson(Transaction transaction){
		
		JSONObject jsonObject = new JSONObject();
		
		try {
			
			jsonObject.put("amount", 		  transaction.getAmount());
			jsonObject.put("typeOfPurchase",  transaction.getTypeOfPurchase());
			jsonObject.put("numberOfParcels", transaction.getNumberOfInstalments());
			jsonObject.put("typeParcels", 	  transaction.getTypeInstalments());
			jsonObject.put("demandId", 		  transaction.getDemandId());
			jsonObject.put("isNeededConfirm", transaction.isNeededConfirm());
			jsonObject.put("emailClient", 	  transaction.getEmailClient());
			
			return jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
