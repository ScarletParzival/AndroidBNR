package com.bignerdranch.android.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerFragment extends DialogFragment {
	private static final String DEBUG_DATE_PICKER = "debug_date_picker_fragment";
	public static final String EXTRA_DATE = 
			"com.bignerdranch.android.criminalintent.date";
	private Date mDate;
	
	public static DatePickerFragment newInstance(Date date){
		Log.d(DEBUG_DATE_PICKER, "newInstance method is called");
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	private void sendResult(int resultCode){
		if(getTargetFragment() == null){
			return;
		}
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		Log.d(DEBUG_DATE_PICKER, "onCreateDialog is created!");
		
		mDate = (Date) getArguments().getSerializable(EXTRA_DATE);
		
		//Create a Calendar to extract the date components.
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
		
		DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
				getArguments().putSerializable(EXTRA_DATE, mDate);			
			}
		});
		return new AlertDialog.Builder(getActivity()).
				setView(v).
				setTitle(R.string.date_picker_title).
				setPositiveButton(android.R.string.ok, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {	
						sendResult(Activity.RESULT_OK);
					}
				}).
				create();
	}
}
