package br.axwebservice;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	static TextView tv;
	EditText fromET;
	EditText toET;
	
	boolean callService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		callService = true;
		tv 		= (TextView) findViewById(R.id.textView1);
		fromET 	= (EditText) findViewById(R.id.fromCurrency);
		toET 	= (EditText) findViewById(R.id.toCurrency);
		
		fromET.addTextChangedListener(new TextWatcher()
		{
			public void afterTextChanged(Editable s) {
				if (s.toString().length() < 2)
					fromET.setError("Invalid currency code!");
				else
					fromET.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			public void onTextChanged(CharSequence s, int start, int before,int count) {}
		});
		toET.addTextChangedListener(new TextWatcher()
		{
			public void afterTextChanged(Editable s) {
				if (s.toString().length() < 2)
					toET.setError("Invalid currency code!");
				else
					toET.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			public void onTextChanged(CharSequence s, int start, int before,int count) {}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void callClick(View _view)
	{
		WebCall webCall = new WebCall();
		
		if (fromET.getText().toString().equals("") || fromET.getError() != null)
		{
			fromET.setError("Invalid currency code!");
			callService = false;
		}
		else
			callService = true;
		
		if (toET.getText().toString().equals("") || toET.getError() != null)
		{
			toET.setError("Invalid currency code!");
			callService = false;
		}
		else
			callService = callService && true;
		
		if (callService)
		{
			webCall.setFrom(fromET.getText().toString());
			webCall.setTo(toET.getText().toString());
			webCall.serviceCall();
		}
	}
}
