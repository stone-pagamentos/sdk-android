package br.com.stone.activity;


import java.util.ArrayList;
import java.util.List;

import br.com.stone.classes.StartPrint;
import br.com.stone.objects.PrintObject;
import br.com.stone.stonedemosdk.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PrintActivity extends Activity implements OnClickListener {
	
	Button newLineButton;
	ViewGroup mContainerView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.print_activity);
		
		instanceViews();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_layout_changes, menu);
        return true;
    }

	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        
            case R.id.action_add_item:
                addItem();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

	private void instanceViews() {

		mContainerView = (ViewGroup) findViewById(R.id.container);
		newLineButton  = (Button)    findViewById(R.id.newLineButton);
		
		newLineButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		List<PrintObject> printList = new ArrayList<>();
		
		for (int i = 0; i < mContainerView.getChildCount(); i++) {
			
			String text  = ((EditText) mContainerView.getChildAt(i).findViewById(R.id.textToPrintEditText)).getText().toString();
			int position = ((Spinner)  mContainerView.getChildAt(i).findViewById(R.id.positionSpinner)).getSelectedItemPosition();
			int size     = ((Spinner)  mContainerView.getChildAt(i).findViewById(R.id.sizeSpinner)).getSelectedItemPosition();
			
			PrintObject printObject = new PrintObject();
			printObject.setMessage(text);
			
			if(position == 0) //  left in spinner
				printObject.setAlign(PrintObject.LEFT);
			else if (position == 1) // center in spinner
				printObject.setAlign(PrintObject.CENTER);
			else if (position == 2) // right in spinner
				printObject.setAlign(PrintObject.RIGHT);
			else 
				printObject.setAlign(PrintObject.TAG_PRINT);
			
			if(size == 0) // small in spinner
				printObject.setSize(PrintObject.SMALL);
			else if (size == 1) // medium in spinner
				printObject.setSize(PrintObject.MEDIUM);
			else if (size == 2)// big in spinner
				printObject.setSize(PrintObject.BIG);
			else
				printObject.setSize(PrintObject.TAG_PRINT);
			
			printList.add(printObject);
		}
		
		// insert 6 lines in space
		StartPrint.putSpace(printList, 6);
		
		
		// WARNING!!
		// it is important to check that all the elements
		// of your list are in the correct sizes
		if (StartPrint.validateListSize(printList))
			StartPrint.sendPrint(this, printList);
		else
			Toast.makeText(this, "Algum item da lista contém mais caracteres que o esperado.\nOlhe o log para mais detalhes.", Toast.LENGTH_LONG).show();
		
	}
	
	private void addItem() {
		
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.print_item_row, mContainerView, false);
		
		
		setArrayToSpinner(((Spinner)newView.findViewById((R.id.positionSpinner))), R.array.positions);
		setArrayToSpinner(((Spinner)newView.findViewById((R.id.sizeSpinner))), R.array.sizes);
		
		mContainerView.addView(newView, mContainerView.getChildCount());
	}
	
	private void setArrayToSpinner(Spinner spinner, int arrayFromResources){
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayFromResources, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

}
