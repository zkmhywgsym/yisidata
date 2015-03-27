package cn.com.yisi.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.yisi.entity.EntityHomtItemChild;
import cn.com.yisi.entity.EntityHomtItemParent;
import cn.com.yisi.util.Constants;
import cn.com.ysdp.R;
//主页
public class HomeFragment2 extends BaseFragment implements OnItemClickListener{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView=LayoutInflater.from(getActivity()).inflate(R.layout.frame_home_layout, container,false);
		initView();
		return rootView;
	}
	private void initView(){
		GridView gridview=(GridView) rootView.findViewById(R.id.gridview);
		gridview.setAdapter(new MyGridAdapter(getTestData()));
		gridview.setOnItemClickListener(this);
	}
	private List<EntityHomtItemParent> getTestData(){
		List<EntityHomtItemParent> list=new ArrayList<EntityHomtItemParent>();
		EntityHomtItemParent parent1=new EntityHomtItemParent();
		EntityHomtItemParent parent2=new EntityHomtItemParent();
		EntityHomtItemParent parent3=new EntityHomtItemParent();
//		EntityHomtItemParent parent4=new EntityHomtItemParent();
//		EntityHomtItemParent parent5=new EntityHomtItemParent();
//		EntityHomtItemParent parent6=new EntityHomtItemParent();
		parent1.setTitle("日明细");
		parent2.setTitle("日汇总");
		parent3.setTitle("月明细");
//		parent4.setTitle("月汇总");
//		parent5.setTitle("年明细");
//		parent6.setTitle("年汇总");
		
		List<EntityHomtItemChild> childs1=new ArrayList<EntityHomtItemChild>();
		EntityHomtItemChild child1=new EntityHomtItemChild();
		EntityHomtItemChild child2=new EntityHomtItemChild();
		EntityHomtItemChild child3=new EntityHomtItemChild();
		child1.setLabelTop("原煤（吨）");
		child1.setLabelBot("车号");
		child1.setValueTop("2");
		child1.setValueBot("晋A11111");
		child2.setLabelTop("原煤（吨）");
		child2.setLabelBot("车号");
		child2.setValueTop("3");
		child2.setValueBot("晋A11112");
		child3.setLabelTop("石灰（吨）");
		child3.setLabelBot("车号");
		child3.setValueTop("1");
		child3.setValueBot("晋A11113");
		childs1.add(child1);
		childs1.add(child2);
		childs1.add(child3);
		parent1.setChilds(childs1);
		
		List<EntityHomtItemChild> childs2=new ArrayList<EntityHomtItemChild>();
		child1=new EntityHomtItemChild();
		child2=new EntityHomtItemChild();
		child3=new EntityHomtItemChild();
		child1.setLabelTop("原煤（吨）");
		child1.setLabelBot("车数");
		child1.setValueTop("30");
		child1.setValueBot("10");
		child2.setLabelTop("石灰（吨）");
		child2.setLabelBot("车数");
		child2.setValueTop("20");
		child2.setValueBot("10");
		child3.setLabelTop("液碱（吨）");
		child3.setLabelBot("车数");
		child3.setValueTop("30");
		child3.setValueBot("10");
		childs2.add(child1);
		childs2.add(child2);
		childs2.add(child3);
		parent2.setChilds(childs2);
		
		List<EntityHomtItemChild> childs3=new ArrayList<EntityHomtItemChild>();
		child1=new EntityHomtItemChild();
		child2=new EntityHomtItemChild();
		child3=new EntityHomtItemChild();
		child1.setLabelTop("原煤（吨）");
		child1.setLabelBot("车数");
		child1.setValueTop("30");
		child1.setValueBot("10");
		child2.setLabelTop("石灰（吨）");
		child2.setLabelBot("车数");
		child2.setValueTop("20");
		child2.setValueBot("10");
		child3.setLabelTop("液碱（吨）");
		child3.setLabelBot("车数");
		child3.setValueTop("30");
		child3.setValueBot("10");
		childs3.add(child1);
		childs3.add(child2);
		childs3.add(child3);
		parent3.setChilds(childs3);
		
		list.add(parent1);
		list.add(parent2);
		list.add(parent3);
		return list;
	}
	@Override
	public String getTitle() {
		return getResources().getString(R.string.mode_home_page);
	}
	private class MyGridAdapter extends BaseAdapter implements ListAdapter{
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
			EntityHomtItemParent item=items.get(index);
			Holder holder=null;
			if (view==null) {
				view=LayoutInflater.from(getActivity()).inflate(R.layout.frame_home_item, group,false);
				holder=new Holder();
				holder.title=(TextView) view.findViewById(R.id.main_item_title);
				holder.type1=(TextView) view.findViewById(R.id.main_item_type_label1);
				holder.type2=(TextView) view.findViewById(R.id.main_item_type_label2);
				holder.type3=(TextView) view.findViewById(R.id.main_item_type_label3);
				holder.typeValue1=(TextView) view.findViewById(R.id.main_item_type_value1);
				holder.typeValue2=(TextView) view.findViewById(R.id.main_item_type_value2);
				holder.typeValue3=(TextView) view.findViewById(R.id.main_item_type_value3);
				holder.car1=(TextView) view.findViewById(R.id.main_item_car_code1);
				holder.car2=(TextView) view.findViewById(R.id.main_item_car_code2);
				holder.car3=(TextView) view.findViewById(R.id.main_item_car_code3);
				holder.carValue1=(TextView) view.findViewById(R.id.main_item_car_code_value1);
				holder.carValue2=(TextView) view.findViewById(R.id.main_item_car_code_value2);
				holder.carValue3=(TextView) view.findViewById(R.id.main_item_car_code_value3);
				view.setTag(holder);
			}else{
				holder=(Holder) view.getTag();
			}
			holder.title.setText(item.getTitle());
			holder.type1.setText(item.getChilds().get(0).getLabelTop());
			holder.type2.setText(item.getChilds().get(1).getLabelTop());
			holder.type3.setText(item.getChilds().get(2).getLabelTop());
			holder.typeValue1.setText(item.getChilds().get(0).getValueTop());
			holder.typeValue2.setText(item.getChilds().get(1).getValueTop());
			holder.typeValue3.setText(item.getChilds().get(2).getValueTop());
			holder.car1.setText(item.getChilds().get(0).getLabelBot());
			holder.car2.setText(item.getChilds().get(1).getLabelBot());
			holder.car3.setText(item.getChilds().get(2).getLabelBot());
			holder.carValue1.setText(item.getChilds().get(0).getValueBot());
			holder.carValue2.setText(item.getChilds().get(1).getValueBot());
			holder.carValue3.setText(item.getChilds().get(2).getValueBot());
			return view;
		}
		private class Holder{
			private TextView title;
			private TextView type1;
			private TextView type2;
			private TextView type3;
			private TextView typeValue1;
			private TextView typeValue2;
			private TextView typeValue3;
			private TextView car1;
			private TextView car2;
			private TextView car3;
			private TextView carValue1;
			private TextView carValue2;
			private TextView carValue3;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> root, View view, int index, long position) {
		switch (index) {
		case 0:
			setValue(mainActivity.getIntent(),Constants.TYPE, Constants.TYPE_DETAILS);
			mainActivity.otherBackableFragment(new ConditionFragment());
			break;
		case 1:
			setValue(mainActivity.getIntent(),Constants.TYPE, Constants.TYPE_GATHER);
			mainActivity.otherBackableFragment(new ConditionFragment());
			break;
		case 2:
			setValue(mainActivity.getIntent(),Constants.TYPE, Constants.TYPE_CONTACTS_DETAILS);
			mainActivity.otherBackableFragment(new ConditionFragment());
			break;

		default:
			break;
		}
	}
}
