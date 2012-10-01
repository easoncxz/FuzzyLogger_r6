package com.easoncxz.fuzzylogger_r6;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class List extends ListActivity {
	
	private TextView tvPrompt, tvEmpty;
	private EditText et;
	private Button btnAdd;
	private ListView lv;
	private Intent intent;
	private Bundle extras;
	private int value_group_type;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
	}
	
	private void setTextApprop(String mode){
		//TODO
	}
	
	private void fillListApprop(String mode){
		//TODO
	}
	
	
}