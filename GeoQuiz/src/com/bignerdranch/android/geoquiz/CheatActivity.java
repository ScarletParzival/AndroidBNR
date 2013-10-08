package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	public static final String EXTRA_ANSWER_IS_TRUE = 
			"com.bignerdranch.android.geoqiuz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = 
			"com.bignerdranch.android.geoqiuz.answer_is_shown";
	
	private boolean mAnswerIsTrue;
	private TextView mAnswerTextView;
	private Button mShowAnswer;
	private TextView showAPILevel;
	private boolean mIsAnswerShown;
	
	private void setAnswerShownResult(boolean isAnswerShown){
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		showAPILevel = (TextView)findViewById(R.id.showAPILevel);
		showAPILevel.setText("APK "+Build.VERSION.SDK_INT);
		
		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		
		mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
		
		if(savedInstanceState != null){
			mIsAnswerShown = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN);
			if(mIsAnswerShown){
				mAnswerIsTrue = savedInstanceState.getBoolean(EXTRA_ANSWER_IS_TRUE);
				if(mAnswerIsTrue){
					mAnswerTextView.setText(R.string.true_button);
				}
				else{
					mAnswerTextView.setText(R.string.false_button);
				}
			}
		}
		else{
			mIsAnswerShown = false;	
			mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		}
		
		setAnswerShownResult(mIsAnswerShown);
		
		mShowAnswer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIsAnswerShown = true;
				setAnswerShownResult(mIsAnswerShown);
				if(mAnswerIsTrue){
					mAnswerTextView.setText(R.string.true_button);
				}
				else{
					mAnswerTextView.setText(R.string.false_button);
				}
			}
		});
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInsantState){
		super.onSaveInstanceState(savedInsantState);
		Log.i(QuizActivity.TAG,"onSaveInstanceState - CheatActivity is called");
		savedInsantState.putBoolean(EXTRA_ANSWER_SHOWN,mIsAnswerShown);
		savedInsantState.putBoolean(EXTRA_ANSWER_IS_TRUE, mAnswerIsTrue);
	}
}
