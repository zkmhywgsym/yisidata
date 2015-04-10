package cn.com.yisi.fragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.yisi.YisiApp;
import cn.com.yisi.entity.EntityGather;
import cn.com.yisi.util.Constants;
import cn.com.yisi.util.WebHelper;
import cn.com.ysdp.R;

//汇总
public class GatherFragment extends BaseFragment implements IXListViewListener {
	private XListView listView;
	private List<EntityGather> items;
	private BaseAdapter adapter;
	private List<NameValuePair> params;
	private int curPage = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		flag = getValue(mainActivity.getIntent(), Constants.FLAG);
		rootView = LayoutInflater.from(getActivity()).inflate(
				R.layout.activity_list_details_layout, container, false);
		listView = (XListView) rootView.findViewById(R.id.list_view);
		rootView.findViewById(R.id.foot_view).setVisibility(View.GONE);
		listView.setXListViewListener(this);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setAdapter(adapter = new MyListAdapter(items = getData()));
		return rootView;
	}

	@Override
	public String getTitle() {
		return getResources().getString(R.string.mode_gather);
	}

	private List<EntityGather> getData() {
		List<EntityGather> list = new ArrayList<EntityGather>();
		// EntityGather entity=null;
		// for (int i = 0; i < 10; i++) {
		// entity=new EntityGather();
		// entity.setCarNum((i+1)+"");
		// entity.setTime("2015-03-24 11:37");
		// entity.setMaterialType("原煤");
		// entity.setCompany("中煤集团");
		// entity.setWeight((i+1)+"");
		// entity.setType("send");
		// entity.setStatue("已发货");
		// list.add(entity);
		// }
		initData();
		return list;
	}

	private class MyListAdapter extends BaseAdapter {

		private List<EntityGather> list;

		public MyListAdapter(List<EntityGather> list) {
			super();
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int index) {
			return list.get(index);
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(int index, View view, ViewGroup root) {
			Holder holder = null;
			if (view == null) {
				view = LayoutInflater.from(root.getContext()).inflate(
						R.layout.item_list_details_layout, root, false);
				holder = new Holder();
				holder.carNumLabel = (TextView) view
						.findViewById(R.id.tv_label_car_num);
				holder.carNumValue = (TextView) view
						.findViewById(R.id.tv_car_num_value);
				holder.meteriel = (TextView) view
						.findViewById(R.id.tv_materiel_value);
				holder.providerLabel = (TextView) view
						.findViewById(R.id.tv_privader_label);
				holder.providerValue = (TextView) view
						.findViewById(R.id.tv_provider_value);
				holder.timeLabel = (TextView) view
						.findViewById(R.id.tv_label_time);
				holder.timeValue = (TextView) view
						.findViewById(R.id.tv_time_value);
				holder.weight = (TextView) view
						.findViewById(R.id.tv_value_weight);
				holder.statue = (TextView) view
						.findViewById(R.id.tv_statue_value);
				holder.statueLabel = (TextView) view
						.findViewById(R.id.tv_label_statue);
				view.setTag(holder);
			} else {
				holder = (Holder) view.getTag();
			}
			EntityGather entity = list.get(index);
			holder.carNumLabel.setText(getResources().getString(
					R.string.details_carNum));
			if ("send".equals(flag)) {
				holder.providerLabel.setText(getResources().getString(
						R.string.details_client));
				holder.timeLabel.setText(getResources().getString(
						R.string.details_time_weight_label));

			} else if ("receive".equals(flag)) {
				holder.providerLabel.setText(getResources().getString(
						R.string.details_provider));
				holder.timeLabel.setText(getResources().getString(
						R.string.details_time_light_label));

			}
			holder.meteriel.setText(entity.getMaterialType());
			holder.providerValue.setText(entity.getCompany());
			holder.weight.setText(entity.getWeight());
			holder.carNumValue.setText(entity.getCarTimes());
			holder.timeValue.setVisibility(View.GONE);
			holder.timeLabel.setVisibility(View.GONE);
			holder.statue.setVisibility(View.GONE);
			holder.statueLabel.setVisibility(View.GONE);
			return view;
		}

		private class Holder {
			private TextView providerLabel;
			private TextView providerValue;
			private TextView carNumLabel;
			private TextView carNumValue;
			private TextView meteriel;
			private TextView timeLabel;
			private TextView timeValue;
			private TextView weight;
			private TextView statue;
			private TextView statueLabel;
		}

	}

	private void onLoad(List<EntityGather> result) {
		items.clear();
		items.addAll(result);
		listView.stopRefresh();
		listView.stopLoadMore();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoadMore() {
		curPage++;
		AsyncTask<Void, Integer, List<EntityGather>> task=new AsyncTask<Void, Integer, List<EntityGather>>(){
			@Override
			protected void onPreExecute() {
				YisiApp.showProgressDialog(mainActivity, "请稍候", "加载中……");
				super.onPreExecute();
			}
			@Override
			protected List<EntityGather> doInBackground(Void... arg0) {
				WebHelper<EntityGather> helper=new WebHelper<EntityGather>(EntityGather.class);
				List<NameValuePair> params=getParams();
				params.add(new BasicNameValuePair("page",curPage+""));
				return helper.getArray(Constants.URL_DETAILS_LIST, params);
			}
			@Override
			protected void onPostExecute(List<EntityGather> results) {
				YisiApp.disMissProgressDialog();
				List<EntityGather> list=new ArrayList<EntityGather>();
				list.addAll(items);
				list.addAll(results);
				onLoad(list);
				super.onPostExecute(results);
			}
		};
		task.execute();
	}

	@Override
	public void onStart() {
		curPage = 1;
		super.onStart();
	}

	// 加载参数
	private void addParam(String name) {
		Intent i = mainActivity.getIntent();
		if (!TextUtils.isEmpty(i.getStringExtra(name))) {
			params.add(new BasicNameValuePair(name, i.getStringExtra(name)));
		}
	}

	// 获取参数
	private List<NameValuePair> getParams() {
		if (params != null) {
			return params;
		}
		params = new ArrayList<NameValuePair>();
		addParam("startTime");
		addParam("endTime");
		addParam("materiel");
		addParam("person");
		if (Constants.FLAG_RECEIVE.equals(flag)) {
			params.add(new BasicNameValuePair("type", Constants.TYPE_ENTER + ""));
		} else if (Constants.FLAG_SEND.equals(flag)) {
			params.add(new BasicNameValuePair("type", Constants.TYPE_EXIT + ""));
		}
		return params;
	}

	// 加载第一页数据
	private void initData() {
		listView.setRefreshTime(DateFormat.getDateTimeInstance().format(
				new Date()));
		curPage = 1;
		AsyncTask<Void, Integer, List<EntityGather>> task = new AsyncTask<Void, Integer, List<EntityGather>>() {
			@Override
			protected void onPreExecute() {
				YisiApp.showProgressDialog(mainActivity, "请稍候", "加载中……");
				super.onPreExecute();
			}

			@Override
			protected List<EntityGather> doInBackground(Void... arg0) {
				WebHelper<EntityGather> helper = new WebHelper<EntityGather>(
						EntityGather.class);
				List<NameValuePair> params = getParams();
				params.add(new BasicNameValuePair("page", curPage + ""));
				return helper.getArray(Constants.URL_GATHER_LIST, params);
			}

			@Override
			protected void onPostExecute(List<EntityGather> results) {
				YisiApp.disMissProgressDialog();
				List<EntityGather> list = new ArrayList<EntityGather>();
				list.addAll(results);
				onLoad(list);
				super.onPostExecute(results);
			}
		};
		task.execute();

	}

	@Override
	public void onRefresh() {
		initData();
	}
}
