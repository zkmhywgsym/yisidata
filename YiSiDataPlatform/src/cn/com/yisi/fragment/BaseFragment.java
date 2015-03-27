package cn.com.yisi.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.yisi.MainActivity;
import cn.com.yisi.util.Constants;
import cn.com.ysdp.R;

public class BaseFragment extends Fragment{
	protected View rootView;
	protected MainActivity mainActivity;
	protected String flag;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentActivity acitvity=getActivity();
		if (acitvity instanceof MainActivity) {
			mainActivity= (MainActivity) acitvity;
		}
	}
	public void setValue(Intent activityIntent,String name,String value){
		if (activityIntent==null) {
			Log.e(getClass().getSimpleName(), "setValue() activityIntent==null");
			return ;
		}
		activityIntent.putExtra(name, value);
	}
	public String getValue(Intent activityIntent,String name){
		if (activityIntent==null) {
			Log.e(getClass().getSimpleName(), "getValue() activityIntent==null");
			return "get Intent Value False";
		}
		return activityIntent.getStringExtra(name);
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
			if (this instanceof HomeFragment) {
				getActivity().findViewById(R.id.btn_titleLeftButton).setVisibility(View.GONE);
				getActivity().findViewById(R.id.btn_titleRightButton).setVisibility(View.GONE);
			}else{
				getActivity().findViewById(R.id.btn_titleLeftButton).setVisibility(View.VISIBLE);
				getActivity().findViewById(R.id.btn_titleRightButton).setVisibility(View.VISIBLE);
			}
			((TextView)getActivity().findViewById(R.id.titleText)).setText(getTitle());
		super.onResume();
	}
	public String getTitle(){
		return getResources().getString(R.string.mode_defaultActivityTitle);
	}
	@Override
	public void onStart() {
		setValue(getActivity().getIntent(),Constants.MODE, getTitle());
		super.onStart();
	}
	public void onResult(Intent intent){
		
	}
	@Override
	public View getView() {
		if (rootView!=null) {
			return rootView;
		}
		return super.getView();
	}
	public void doBack(View view) {
	}
}
