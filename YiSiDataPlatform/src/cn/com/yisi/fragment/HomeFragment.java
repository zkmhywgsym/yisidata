package cn.com.yisi.fragment;

import cn.com.jdsc.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//ึ๗าณ
public class HomeFragment extends BaseFragment{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		return rootView=LayoutInflater.from(getActivity()).inflate(R.layout.main_fragment_statistic_daily, container,false);
	}
	@Override
	public String getTitle() {
		return getResources().getString(R.string.mode_home_page);
	}
}
