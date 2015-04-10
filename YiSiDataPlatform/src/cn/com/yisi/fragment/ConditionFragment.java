package cn.com.yisi.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import cn.com.yisi.YisiApp;
import cn.com.yisi.entity.EntityContidion;
import cn.com.yisi.util.Constants;
import cn.com.yisi.util.WebHelper;
import cn.com.ysdp.R;

//条件界面
public class ConditionFragment extends BaseFragment implements OnClickListener {
	private EditText ETstartTime, ETendTime, ETcarNum;//
	private Spinner Smateriel, Sstatus, Sperson;//
	private TextView TVperson, TVcarType;
	private List<EntityContidion> conditions;
	private EntityContidion enterCon, exitCon, curCon;
	private ArrayAdapter<String> materielAdapter, statusAdapter, personAdapter;
	private BaseAdapter carTypeAdapter;
	private final List<String> adapterValuesMater = new ArrayList<String>(),
			adapterValuesStatus = new ArrayList<String>(),
			adapterValuesCarType = new ArrayList<String>(),
			adapterValuesPerson = new ArrayList<String>();
	public static final int REQUEST_START_TIME_CODE = 10021,
			REQUEST_END_TIME_CODE = 10022;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_condition_layout,
				container, false);
		return rootView;
	}

	@Override
	public View getView() {
		return super.getView();
	}

	@Override
	public String getTitle() {
		int rid = Constants.TYPE_DETAILS.equals(getValue(getActivity()
				.getIntent(), Constants.TYPE)) ? R.string.search_details
				: Constants.TYPE_GATHER.equals(getValue(getActivity()
						.getIntent(), Constants.TYPE)) ? R.string.search_gather
						: R.string.search_contacts_details;
		return getResources().getString(rid);
	}

	@Override
	public void onStart() {
//		mainActivity.setIntent(new Intent());
		initView();
		update();
		flag = Constants.FLAG_RECEIVE;
		((RadioButton) rootView.findViewById(R.id.second_title1))
				.setChecked(true);
		super.onStart();
	}

	private void initView() {
		rootView.findViewById(R.id.second_title1).setOnClickListener(this);
		rootView.findViewById(R.id.second_title2).setOnClickListener(this);
		rootView.findViewById(R.id.second_title3).setOnClickListener(this);
		rootView.findViewById(R.id.btn_search).setOnClickListener(this);
		OnFocusChangeListener listener=new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				switch (v.getId()) {
				case R.id.et_start_time:
					if (hasFocus) {
						showTimeDialog("start");
					}
					break;
				case R.id.et_end_time:
					if (hasFocus) {
						showTimeDialog("end");
					}
					break;
				}
			}
		};
		ETcarNum = (EditText) rootView.findViewById(R.id.et_car_num);
		ETendTime = (EditText) rootView.findViewById(R.id.et_end_time);
		ETstartTime = (EditText) rootView.findViewById(R.id.et_start_time);
		ETstartTime.setOnFocusChangeListener(listener);
		ETendTime.setOnFocusChangeListener(listener);
		Smateriel = (Spinner) rootView.findViewById(R.id.s_materiel);
		Sstatus = (Spinner) rootView.findViewById(R.id.s_status);
		TVcarType = (TextView) rootView.findViewById(R.id.tv_car_type);
		Sperson = (Spinner) rootView.findViewById(R.id.s_person);
		TVperson = (TextView) rootView.findViewById(R.id.tv_person_label);
		rootView.findViewById(R.id.btn_search).setOnClickListener(this);
		if (Constants.TYPE_GATHER.equals(getValue(getActivity().getIntent(),
				Constants.TYPE))) {
			TVcarType.setVisibility(View.GONE);
			ETcarNum.setVisibility(View.GONE);
			rootView.findViewById(R.id.tv_car_label).setVisibility(View.GONE);
			Sstatus.setVisibility(View.GONE);
			rootView.findViewById(R.id.tv_status_label).setVisibility(View.GONE);
		}
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINA);
		Calendar cal = Calendar.getInstance();
		ETendTime.setText(sdf.format(cal.getTime()));
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		ETstartTime.setText(sdf.format(cal.getTime()));
		// ETstartTime.setText("2015-03-23 11:51");
		// ETendTime.setText("2015-03-23 11:51");
		ETstartTime.setOnClickListener(this);
		ETendTime.setOnClickListener(this);
		initData();
	}

	private void initData() {
		adapterValuesMater.addAll(Arrays.asList(getResources().getStringArray(
				R.array.materiel_type)));
		materielAdapter = new ArrayAdapter<String>(mainActivity,
				android.R.layout.simple_spinner_item, adapterValuesMater);
		materielAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Smateriel.setAdapter(materielAdapter);
		adapterValuesStatus.addAll(Arrays.asList(getResources().getStringArray(
				R.array.status)));
		statusAdapter = new ArrayAdapter<String>(mainActivity,
				android.R.layout.simple_spinner_item, adapterValuesStatus);
		statusAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Sstatus.setAdapter(statusAdapter);
		adapterValuesCarType.addAll(Arrays.asList(getResources()
				.getStringArray(R.array.car_types)));
		carTypeAdapter = new MyGridAdapter(adapterValuesCarType);
		// carTypeAdapter.
		// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		TVcarType.setOnClickListener(this);
		adapterValuesPerson.addAll(Arrays.asList(getResources().getStringArray(
				R.array.person)));
		personAdapter = new ArrayAdapter<String>(mainActivity,
				android.R.layout.simple_spinner_item, adapterValuesPerson);
		personAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Sperson.setAdapter(personAdapter);

	}

	private void update() {
		if (conditions == null) {
			AsyncTask<Void, Void, List<EntityContidion>> task = new AsyncTask<Void, Void, List<EntityContidion>>() {

				@Override
				protected void onPreExecute() {
					YisiApp.showProgressDialog(mainActivity, "加载中……", "正在加载中……");
					super.onPreExecute();
				}

				@Override
				protected List<EntityContidion> doInBackground(Void... params) {
					List<NameValuePair> ps = new ArrayList<NameValuePair>();
					ps.add(new BasicNameValuePair("flag", getValue(
							mainActivity.getIntent(), Constants.TYPE)));
					WebHelper<EntityContidion> helper = new WebHelper<EntityContidion>(
							EntityContidion.class);
					return helper.getArray(Constants.URL_CONDITION, ps);
				}

				@Override
				protected void onPostExecute(List<EntityContidion> result) {
					super.onPostExecute(result);
					YisiApp.disMissProgressDialog();
					if (result != null) {
						conditions = result;
						for (EntityContidion entityContidion : result) {
							if (entityContidion.getType() == Constants.TYPE_ENTER) {
								enterCon = entityContidion;
							} else if (entityContidion.getType() == Constants.TYPE_EXIT) {
								exitCon = entityContidion;
							}
						}
						update();
					} else {
						conditions = new ArrayList<EntityContidion>();
					}
				}

			};
			task.execute();
		}
		if (Constants.FLAG_RECEIVE.equals(flag)) {
			curCon = enterCon;
			TVperson.setText(getResources().getString(R.string.person_supply));
		} else {
			curCon = exitCon;
			TVperson.setText(getResources().getString(R.string.person_custom));
		}
		if (curCon != null) {
			if (curCon.getCars() != null) {
				adapterValuesCarType.clear();
				adapterValuesCarType.addAll(Arrays.asList(curCon.getCars()
						.split(",")));
				adapterValuesCarType.add("全部");
				carTypeAdapter.notifyDataSetChanged();
				// ScarType.setAdapter(carTypeAdapter);
			}
			if (curCon.getMaterials() != null) {
				adapterValuesMater.clear();
				adapterValuesMater.add("全部");
				adapterValuesMater.addAll(Arrays.asList(curCon
						.getMaterials()
						.values()
						.toArray(
								new String[curCon.getMaterials().values()
										.size()])));
				materielAdapter.notifyDataSetChanged();
			}
			if (curCon.getStatus() != null) {
				adapterValuesStatus.clear();
				adapterValuesStatus.add("全部");
				adapterValuesStatus.addAll(Arrays.asList(curCon.getStatus()
						.split(",")));
				statusAdapter.notifyDataSetChanged();
			}
			if (curCon.getPersons() != null) {
				adapterValuesPerson.clear();
				adapterValuesPerson.add("全部");
				adapterValuesPerson.addAll(Arrays
						.asList(curCon
								.getPersons()
								.values()
								.toArray(
										new String[curCon.getPersons().values()
												.size()])));
				personAdapter.notifyDataSetChanged();
			}
		}

	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.second_title1:// in
			flag = Constants.FLAG_RECEIVE;
			update();
			break;
		case R.id.second_title2:// out
			flag = Constants.FLAG_SEND;
			update();
			break;
		case R.id.second_title3:// all
			break;
		case R.id.tv_car_type://
			final Dialog dialog = new AlertDialog.Builder(mainActivity)
					.create();
			dialog.show();
			Window win = dialog.getWindow();
			win.setContentView(R.layout.dialog_list_grid);
			GridView gridview = (GridView) win
					.findViewById(R.id.dialog_gridview);
			gridview.setAdapter(carTypeAdapter);
			gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					TVcarType.setText(parent.getItemAtPosition(position)
							.toString());
					dialog.dismiss();
				}
			});
			break;
		case R.id.et_start_time://
			showTimeDialog("start");
			break;
		case R.id.et_end_time://
			showTimeDialog("end");
			break;
		case R.id.btn_search://
			Intent i = getActivity().getIntent();
			i.putExtra("startTime", ETstartTime.getText().toString());
			i.putExtra("endTime", ETendTime.getText().toString());
			i.putExtra(
					"materiel",
					getMapKeyByValue(Smateriel.getSelectedItem().toString(),
							curCon.getMaterials()));
			i.putExtra(
					"person",
					getMapKeyByValue(Sperson.getSelectedItem().toString(),
							curCon.getPersons()));
			if (Constants.TYPE_GATHER.equals(getValue(getActivity().getIntent(),
					Constants.TYPE))) {
				if (!"全部".equals(TVcarType.getText().toString())) {
					i.putExtra("carNum", TVcarType.getText().toString()
							+ ETcarNum.getText().toString());
				}
				i.putExtra("status", "全部".equals(Sstatus.getSelectedItem()
						.toString()) ? "" : Sstatus.getSelectedItem().toString());
			}
			BaseFragment fragment = Constants.TYPE_DETAILS.equals(getValue(
					getActivity().getIntent(), Constants.TYPE)) ? new DetailsFragment()
					: Constants.TYPE_GATHER.equals(getValue(getActivity()
							.getIntent(), Constants.TYPE)) ? new GatherFragment()
							: new ContactsDetailsFragment();
			fragment.setValue(getActivity().getIntent(), Constants.FLAG, flag);
			mainActivity.otherBackableFragment(fragment);
			break;
		}
	}
	//type="start"/"end";
	private void showTimeDialog(String type) {
		FragmentTransaction ft = mainActivity.getSupportFragmentManager()
				.beginTransaction();
		Intent intent = mainActivity.getIntent();
		DateDialogFragment dialog = DateDialogFragment.newInstance();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		dialog.show(ft, "df");
		if ("start".equals(type)) {
			intent.putExtra("requestCode", REQUEST_START_TIME_CODE);
			startActivityForResult(intent, REQUEST_START_TIME_CODE);
		}else if("end".equals(type)){
			intent.putExtra("requestCode", REQUEST_END_TIME_CODE);
			startActivityForResult(intent, REQUEST_END_TIME_CODE);
		}
		
	}
	//车省
	class MyGridAdapter extends BaseAdapter {
		private List<String> items;

		public MyGridAdapter(List<String> items) {
			super();
			this.items = items;
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			Holder holder;
			if (view == null) {
				view = LayoutInflater.from(parent.getContext()).inflate(
						android.R.layout.simple_gallery_item, parent, false);
				holder = new Holder();
				holder.tv = (TextView) view.findViewById(android.R.id.text1);
				view.setTag(holder);
			} else {
				holder = (Holder) view.getTag();
			}
			holder.tv.setText(items.get(position));
			return view;
		}

		class Holder {
			TextView tv;
		}

	}

	private String getMapKeyByValue(String value, Map<String, String> map) {
		if ("全部".equals(value)) {
			return "";
		}
		for (Entry<String, String> e : map.entrySet()) {
			if (value.equals(e.getValue())) {
				return e.getKey();
			}
		}
		return "";
	}

	@Override
	public void onResult(Intent intent) {
		String result = intent.getStringExtra("result");
		intent.removeExtra("result");
		if (TextUtils.isEmpty(result)) {
			return;
		}
		int requestCode = intent.getIntExtra("requestCode", 0);
		if (REQUEST_START_TIME_CODE == requestCode) {
			ETstartTime.setText(result);
		} else if (REQUEST_END_TIME_CODE == requestCode) {
			ETendTime.setText(result);
		}
		super.onResult(intent);
	}
}
