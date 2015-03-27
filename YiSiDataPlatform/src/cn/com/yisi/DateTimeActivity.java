package cn.com.yisi;

import java.util.Calendar;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.ysdp.R;

public class DateTimeActivity extends DialogActivity {
	public static final int DATE_RESULT = 10011;
	public static final int TIME_RESULT = 10012;

	// Time scrolled flag
	private boolean timeScrolled = false;

	@Override
	public void init() {
		// setContentView(R.layout.wheel_view_date_layout);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		contentLayout.addView(
				LayoutInflater.from(this).inflate(
						R.layout.wheel_view_date_time_layout, contentLayout,
						false), params);
		Calendar calendar = Calendar.getInstance();

		final WheelView month = (WheelView) findViewById(R.id.month);
		final WheelView year = (WheelView) findViewById(R.id.year);
		final WheelView day = (WheelView) findViewById(R.id.day);

		OnWheelChangedListener listener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month, day);
			}
		};

		// month
		int curMonth = calendar.get(Calendar.MONTH);
		String months[] = new String[] { "01", "02", "03", "04", "05", "06",
				"07", "08", "09", "10", "11", "12" };
		month.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
		month.setCurrentItem(curMonth);
		month.getViewAdapter().setCurrentIndex(curMonth);
		month.addChangingListener(listener);

		// year
		int curYear = calendar.get(Calendar.YEAR);
		year.setViewAdapter(new DateNumericAdapter(this, curYear - 10,
				curYear + 10, 0));

		year.setCurrentItem(10);
		year.getViewAdapter().setCurrentIndex(10);
		year.addChangingListener(listener);

		// day
		updateDays(year, month, day);
		day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
		final WheelView hours = (WheelView) findViewById(R.id.hour);
		hours.setViewAdapter(new DateNumericAdapter(this, 0, 23,0));

		final WheelView mins = (WheelView) findViewById(R.id.mins);
		mins.setViewAdapter(new DateNumericAdapter(this, 0, 59,0, "%02d"));
		mins.setCyclic(true);
		year.setCyclic(true);
		month.setCyclic(true);
		day.setCyclic(true);
		hours.setCyclic(true);

		findViewById(R.id.confirm_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						Intent data = getIntent();
						data.putExtra("result", data.getStringExtra("result"));
						String time = year.getViewAdapter().getCurentValue(
								year.getCurrentItem())
								+ "-"
								+ month.getViewAdapter().getCurentValue(
										month.getCurrentItem())
								+ "-"
								+ day.getViewAdapter().getCurentValue(
										day.getCurrentItem())
								+ " "
								+ hours.getViewAdapter().getCurentValue(
										hours.getCurrentItem())
								+ ":"
								+ mins.getViewAdapter().getCurentValue(
										mins.getCurrentItem());
						data.putExtra("result", time);
						setResult(TIME_RESULT, data);
						finish();
					}
				});
		findViewById(R.id.cancel_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						Intent data = getIntent();
						data.putExtra("result", data.getStringExtra("result")
								+ " 00:00");
						setResult(TIME_RESULT, data);
						finish();
					}
				});

		// set current time
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR_OF_DAY);
		int curMinutes = c.get(Calendar.MINUTE);

		hours.setCurrentItem(curHours);
		hours.getViewAdapter().setCurrentIndex(curHours);
		mins.setCurrentItem(curMinutes);
		mins.getViewAdapter().setCurrentIndex(curMinutes);

		// add listeners
		addChangingListener(mins, "min");
		addChangingListener(hours, "hour");

		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
				}
			}
		};
		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);

		OnWheelClickedListener click = new OnWheelClickedListener() {
			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {
				wheel.setCurrentItem(itemIndex, true);
			}
		};
		hours.addClickingListener(click);
		mins.addClickingListener(click);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				wheel.getViewAdapter().setCurrentIndex(wheel.getCurrentItem());
				wheel.getViewAdapter().notifyDataSetChanged();;
			}
		};

		year.addScrollingListener(scrollListener);
		month.addScrollingListener(scrollListener);
		day.addScrollingListener(scrollListener);
		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);
	}

	/**
	 * Updates day wheel. Sets max days according to selected month and year
	 */
	void updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,
				calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar
				.get(Calendar.DAY_OF_MONTH) - 1));
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
		day.getViewAdapter().setCurrentIndex(calendar
				.get(Calendar.DAY_OF_MONTH) - 1);
		day.getViewAdapter().notifyDataSetChanged();
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
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			this(context, minValue, maxValue, current, null);
		}
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current,String format) {
			super(context, minValue, maxValue, format);
			this.currentValue = current;
			setTextSize(20);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(Color.RED);
				view.getPaint().setFakeBoldText(true);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
		@Override
		public void setCurrentIndex(int index) {
			currentValue=index;
		}
		@Override
		public int getCurrentIndex() {
			return currentValue;
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
			setTextSize(20);
		}

		@Override
		public void setCurrentIndex(int index) {
			currentValue=index;
		}
		@Override
		public int getCurrentIndex() {
			return currentValue;
		}
		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.getPaint().setFakeBoldText(true);
				view.setTextColor(Color.RED);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	}
}
