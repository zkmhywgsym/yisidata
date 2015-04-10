package cn.com.yisi.fragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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
import cn.com.yisi.entity.EntityDetails;
import cn.com.yisi.entity.EntityStatistics;
import cn.com.yisi.service.Service;
import cn.com.yisi.util.Constants;
import cn.com.yisi.util.Marquee;
import cn.com.yisi.util.WebHelper;
import cn.com.ysdp.R;
//明细
public class DetailsFragment extends BaseFragment implements IXListViewListener{
	private XListView listView;
	private TextView TVcarCount,TVweightCount;
	private List<EntityDetails> items=new ArrayList<EntityDetails>();
	private BaseAdapter adapter;
	private List<NameValuePair> params;
	private int curPage=1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onStart() {
		curPage=1;
		super.onStart();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		flag=getValue(mainActivity.getIntent(),Constants.FLAG);
		rootView=LayoutInflater.from(getActivity()).inflate(R.layout.activity_list_details_layout, container,false);
		listView=(XListView) rootView.findViewById(R.id.list_view);
		TVcarCount=(TextView) rootView.findViewById(R.id.TVcarCount);
		TVweightCount=(TextView) rootView.findViewById(R.id.TVweightCount);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(this);
		getParams();
		listView.setAdapter(adapter=new MyListAdapter(items));
//		testData1();
		initDate();
		
		return rootView;
	}
	//获取参数
	private List<NameValuePair> getParams() {
		if (params!=null) {
			return params;
		}
		params=new ArrayList<NameValuePair>();
		addParam("startTime");
		addParam("endTime");
		addParam("carNum");
		addParam("materiel");
		addParam("status");
		addParam("person");
//		params.add(new BasicNameValuePair("startTime", i.getStringExtra("startTime")));
//		params.add(new BasicNameValuePair("endTime", i.getStringExtra("endTime")));
//		params.add(new BasicNameValuePair("carNum", i.getStringExtra("carNum")));
//		params.add(new BasicNameValuePair("materiel", i.getStringExtra("materiel")));
//		params.add(new BasicNameValuePair("status", i.getStringExtra("status")));
//		params.add(new BasicNameValuePair("person", i.getStringExtra("person")));
		if (Constants.FLAG_RECEIVE.equals(flag)) {
			params.add(new BasicNameValuePair("type", Constants.TYPE_ENTER+""));
		}else if (Constants.FLAG_SEND.equals(flag)){
			params.add(new BasicNameValuePair("type", Constants.TYPE_EXIT+""));
		}
		return params;
	}
	//加载参数
	private void addParam(String name){
		Intent i=mainActivity.getIntent();
		if (!TextUtils.isEmpty(i.getStringExtra(name))) {
			params.add(new BasicNameValuePair(name,i.getStringExtra(name)));
		}
	}
	@Override
	public String getTitle() {
		return getResources().getString(R.string.mode_details);
	}
//	private List<EntityDetails> testData(){
//		List<EntityDetails> list=new ArrayList<EntityDetails>();
//		EntityDetails entity=null;
//		for (int i = 0; i < 8; i++) {
//			entity=new EntityDetails();
//			entity.setCarCode("晋A5384"+i);
//			entity.setTime("2015-03-24 11:37");
//			entity.setMaterialType("原煤");
//			entity.setCompany("中煤集团");
//			entity.setWeight((i+1)+"");
//			entity.setStatue("已发货");
//			list.add(entity);
//		}
//		return list;
//	}
//	private List<EntityDetails> testData1(){
//		List<EntityDetails> list=new ArrayList<EntityDetails>();
//		return list;
//	}
	//列表适配
	private class MyListAdapter extends BaseAdapter{

		private List<EntityDetails> list;
		
