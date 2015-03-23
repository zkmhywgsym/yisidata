package cn.com.jdsc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.com.util.ExitManager;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

//条件查询
public class SearchConditionActivity extends Activity implements OnClickListener {
	public static final int REQUEST_START_TIME_CODE=10021,REQUEST_END_TIME_CODE=10022;
	private EditText ETstartTime,ETendTime,ETcarNum;//开始时间，截止时间，车数
	private Spinner Smateriel,Sstatus;//物料，状态
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_condition_layout);
		initView();
		ExitManager.getInstance().addActivity(this);
	}
	private void initView(){
		ETcarNum=(EditText) findViewById(R.id.et_car_num);
		ETendTime=(EditText) findViewById(R.id.et_end_time);
		ETstartTime=(EditText) findViewById(R.id.et_start_time);
		Smateriel=(Spinner) findViewById(R.id.s_materiel);
		Sstatus=(Spinner) findViewById(R.id.s_status);
		findViewById(R.id.btn_search).setOnClickListener(this);;
		ETcarNum.setText(30+"");
		ETstartTime.setText("2015-03-23 11:51:30");
		ETendTime.setText("2015-03-23 11:51:30");
		ETstartTime.setOnClickListener(this);
		ETendTime.setOnClickListener(this);
		ArrayAdapter<String> materielAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.materiel_type));
		materielAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Smateriel.setAdapter(materielAdapter);
		ArrayAdapter<String> statusAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.status));
		statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Sstatus.setAdapter(statusAdapter);
		
//		BaseAdapter materielAdapter=new MySpinnerAdapter(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.materiel_type))));
//		new ArrayAdapter<String>(this, 0).set
//		Smateriel.setAdapter(materielAdapter);
//		SpinnerAdapter materielAdapter=new MySpinnerAdapter(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.materiel_type))));
//		Sstatus.setAdapter(new MySpinnerAdapter(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.status)))));
	}
	private class MySpinnerAdapter extends BaseAdapter implements SpinnerAdapter{

		private List<String> items;
		
		public MySpinnerAdapter(List<String> items) {
			super();
			this.items = items;
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int index) {
			return items.get(index);
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(int index, View view, ViewGroup root) {
			Holder holder;
			if (view==null) {
				view=LayoutInflater.from(root.getContext()).inflate(android.R.layout.simple_spinner_item, root,false);
				holder=new Holder();
				holder.setTv((TextView) view.findViewById(android.R.id.text1));
				view.setTag(holder);
			}else{
				holder=(Holder) view.getTag();
			}
			holder.getTv().setText(items.get(index));
			return view;
		}

		private class Holder{
			private TextView tv;

			public TextView getTv() {
				return tv;
			}

			public void setTv(TextView tv) {
				this.tv = tv;
			}
			
		}
	}
	@Override
	public void onClick(View view) {
		Intent intent=new Intent(this,DateActivity.class);
		int id=view.getId();
		switch (id) {
		case R.id.et_start_time://选择开始时间
			startActivityForResult(intent, REQUEST_START_TIME_CODE);
			break;
		case R.id.et_end_time://选择截止时间
			startActivityForResult(intent, REQUEST_END_TIME_CODE);
			break;
		case R.id.btn_search://查找
			intent=new Intent(this,ListDetailsActivity.class);
			intent.putExtra("startTime", ETstartTime.getText().toString());
			intent.putExtra("endTime", ETendTime.getText().toString());
			intent.putExtra("carNum", ETcarNum.getText().toString());
			intent.putExtra("materiel", Smateriel.getSelectedItem().toString());
			intent.putExtra("status", Sstatus.getSelectedItem().toString());
			startActivity(intent);
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case DateActivity.DATE_RESULT:
			data.setClass(this, TimeActivity.class);
			startActivityForResult(data, requestCode);
			break;
		case TimeActivity.TIME_RESULT:
			data.setClass(this, TimeActivity.class);
			switch (requestCode) {
			case REQUEST_START_TIME_CODE:
				ETstartTime.setText(data.getStringExtra("result"));
				break;
			case REQUEST_END_TIME_CODE:
				ETendTime.setText(data.getStringExtra("result"));
				break;
			}
			break;
		}
	}
}
