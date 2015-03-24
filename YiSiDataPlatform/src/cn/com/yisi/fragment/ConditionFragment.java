package cn.com.yisi.fragment;

import cn.com.jdsc.R;
import cn.com.yisi.DateActivity;
import cn.com.yisi.DateTimeActivity;
import cn.com.yisi.ListDetailsActivity;
import cn.com.yisi.MainActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//查询
public class ConditionFragment extends BaseFragment implements OnClickListener{
	private EditText ETstartTime,ETendTime,ETcarNum;//开始时间，截止时间，车数
	private Spinner Smateriel,Sstatus;//物料，状态
	public static final int REQUEST_START_TIME_CODE=10021,REQUEST_END_TIME_CODE=10022;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView=inflater.inflate(R.layout.activity_condition_layout, container,false);
		initView();
		return rootView;
	}
	@Override
	public View getView() {
		return super.getView();
	}
	@Override
	public String getTitle() {
		return getResources().getString(R.string.mode_search_conditions);
	}
	private void initView(){
		TextView TVtype=(TextView) rootView.findViewById(R.id.second_title);
		TVtype.setText("details".equals(mainActivity.type)?R.string.search_details_out:"gather".equals(mainActivity.type)?R.string.search_gather_out:R.string.search_contacts_details);
		rootView.findViewById(R.id.btn_search).setOnClickListener(this);
		ETcarNum=(EditText) rootView.findViewById(R.id.et_car_num);
		ETendTime=(EditText) rootView.findViewById(R.id.et_end_time);
		ETstartTime=(EditText) rootView.findViewById(R.id.et_start_time);
		Smateriel=(Spinner) rootView.findViewById(R.id.s_materiel);
		Sstatus=(Spinner) rootView.findViewById(R.id.s_status);
		rootView.findViewById(R.id.btn_search).setOnClickListener(this);;
		ETcarNum.setText(30+"");
		ETstartTime.setText("2015-03-23 11:51");
		ETendTime.setText("2015-03-23 11:51");
		ETstartTime.setOnClickListener(this);
		ETendTime.setOnClickListener(this);
		ArrayAdapter<String> materielAdapter=new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.materiel_type));
		materielAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Smateriel.setAdapter(materielAdapter);
		ArrayAdapter<String> statusAdapter=new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.status));
		statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Sstatus.setAdapter(statusAdapter);
	}
	@Override
	public void onClick(View view) {
		Intent intent=new Intent(mainActivity,DateTimeActivity.class);
		int id=view.getId();
		switch (id) {
		case R.id.et_start_time://选择开始时间
			startActivityForResult(intent, REQUEST_START_TIME_CODE);
			break;
		case R.id.et_end_time://选择截止时间
			startActivityForResult(intent, REQUEST_END_TIME_CODE);
			break;
		case R.id.btn_search://查找
			Intent i=getActivity().getIntent();
			i.putExtra("startTime", ETstartTime.getText().toString());
			i.putExtra("endTime", ETendTime.getText().toString());
			i.putExtra("carNum", ETcarNum.getText().toString());
			i.putExtra("materiel", Smateriel.getSelectedItem().toString());
			i.putExtra("status", Sstatus.getSelectedItem().toString());
			BaseFragment fragment="details".equals(mainActivity.type)?new DetailsFragment():"gather".equals(mainActivity.type)?new GatherFragment():new ContactsDetailsFragment();
			mainActivity.otherFragment(fragment);
			break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (DateTimeActivity.TIME_RESULT==resultCode) {
			if (REQUEST_START_TIME_CODE==requestCode) {
				ETstartTime.setText(data.getStringExtra("result"));
			}else if(REQUEST_END_TIME_CODE==requestCode){
				ETendTime.setText(data.getStringExtra("result"));
			}
		}
	}
}
