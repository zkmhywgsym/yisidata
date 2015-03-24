package cn.com.yisi.fragment;

import cn.com.jdsc.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//¸ü¶à
public class MoreFragment extends BaseFragment{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return rootView=LayoutInflater.from(getActivity()).inflate(R.layout.main_more, container,false);
	}
	@Override
	public String getTitle() {
		return getResources().getString(R.string.mode_home_page);
	}
	@Override
	public void doBack(View view){
		switch (view.getId()) {
		case R.id.personal_info:
			Log.e(getClass().getSimpleName(),"pInfo");
			break;
		case R.id.sys_set:
			
			break;
		case R.id.feed_back:
			
			break;
		case R.id.update:
			
			break;
		case R.id.about:
			
			break;

		}
	}
}
