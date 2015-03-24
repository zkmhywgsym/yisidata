package cn.com.yisi.fragment;

import cn.com.jdsc.R;
import cn.com.yisi.MainActivity;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseFragment extends Fragment{
	protected View rootView;
	protected MainActivity mainActivity;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentActivity acitvity=getActivity();
		if (acitvity instanceof MainActivity) {
			mainActivity=(MainActivity) acitvity;
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onAttach(Activity activity) {
		Log.e(getClass().getSimpleName(), "onAttach");
		super.onAttach(activity);
	}
	@Override
	public void onResume() {
		if (rootView!=null) {
			if (rootView.findViewById(R.id.titleText)==null) {
				Log.e(getClass().getSimpleName(), "没定义标题栏");
				((ViewGroup)rootView).addView(LayoutInflater.from(getActivity()).inflate(R.layout.title_layout, (ViewGroup) rootView,false));
			}
			((TextView)rootView.findViewById(R.id.titleText)).setText(getTitle());
		}
		super.onResume();
	}
	//重写以修改标题
	public String getTitle(){
		return getResources().getString(R.string.mode_defaultActivityTitle);
	}
	@Override
	public void onStart() {
		mainActivity.mode=getTitle();
		super.onStart();
	}
	@Override
	public View getView() {
		if (rootView!=null) {
			return rootView;
		}
		return super.getView();
	}
	//重写以响应点击事件
	public void doBack(View view) {
	}
}
