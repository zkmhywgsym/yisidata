package cn.com.yisi;

import java.util.Calendar;

import cn.com.jdsc.R;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelAdapter;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DateActivity extends DialogActivity {
	public static final int DATE_RESULT=10011;
	@Override
	public void init() {
//		setContentView(R.layout.wheel_view_date_layout);
		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		contentLayout.addView(LayoutInflater.from(this).inflate(R.layout.wheel_view_date_layout, contentLayout,false),params);
		Calendar calendar = Calendar.getInstance();

		final WheelView month = (WheelView) findViewById(R.id.month);
		final WheelView year = (WheelView) findViewById(R.id.year);
		final WheelView day = (WheelView) findViewById(R.id.day);
		findViewById(R.id.confirm_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent data=getIntent();
				data.putExtra("result", year.getViewAdapter().getCurentValue(year.getCurrentItem())+"-"+month.getViewAdapter().getCurentValue(month.getCurrentItem())+"-"+day.getViewAdapter().getCurentValue(day.getCurrentItem()));
				setResult(DATE_RESULT, data);
				finish();
			}
		});
		findViewById(R.id.cancel_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent data=getIntent();
//				data.putExtra("result", "操作取消");
				setResult(DATE_RESULT, data);
				finish();
			}
		});

		OnWheelChangedListener listener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month, day);
			}
		};

		// month
		int curMonth = calendar.get(Calendar.MONTH);
		String months[] = new String[] {"01", "02", "03", "04", "05",
				"06", "07", "08", "09", "10", "11", "12"};
//		String months[] = new String[] {"一月", "二月", "三月", "四月", "五月",
//				"六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
		month.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
		month.setCurrentItem(curMonth);
		month.addChangingListener(listener);

		// year
		int curYear = calendar.get(Calendar.YEAR);
		year.setViewAdapter(new DateNumericAdapter(this, curYear-10, curYear + 10, 0));
		
		year.setCurrentItem(10);
		year.addChangingListener(listener);

		//day
		updateDays(year, month, day);
		day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
	}

	/**
	 * Updates day wheel. Sets max days according to selected month and year
	 */
	void updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	private class DateArrayAdapter extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}
}
