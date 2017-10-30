package br.com.stone.utils;

import android.app.Activity;

public class StoneUseful {
	
	protected static final String STONE_PACKAGE     = "br.com.stone";
	protected static final int    SEND_TRANSACTION  = 1;
	protected static final int    SEND_CANCELLATION = 2;
	protected static final int    SEND_TYPED_TRANSACTION = 3;
	protected static final int    SEND_TRANSACTION_V2 = 4;
	protected static final int    SEND_PRINT_REQUEST  = 1011;

	public static void clearBundle(Activity activityForClear){
		
		activityForClear.getIntent().removeExtra("xmlTransaction");
		activityForClear.getIntent().removeExtra("xmlCancellation");
		activityForClear.getIntent().removeExtra("xmlPrint");
	}
}
