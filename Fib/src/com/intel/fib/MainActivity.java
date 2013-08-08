package com.intel.fib;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private EditText input;
	private Button buttonGo;
	private TextView output;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); // Inflate from XML to Java

		input = (EditText) findViewById(R.id.input);
		buttonGo = (Button) findViewById(R.id.button_go);
		output = (TextView) findViewById(R.id.output);

		buttonGo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		long n = Long.parseLong(input.getText().toString());
		
		// Call to Java
		new FibTask().execute(n);
		
		// Call Native
		long start = System.currentTimeMillis();
		long resultN = FibLib.fibN(n);
		long timeN = System.currentTimeMillis() - start;
		output.append( String.format("fibN() = %d (%d ms)\n", resultN, timeN) );
	}

	class FibTask extends AsyncTask<Long, Void, Long> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(MainActivity.this, "Calculating",
					"Please wait...");
		}

		long timeJ;
		// Non-UI worker thread
		@Override
		protected Long doInBackground(Long... params) {
			timeJ = System.currentTimeMillis();
			long resultJ = FibLib.fibJ(params[0]);
			timeJ = System.currentTimeMillis() - timeJ;
			return resultJ;
		}

		@Override
		protected void onPostExecute(Long result) {
			dialog.dismiss();
			output.append(String.format("fibJ() = %d (%d ms)\n", result, timeJ));
		}
	}

}
