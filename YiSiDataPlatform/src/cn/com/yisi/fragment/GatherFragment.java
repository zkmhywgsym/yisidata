package cn.com.yisi.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.yisi.entity.EntityDetails;
import cn.com.yisi.util.Constants;
import cn.com.ysdp.R;
//汇总
public class GatherFragment extends BaseFragment{
	private ListView listView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		flag=getValue(mainActivity.getIntent(),Constants.FLAG);
		rootView=LayoutInflater.from(getActivity()).inflate(R.layout.activity_list_details_layout, container,false);
		listView=(ListView) rootView.findViewById(R.id.list_view);
		listView.setAdapter(new MyListAdapter(testData()));
		return rootView;
	}
	@Override
	public String getTitle() {
		return getResources().getString(R.string.mode_gather);
	}
	private List<EntityDetails> testData(){
		List<EntityDetails> list=new ArrayList<EntityDetails>();
		EntityDetails entity=null;
		for (int i = 0; i < 10; i++) {
			entity=new EntityDetails();
			entity.setCarNum((i+1)+"");
			entity.setTime("2015-03-24 11:37");
			entity.setMetariel("原煤");
			entity.setProvider("中煤集团");
			entity.setWeight((i+1)+"");
			entity.setType("send");
			entity.setStatue("已发货");
			list.add(entity);
		}
		return list;
	}
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
				holder.meteriel=(TextView) view.findViewById(R.id.tv_materiel_value);
				holder.providerLabel=(TextView) view.findViewById(R.id.tv_privader_label);
				holder.providerValue=(TextView) view.findViewById(R.id.tv_provider_value);
				holder.timeLabel=(TextView) view.findViewById(R.id.tv_label_time);
				holder.timeValue=(TextView) view.findViewById(R.id.tv_time_value);
				holder.weight=(TextView) view.findViewById(R.id.tv_value_weight);
				holder.statue=(TextView) view.findViewById(R.id.tv_statue_value);
				view.setTag(holder);
			}else{
				holder=(Holder) view.getTag();
			}
			EntityDetails entity=list.get(index);
			holder.carNumLabel.setText(getResources().getString(R.string.details_carNum));
			if ("send".equals(flag)) {
				holder.providerLabel.setText(getResources().getString(R.string.details_client));
				holder.timeLabel.setText(getResources().getString(R.string.details_time_weight_label));
				
			}else if ("receive".equals(flag)) {
				holder.providerLabel.setText(getResources().getString(R.string.details_provider));
				holder.timeLabel.setText(getResources().getString(R.string.details_time_light_label));
				
			}
			holder.meteriel.setText(entity.getMetariel());
			holder.providerValue.setText(entity.getProvider());
			holder.weight.setText(entity.getWeight());
			holder.carNumValue.setText(entity.getCarNum());
			holder.timeValue.setText(entity.getTime());
			holder.statue.setText(entity.getStatue());
			return view;
		}
		private class Holder{
			private TextView providerLabel;
			private TextView providerValue;
			private TextView carNumLabel;
			private TextView carNumValue;
			private TextView meteriel;
			private TextView timeLabel;
			private TextView timeValue;
			private TextView weight;
			private TextView statue;
		}
		
	}
}
