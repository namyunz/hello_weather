package com.android.helloweather;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;

public class SearchActivity extends SherlockActivity {

	private ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchlayout);
		mActionBar=getSupportActionBar();
		
		mActionBar.setTitle(R.string.action_add);
		
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}

}