		public MyListAdapter(List<EntityDetails> list) {
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
			Holder holder=null;
			if (view==null) {
				view=LayoutInflater.from(root.getContext()).inflate(R.layout.item_list_details_layout, root,false);
				holder=new Holder();
				holder.carNumLabel=(TextView) view.findViewById(R.id.tv_label_car_num);
				holder.carNumValue=(TextView) view.findViewById(R.id.tv_car_num_value);
				holder.meteriel=(Marquee) view.findViewById(R.id.tv_materiel_value);
				holder.providerLabel=(TextView) view.findViewById(R.id.tv_privader_label);
				holder.providerValue=(Marquee) view.findViewById(R.id.tv_provider_value);
				holder.timeLabel=(TextView) view.findViewById(R.id.tv_label_time);
				holder.timeValue=(Marquee) view.findViewById(R.id.tv_time_value);
				holder.weight=(TextView) view.findViewById(R.id.tv_value_weight);
				holder.statue=(TextView) view.findViewById(R.id.tv_statue_value);
				view.setTag(holder);
			}else{
				holder=(Holder) view.getTag();
			}
			EntityDetails entity=list.get(index);
			holder.carNumLabel.setText(getResources().getString(R.string.details_carCode));
			if (Constants.FLAG_RECEIVE.equals(getValue(mainActivity.getIntent(),Constants.FLAG))) {
				holder.providerLabel.setText(getResources().getString(R.string.details_client));
				holder.timeLabel.setText(getResources().getString(R.string.details_time_light_label));
				
			}else if (Constants.FLAG_SEND.equals(getValue(mainActivity.getIntent(),Constants.FLAG))) {
				holder.providerLabel.setText(getResources().getString(R.string.details_provider));
				holder.timeLabel.setText(getResources().getString(R.string.details_time_weight_label));
				
			}
			holder.meteriel.setText(entity.getMaterialType());
			holder.providerValue.setText(entity.getCompany());
			holder.weight.setText(entity.getWeight());
			holder.carNumValue.setText(entity.getCarCode());
			holder.timeValue.setText(entity.getTime());
			holder.statue.setText(entity.getStatus());
			return view;
		}
		private class Holder{
			private TextView providerLabel;
			private Marquee providerValue;
			private TextView carNumLabel;
			private TextView carNumValue;
			private Marquee meteriel;
			private TextView timeLabel;
			private Marquee timeValue;
			private TextView weight;
			private TextView statue;
		}
		
	}
	//刷新
	@Override
	public void onRefresh() {
		initDate();
	}
	//加载第一页数据
	private void initDate(){
		listView.setRefreshTime(DateFormat.getDateTimeInstance().format(new Date()));
		curPage=1;
		AsyncTask<Void, Integer, List<EntityDetails>> task=new AsyncTask<Void, Integer, List<EntityDetails>>(){
			@Override
			protected void onPreExecute() {
				YisiApp.showProgressDialog(mainActivity, "请稍候", "加载中……");
				super.onPreExecute();
			}
			@Override
			protected List<EntityDetails> doInBackground(Void... arg0) {
				WebHelper<EntityDetails> helper=new WebHelper<EntityDetails>(EntityDetails.class);
				List<NameValuePair> params=getParams();
				params.add(new BasicNameValuePair("page",curPage+""));
				return helper.getArray(Constants.URL_DETAILS_LIST, params);
			}
			@Override
			protected void onPostExecute(List<EntityDetails> results) {
				YisiApp.disMissProgressDialog();
				List<EntityDetails> list=new ArrayList<EntityDetails>();
				list.addAll(results);
				onLoad(list);
				statistics();
				super.onPostExecute(results);
			}
		};
		task.execute();
		
	}
	//统计
	private void statistics(){
		AsyncTask<Void, Integer, EntityStatistics> task=new AsyncTask<Void, Integer, EntityStatistics>(){
			@Override
			protected void onPreExecute() {
				YisiApp.showProgressDialog(mainActivity, "请稍候", "加载中……");
				super.onPreExecute();
			}
			@Override
			protected EntityStatistics doInBackground(Void... arg0) {
				WebHelper<EntityStatistics> helper=new WebHelper<EntityStatistics>(EntityStatistics.class);
				List<NameValuePair> params=getParams();
				return helper.getObject(Constants.URL_DETAILS_STATISTICS, params);
			}
			@Override
			protected void onPostExecute(EntityStatistics result) {
				YisiApp.disMissProgressDialog();
				if (result==null) {
					TVcarCount.setText("-1");
					TVweightCount.setText("-1");
				}else{
					TVcarCount.setText(result.getCount()+"");
					TVweightCount.setText(result.getWeightCount()+"");
				}
				super.onPostExecute(result);
			}
		};
		task.execute();
	}
	//加载完成
	private void onLoad(List<EntityDetails> result){
		items.clear();
		items.addAll(result);
		listView.stopRefresh();
		listView.stopLoadMore();
		adapter.notifyDataSetChanged();
	}
	//加载更多
	@Override
	public void onLoadMore() {
		curPage++;
		AsyncTask<Void, Integer, List<EntityDetails>> task=new AsyncTask<Void, Integer, List<EntityDetails>>(){
			@Override
			protected void onPreExecute() {
				YisiApp.showProgressDialog(mainActivity, "请稍候", "加载中……");
				super.onPreExecute();
			}
			@Override
			protected List<EntityDetails> doInBackground(Void... arg0) {
				WebHelper<EntityDetails> helper=new WebHelper<EntityDetails>(EntityDetails.class);
				List<NameValuePair> params=getParams();
				params.add(new BasicNameValuePair("page",curPage+""));
				return helper.getArray(Constants.URL_DETAILS_LIST, params);
			}
			@Override
			protected void onPostExecute(List<EntityDetails> results) {
				YisiApp.disMissProgressDialog();
				List<EntityDetails> list=new ArrayList<EntityDetails>();
				list.addAll(items);
				list.addAll(results);
				onLoad(list);
				super.onPostExecute(results);
			}
		};
		task.execute();
	}
}
