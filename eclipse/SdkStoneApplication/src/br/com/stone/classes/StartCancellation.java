package br.com.stone.classes;

import br.com.stone.utils.StoneUseful;
import android.content.Context;
import android.content.Intent;

public class StartCancellation extends StoneUseful{

	public static void sendCancellationToStoneApplication(Context context ,String arn, String ca){
		
		try {
			
			String thisPackage = context.getApplicationContext().getPackageName();
			
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(STONE_PACKAGE);
			intent.putExtra("action", SEND_CANCELLATION);
			intent.putExtra("arn"   , arn);   
			intent.putExtra("ca"    , ca);   
			intent.putExtra("externalPackage", thisPackage);
			context.startActivity(intent);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
