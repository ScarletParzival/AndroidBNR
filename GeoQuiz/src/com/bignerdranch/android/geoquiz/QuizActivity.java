package com.bignerdranch.android.geoquiz;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
	
	public static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";
	private static final String USER_CHEATED = "userCheated";
	
	private Button mNoButton; 	// m - member variable
	private Button mYesButton;
	private Button mNextButton;
	private Button mPrevButton;
	private Button mCheatButton;
	private TextView mQuestionTextView;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[]{
		new TrueFalse(R.string.question_oceans, true),
		new TrueFalse(R.string.question_mideast, false),
		new TrueFalse(R.string.question_africa, false),
		new TrueFalse(R.string.question_americas,true),
		new TrueFalse(R.string.question_asia, true)
	};
	
	private int mCurrentIndex = 0;
	private boolean mIsCheater;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(data == null){
			return;
		}
		mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
	}
	
	private void updateQuestion(){
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue){
		boolean isAnswerTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		
		int messageRId = 0;
		
		if(mIsCheater){
			messageRId = R.string.judgement_toast;
		}
		else{
			if(userPressedTrue == isAnswerTrue){
				messageRId = R.string.correct_toast;
			}
			else{
				messageRId = R.string.incorrect_toast;
			}
		}
		Toast.makeText(getApplicationContext(), messageRId, Toast.LENGTH_SHORT).show();
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle) called");
		setContentView(R.layout.activity_quiz);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			ActionBar actionBar = getActionBar();
			actionBar.setSubtitle("Bodies of Water");	
		}
		
		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
		updateQuestion();
		
		mNoButton = (Button) findViewById(R.id.false_button);
		mNoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
		
		mYesButton = (Button) findViewById(R.id.true_button);
		mYesButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
		
		mNextButton = (Button) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
			}
		});
		
		mPrevButton = (Button) findViewById(R.id.prev_button);
		mPrevButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mCurrentIndex == 0){
					mCurrentIndex = mQuestionBank.length - 1;
				}
				else{
					mCurrentIndex = (mCurrentIndex-1);	
				}
				mIsCheater = false;
				updateQuestion();
			}
		});
		
		mCheatButton = (Button)findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivityForResult(i,0);
			}
		});
		
		
		if(savedInstanceState != null){
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
			mIsCheater = savedInstanceState.getBoolean(USER_CHEATED);
		}
		
		updateQuestion();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "onSaveInstancState");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
		savedInstanceState.putBoolean(USER_CHEATED, mIsCheater);	
	}
	
	@Override
	public void onStart(){
		super.onStart();
		Log.d(TAG, "onStart() called");
	}
	
	@Override
	public void onPause(){
		super.onPause();
		Log.d(TAG, "onPause() called");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.d(TAG, "onResume() called");
	}
	
	@Override
	public void onStop(){
		super.onStop();
		Log.d(TAG, "onStop() called");
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}
}
