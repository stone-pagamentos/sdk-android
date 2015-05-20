package br.com.stone.classes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;
import br.com.stone.objects.PrintObject;
import br.com.stone.utils.StoneUseful;

public class StartPrint extends StoneUseful{
	
	/*
	 * small  - 48 
	 * medium - 48
	 * big    - 24
	 * */
	
	public static void putSpace(List<PrintObject> printList, int lines) {
		for (int i = 0; i < lines; i++)
			printList.add(new PrintObject(" ", PrintObject.MEDIUM, PrintObject.CENTER));
	}
	
	public static final boolean validateListSize(List<PrintObject> printList) {
		
		List<String> errorList = new ArrayList<>();
		
		for (int i = 0; i < printList.size(); i++) {
			// if item was a MEDIUM or SMALL size
			if (printList.get(i).getSize() == PrintObject.MEDIUM || printList.get(i).getSize() == PrintObject.SMALL) {
				if (printList.get(i).getMessage().length() > 48) {
					errorList.add(String.format("Item at position %d has more then 48 characters", i));
				}
			} else { // BIG size
				if (printList.get(i).getMessage().length() > 24) {
					errorList.add(String.format("Item at position %d has more then 24 characters", i));
				}
			}
		}
		
		if (errorList.isEmpty() == false) {
			for (int i = 0; i < errorList.size(); i++) {
				Log.e("ERROR_PRINT", errorList.get(i));
			}
			return false;
		} else {
			return true;
		}
	}
	
	public static final void sendPrint(Activity activity,
			List<PrintObject> printList) {

		try {

			String thisPackage = activity.getApplicationContext().getPackageName();

			Intent intent = activity.getPackageManager().getLaunchIntentForPackage(STONE_PACKAGE);
			intent.putExtra("action", SEND_PRINT_REQUEST);
			intent.putParcelableArrayListExtra("listToPrint", (ArrayList<? extends Parcelable>) printList);
			intent.putExtra("externalPackage", thisPackage);
			activity.startActivity(intent);
		} catch (Exception e) {
			// show toast if there's no application
			Toast.makeText( activity, "Aplicativo da Stone não foi encontrado nesse dispositivo.", Toast.LENGTH_LONG).show();
		}
	}

}
