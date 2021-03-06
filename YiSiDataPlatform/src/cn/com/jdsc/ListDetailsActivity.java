package cn.com.jdsc;

import java.util.ArrayList;
import java.util.List;

import cn.com.jdsc.entity.EntityDetails;
import cn.com.util.ExitManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

//�б�����
public class ListDetailsActivity extends Activity {
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_details_layout);
		getValues();
		initViews();
		ExitManager.getInstance().addActivity(this);
	}
	private void initViews(){
		listView=(ListView) findViewById(R.id.list_view);
		listView.setAdapter(new MyListAdapter(testData()));
	}
	private void getValues(){
		Intent intent=getIntent();
		System.out.println(intent.getStringExtra("status"));
	}
	private List<EntityDetails> testData(){
		List<EntityDetails> list=new ArrayList<EntityDetails>();
		EntityDetails entity=null;
		for (int i = 0; i < 10; i++) {
			entity=new EntityDetails();
			entity.setCarNum(i+1+"");
			entity.setLightTime("lightTime"+i);
			entity.setMetariel("ԭú");
			entity.setProvider("ú��"+i);
			entity.setWeight((30+i)+"");
			entity.setWeightTime("weightTime"+i);
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
				holder.carNum=(TextView) view.findViewById(R.id.tv_car_num_value);
				holder.meteriel=(TextView) view.findViewById(R.id.tv_materiel_value);
				holder.provider=(TextView) view.findViewById(R.id.tv_provider_value);
				holder.timeLight=(TextView) view.findViewById(R.id.tv_time_light_value);
				holder.timeWeight=(TextView) view.findViewById(R.id.tv_time_weight_value);
				holder.weight=(TextView) view.findViewById(R.id.tv_weight_value);
				view.setTag(holder);
			}else{
				holder=(Holder) view.getTag();
			}
			EntityDetails entity=list.get(index);
			holder.carNum.setText(entity.getCarNum());
			holder.meteriel.setText(entity.getMetariel());
			holder.provider.setText(entity.getProvider());
			holder.timeLight.setText(entity.getLightTime());
			holder.timeWeight.setText(entity.getWeightTime());
			holder.weight.setText(entity.getWeight());
			return view;
		}
		private class Holder{
			private TextView carNum;
			private TextView meteriel;
			private TextView provider;
			private TextView timeLight;
			private TextView timeWeight;
			private TextView weight;
		}
		
	}
}
