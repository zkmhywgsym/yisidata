package cn.com.yisi.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import cn.com.yisi.YisiApp;
import cn.com.yisi.entity.EntityGather;
import cn.com.yisi.entity.EntityHomtItemParent;
import cn.com.yisi.entity.EntityTip;
import cn.com.yisi.util.Constants;
import cn.com.yisi.util.WebHelper;
import cn.com.yisi.widget.chart.LineChartView;
import cn.com.yisi.widget.chart.MyChartSetEntity;
import cn.com.yisi.widget.chart.MyLineSet;
import cn.com.yisi.widget.chart.Tools;
import cn.com.ysdp.R;

//主页
public class HomeFragment extends BaseLinearChartFragment implements
		OnItemClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = LayoutInflater.from(getActivity()).inflate(
				R.layout.frame_home_layout, container, false);
		currentValues=initValues();
		currentSets=initLineSets();
		initView();
		initLineChart((LineChartView) rootView.findViewById(R.id.linechart));
		return rootView;
	}

	private void initView() {
		GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
		gridview.setAdapter(new MyGridAdapter(getTestData()));
		gridview.setOnItemClickListener(this);
		GridView gridViewTip = (GridView) rootView.findViewById(R.id.gv_tips);
		gridViewTip.setAdapter(new MyGridTipAdapter(getTestTipData()));
		gridViewTip.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int id,
					long arg3) {
				showSingelLine(id);
			}
		});
	}
	@Override
	protected MyChartSetEntity getLineSets() {
		return initLineSets();
	}

	private MyChartSetEntity initLineSets(){
		MyChartSetEntity sets = new MyChartSetEntity(mainActivity);
		ArrayList<MyLineSet> lineSets = new ArrayList<MyLineSet>();
		MyLineSet set = null;
		for (int i = 0; i < 6; i++) {
			set=new MyLineSet(mainActivity);
			set.setLineColor(defaultColors[i]);
			set.setDotsRadius(Tools.fromDpToPx(1));
			lineSets.add(set);
		}
		sets.setAxisBorderMin(0);
		sets.setAxisBorderMax(20);
		sets.setLineSets(lineSets);
		return sets;
	}
	@Override
	protected String[] setChartLabels() {
		Calendar cal=Calendar.getInstance(Locale.CHINA);
//		String[] lables = new String[] {"周日","周一", "周二", "周三", "周四", "周五", "周六",};
//		int dayOfWeek=Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;//昨天周几
		String[] ret = new String[7];
		for (int i = 0; i < 7; i++) {
//			dayOfWeek--;
//			dayOfWeek=dayOfWeek<0?dayOfWeek+7:dayOfWeek;
//			ret[i]=lables[dayOfWeek];
			cal.roll(Calendar.DAY_OF_MONTH, -1);
			ret[i]=cal.get(Calendar.DAY_OF_MONTH)+"日";
		}
		return ret;
	}

	@Override
	protected float[][] setChartValues() {
		return currentValues;
	}

	private float[][] initValues() {
		List<float[]> datas = new ArrayList<float[]>();
		// tese data
//		Random ran = new Random();
//		float[] val;
//		for (int i = 0; i < 6; i++) {
//			val = new float[7];
//			for (int j = 0; j < 7; j++) {
//				val[j] = ran.nextInt(15);
//			}
//			datas.add(val);
//		}
		final float[][] values = new float[2][7];
//		for (int i = 0; i < datas.size(); i++) {
//			values[i] = datas.get(i);
//		}
		// tese data
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("type",Constants.TYPE_ALL+""));
		AsyncTask<List<NameValuePair>, Integer, List<EntityGather>> task=new AsyncTask<List<NameValuePair>, Integer, List<EntityGather>>(){

			@Override
			protected void onPreExecute() {
				YisiApp.showProgressDialog(mainActivity, "请稍候……", "正在加载中……");
				super.onPreExecute();
			}
			@Override
			protected List<EntityGather> doInBackground(List<NameValuePair>... params) {
				WebHelper<EntityGather> helper=new WebHelper<EntityGather>(EntityGather.class);
				return helper.getArray(Constants.URL_CUR_WEIGHT, params[0]);
			}
			
			@Override
			protected void onPostExecute(List<EntityGather> result) {
				float[] fEnter=new float[7];
				float[] fExit=new float[7];
				Map<String, EntityGather> enter=new HashMap<String, EntityGather>();
				Map<String, EntityGather> exit=new HashMap<String, EntityGather>();
				if(result!=null){
					for (EntityGather e : result) {
						if ((Constants.TYPE_ENTER+"").equals(e.getType())) {
							enter.put(e.getTime(), e);
						}else if((Constants.TYPE_EXIT+"").equals(e.getType())){
							exit.put(e.getTime(), e);
						}
					}
					Calendar c=Calendar.getInstance();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
					String key;
					for (int i = 0; i < 7; i++) {
						c.add(Calendar.DAY_OF_MONTH, -1);
						key=sdf.format(c.getTime());
						EntityGather e=enter.get(key);
						fEnter[i]=Float.valueOf(e==null?0:Float.valueOf(e.getWeight()));
						e=exit.get(key);
						fExit[i]=Float.valueOf(e==null?0:Float.valueOf(e.getWeight()));
					}
					values[0]=fExit;
					values[1]=fEnter;
					update();
					c.clear();
				}
				YisiApp.disMissProgressDialog();
				
				super.onPostExecute(result);
			}
			
		};
		task.execute(params);
		return values;
	}

	private List<EntityTip> getTestTipData(){
		List<EntityTip> list=new ArrayList<EntityTip>();
		EntityTip tip1=new EntityTip();
		EntityTip tip2=new EntityTip();
		EntityTip tip3=new EntityTip();
		EntityTip tip4=new EntityTip();
		EntityTip tip5=new EntityTip();
		EntityTip tip6=new EntityTip();
		tip1.setTitle(mainActivity.getResources().getString(R.string.home_menu_cur_receive));
		tip2.setTitle(mainActivity.getResources().getString(R.string.home_menu_cur_send));
//		tip1.setTitle("原煤收货");
//		tip2.setTitle("原煤发货");
		tip3.setTitle("石灰收货");
		tip4.setTitle("石灰发货");
		tip5.setTitle("液碱收货");
		tip6.setTitle("液碱发货");
		tip1.setColor(defaultColors[0]);
		tip2.setColor(defaultColors[1]);
		tip3.setColor(defaultColors[2]);
		tip4.setColor(defaultColors[3]);
		tip5.setColor(defaultColors[4]);
		tip6.setColor(defaultColors[5]);
		list.add(tip1);
		list.add(tip2);
//		list.add(tip3);
//		list.add(tip4);
//		list.add(tip5);
//		list.add(tip6);
		return list;
	}
	private List<EntityHomtItemParent> getTestData() {
		List<EntityHomtItemParent> list = new ArrayList<EntityHomtItemParent>();
		EntityHomtItemParent parent1 = new EntityHomtItemParent();
		EntityHomtItemParent parent2 = new EntityHomtItemParent();
		EntityHomtItemParent parent3 = new EntityHomtItemParent();
		parent1.setTitle("明细");
		parent2.setTitle("汇总");
		parent3.setTitle("往来明细");
		// parent1.setBackGroundColor(getResources().getColor(R.color.light_green));
		// parent2.setBackGroundColor(getResources().getColor(R.color.light_red));
		// parent3.setBackGroundColor(getResources().getColor(R.color.dark_yellow));
		list.add(parent1);
		list.add(parent2);
		list.add(parent3);
		return list;
	}

	@Override
	public String getTitle() {
		return getResources().getString(R.string.mode_home_page);
	}
	//折线图标注
	private class MyGridTipAdapter extends BaseAdapter implements ListAdapter {
		private List<EntityTip> items;
		
		public MyGridTipAdapter(List<EntityTip> items) {
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
		public View getView(int index, View view, ViewGroup group) {
			EntityTip item = items.get(index);
			Holder holder = null;
			if (view == null) {
				view = LayoutInflater.from(getActivity()).inflate(
						R.layout.frame_home_item, group, false);
				holder = new Holder();
				holder.titleText = (TextView) view
						.findViewById(R.id.tv_main_item_tip);
				holder.titleImage = (ImageView) view
						.findViewById(R.id.iv_main_item_image);
				view.setTag(holder);
			} else {
				holder = (Holder) view.getTag();
			}
			holder.titleText.setText(item.getTitle());
			holder.titleText.setTextSize(13);
			holder.titleImage.setBackgroundColor(item.getColor());
			return view;
		}
		
		private class Holder {
			private TextView titleText;
			private ImageView titleImage;
		}
	}
	private class MyGridAdapter extends BaseAdapter implements ListAdapter {
		private List<EntityHomtItemParent> items;

		public MyGridAdapter(List<EntityHomtItemParent> items) {
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
		public View getView(int index, View view, ViewGroup group) {
			EntityHomtItemParent item = items.get(index);
			Holder holder = null;
			if (view == null) {
				view = LayoutInflater.from(getActivity()).inflate(
						R.layout.frame_home_item, group, false);
				holder = new Holder();
				holder.titleText = (TextView) view
						.findViewById(R.id.tv_main_item_tip);
//				holder.titleImage = (ImageView) view
//						.findViewById(R.id.iv_main_item_image);
				view.setTag(holder);
			} else {
				holder = (Holder) view.getTag();
			}
			holder.titleText.setText(item.getTitle());
			// holder.titleImage.setBackgroundColor(item.getBackGroundColor());
			return view;
		}

		private class Holder {
			private TextView titleText;
//			private ImageView titleImage;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> root, View view, int index,
			long position) {
		switch (index) {
		case 0:
			setValue(mainActivity.getIntent(), Constants.TYPE,
					Constants.TYPE_DETAILS);
			mainActivity.otherBackableFragment(new ConditionFragment());
			break;
		case 1:
			setValue(mainActivity.getIntent(), Constants.TYPE,
					Constants.TYPE_GATHER);
			mainActivity.otherBackableFragment(new ConditionFragment());
			break;
		case 2:
			setValue(mainActivity.getIntent(), Constants.TYPE,
					Constants.TYPE_CONTACTS_DETAILS);
			mainActivity.otherBackableFragment(new ConditionFragment());
			break;

		default:
			break;
		}
	}
}
