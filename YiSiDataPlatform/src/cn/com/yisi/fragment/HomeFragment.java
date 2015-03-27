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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.yisi.entity.EntityHomtItemChild;
import cn.com.yisi.entity.EntityHomtItemParent;
import cn.com.yisi.util.Constants;
import cn.com.ysdp.R;
//主页
public class HomeFragment extends BaseFragment implements OnItemClickListener{
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
	private List<EntityHomtItemParent> getTestData() {
		List<EntityHomtItemParent> list=new ArrayList<EntityHomtItemParent>();
		EntityHomtItemParent parent1=new EntityHomtItemParent();
		EntityHomtItemParent parent2=new EntityHomtItemParent();
		EntityHomtItemParent parent3=new EntityHomtItemParent();
		parent1.setTitle("明细");
		parent2.setTitle("汇总");
		parent3.setTitle("往来明细");
//		parent1.setBackGroundColor(getResources().getColor(R.color.light_green));
//		parent2.setBackGroundColor(getResources().getColor(R.color.light_red));
//		parent3.setBackGroundColor(getResources().getColor(R.color.dark_yellow));
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
				holder.titleText=(TextView) view.findViewById(R.id.tv_main_item_tip);
				holder.titleImage=(ImageView) view.findViewById(R.id.iv_main_item_image);
				view.setTag(holder);
			}else{
				holder=(Holder) view.getTag();
			}
			holder.titleText.setText(item.getTitle());
//			holder.titleImage.setBackgroundColor(item.getBackGroundColor());
			return view;
		}
		private class Holder{
			private TextView titleText;
			private ImageView titleImage;
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
