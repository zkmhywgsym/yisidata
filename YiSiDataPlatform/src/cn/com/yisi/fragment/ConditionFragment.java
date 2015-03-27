package cn.com.yisi.fragment;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import cn.com.yisi.DateDialogFragment;
import cn.com.yisi.DateTimeActivity;
import cn.com.yisi.util.Constants;
import cn.com.ysdp.R;

//
public class ConditionFragment extends BaseFragment implements OnClickListener {
	private EditText ETstartTime, ETendTime, ETcarNum;//
	private Spinner Smateriel, Sstatus, ScarType;//
	public static final int REQUEST_START_TIME_CODE = 10021,
			REQUEST_END_TIME_CODE = 10022;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_condition_layout,
				container, false);
		initView();
		return rootView;
	}

	@Override
	public View getView() {
		return super.getView();
	}

	@Override
	public String getTitle() {
		int rid = Constants.TYPE_DETAILS.equals(getValue(getActivity().getIntent(),Constants.TYPE)) ? R.string.search_details_out
				: Constants.TYPE_GATHER.equals(getValue(getActivity().getIntent(),Constants.TYPE)) ? R.string.search_gather_out
						: R.string.search_contacts_details;
		return getResources().getString(rid);
	}

	private void initView() {
		flag=Constants.FLAG_RECEIVE;
		((TextView) rootView.findViewById(R.id.second_title1))
				.setOnClickListener(this);
		((TextView) rootView.findViewById(R.id.second_title2))
				.setOnClickListener(this);
		((TextView) rootView.findViewById(R.id.second_title3))
				.setOnClickListener(this);
		rootView.findViewById(R.id.btn_search).setOnClickListener(this);
		ETcarNum = (EditText) rootView.findViewById(R.id.et_car_num);
		ETendTime = (EditText) rootView.findViewById(R.id.et_end_time);
		ETstartTime = (EditText) rootView.findViewById(R.id.et_start_time);
		Smateriel = (Spinner) rootView.findViewById(R.id.s_materiel);
		Sstatus = (Spinner) rootView.findViewById(R.id.s_status);
		ScarType = (Spinner) rootView.findViewById(R.id.s_car_type);
		rootView.findViewById(R.id.btn_search).setOnClickListener(this);
		ETcarNum.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence chars, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence chars, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable editable) {
				if (editable != null
						&& !editable.toString().toUpperCase(Locale.CHINA)
								.equals(editable.toString())) {
					ETcarNum.setText(editable.toString().toUpperCase(
							Locale.CHINA));
				}
				Spannable t = editable;
				Selection.setSelection(t, editable.toString().length());
			}
		});
		ETstartTime.setText("2015-03-23 11:51");
		ETendTime.setText("2015-03-23 11:51");
		ETstartTime.setOnClickListener(this);
		ETendTime.setOnClickListener(this);
		ArrayAdapter<String> materielAdapter = new ArrayAdapter<String>(
				mainActivity, android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.materiel_type));
		materielAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Smateriel.setAdapter(materielAdapter);
		ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(
				mainActivity, android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.status));
		statusAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Sstatus.setAdapter(statusAdapter);
		ArrayAdapter<String> carTypeAdapter = new ArrayAdapter<String>(
				mainActivity, android.R.layout.simple_spinner_item,
				getResources().getStringArray(R.array.car_types));
		carTypeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ScarType.setAdapter(carTypeAdapter);
	}

	@Override
	public void onClick(View view) {
		FragmentTransaction ft = mainActivity.getSupportFragmentManager()
				.beginTransaction();
		Intent intent = mainActivity.getIntent();
		int id = view.getId();
		switch (id) {
		case R.id.second_title1:// in
			flag = Constants.FLAG_RECEIVE;
			break;
		case R.id.second_title2:// out
			flag = Constants.FLAG_SEND;
			break;
		case R.id.second_title3:// all
			break;
		case R.id.et_start_time://
			DateDialogFragment dialog1 = DateDialogFragment.newInstance();
			intent.putExtra("requestCode", REQUEST_START_TIME_CODE);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			dialog1.show(ft, "df");
			// startActivityForResult(intent, REQUEST_START_TIME_CODE);
			break;
		case R.id.et_end_time://
			DateDialogFragment dialog2 = DateDialogFragment.newInstance();
			intent.putExtra("requestCode", REQUEST_END_TIME_CODE);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			dialog2.show(ft, "df");
			// intent.putExtra("requestCode", REQUEST_END_TIME_CODE);
			// startActivityForResult(intent, REQUEST_END_TIME_CODE);
			break;
		case R.id.btn_search://
			Intent i = getActivity().getIntent();
			i.putExtra("startTime", ETstartTime.getText().toString());
			i.putExtra("endTime", ETendTime.getText().toString());
			i.putExtra("carNum", ETcarNum.getText().toString());
			i.putExtra("materiel", Smateriel.getSelectedItem().toString());
			i.putExtra("status", Sstatus.getSelectedItem().toString());
			BaseFragment fragment = Constants.TYPE_DETAILS
					.equals(getValue(getActivity().getIntent(),Constants.TYPE)) ? new DetailsFragment()
					: Constants.TYPE_GATHER.equals(getValue(getActivity().getIntent(),Constants.TYPE)) ? new GatherFragment()
							: new ContactsDetailsFragment();
			fragment.setValue(getActivity().getIntent(),Constants.FLAG, flag);
			String flag=fragment.getValue(getActivity().getIntent(),Constants.FLAG);
			System.out.println(flag);
			mainActivity.otherBackableFragment(fragment);
			break;
		}
	}

	@Override
	public void onResult(Intent intent) {
		String result = mainActivity.getIntent().getStringExtra("result");
		int requestCode = mainActivity.getIntent()
				.getIntExtra("requestCode", 0);
		if (REQUEST_START_TIME_CODE == requestCode) {
			ETstartTime.setText(result);
		} else if (REQUEST_END_TIME_CODE == requestCode) {
			ETendTime.setText(result);
		}
		super.onResult(intent);
	}
}
