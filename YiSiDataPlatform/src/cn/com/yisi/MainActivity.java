package cn.com.yisi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import cn.com.yisi.fragment.BaseFragment;
import cn.com.yisi.fragment.HomeFragment;
import cn.com.yisi.util.ExitManager;
import cn.com.ysdp.R;
//主页
public class MainActivity extends FragmentActivity {
	public String mode;//模块
	private FragmentManager fragmentManager;
	private BaseFragment curFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        ExitManager.getInstance().addActivity(this);
        initFragment();
    }
    public BaseFragment getCurFragment(){
    	return curFragment;
    }
    
    public void setCurFragment(BaseFragment curFragment) {
		this.curFragment = curFragment;
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode==100&&resultCode==1001) {
			Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
		}else if (requestCode==200&&resultCode==1001) {
			Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
		}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    private void initFragment(){
    	fragmentManager=getSupportFragmentManager();
    	otherFragment(new HomeFragment());
    }
    public void otherFragment(BaseFragment fragment){
    	curFragment=fragment;
    	FragmentTransaction ft=fragmentManager.beginTransaction();
    	ft.replace(android.R.id.tabcontent, fragment);
    	ft.commit();
    }
    public void otherBackableFragment(BaseFragment fragment){
    	curFragment=fragment;
    	FragmentTransaction ft=fragmentManager.beginTransaction();
    	ft.replace(android.R.id.tabcontent, fragment);
    	ft.addToBackStack(null);
    	ft.commit();
    }
    AlertDialog dialog=null;
    public void exit(){
    	if (dialog!=null&&dialog.isShowing()) {
    		dialog.dismiss();
    	}
    	Log.e(getClass().getSimpleName(), "st2");
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(
    			MainActivity.this);
    	alertDialog.setTitle(MainActivity.this
    			.getString(R.string.app_close));
    	alertDialog.setPositiveButton(MainActivity.this
    			.getString(R.string.btn_ok),
    			new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog,
    				int which) {
    			finish();
    		}
    	});
    	alertDialog.setNegativeButton(MainActivity.this
    			.getString(R.string.btn_cancel),
    			new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog,
    				int which) {
    			dialog.dismiss();
    		}
    	});
    	dialog=alertDialog.create();
    	dialog.show();
    	
    }
    @Override
    public void onBackPressed() {
    	findViewById(R.id.btn_titleLeftButton).performClick();
    	return;
    }
    public void onClick(View view){
    	switch (view.getId()) {
		case R.id.btn_titleLeftButton://返回
			if (fragmentManager.getBackStackEntryCount()>0) {
				fragmentManager.popBackStack();
			}else{
				exit();
			}
			break;

		case R.id.btn_titleRightButton://主页
			otherFragment(new HomeFragment());
			break;
		default:
			if (curFragment!=null) {
				curFragment.doBack(view);
			}
			break;
		}
    }


}


//package com.hyq.activity;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.widget.DrawerLayout;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import com.hyq.fragment.BaseFragment;
//import com.hyq.fragment.TestFragment;
//import com.hyq.fragment.TestFragment1;
//import com.hyq.fragment.TestFragment2;
//import com.hyq.widget.Tab;
//import com.hyq.widget.Tab.TabListener;
//import com.hyq.widget.TabHost;
//import com.hyqapp.R;
//import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
//import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
//
////首页
////git https://github.com/zkmhywgsym/myApp.git
//public class MainActivity extends FragmentActivity implements TabListener {
//	private ViewPager pager;
//	private TabHost tabHost;
//	private List<BaseFragment> fragments = new ArrayList<BaseFragment>();
//	private ViewPagerAdapter pagerAdapter;
//	private DrawerArrowDrawable drawerArrow;
//	private DrawerLayout mDrawerLayout;
//	private ActionBarDrawerToggle mDrawerToggle;
//	private ListView navdrawer;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setContentView(R.layout.activity_main_layout);
//		initView();
//	}
//
//	private void initView() {
//		pager = (ViewPager) this.findViewById(R.id.pager);
//		tabHost = (TabHost) findViewById(R.id.tabHost);
//		navdrawer=(ListView) findViewById(R.id.navdrawer);
//		initNavdrawer();
//		FragmentManager fm = getSupportFragmentManager();
//		// FragmentTransaction transaction = fm.beginTransaction();
//		addFragment();
//		// transaction.commit();
//		pagerAdapter = new ViewPagerAdapter(fm, fragments);
//		pager.setAdapter(pagerAdapter);
//		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {// 翻页
//			@Override
//			public void onPageSelected(int position) {
//				// when user do a swipe the selected tab change
//				tabHost.setSelectedTab(position);
//			}
//		});
//		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		drawerArrow = new DrawerArrowDrawable(this) {
//			@Override
//			public boolean isLayoutRtl() {
//				return false;
//			}
//		};
//		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//				drawerArrow, R.string.drawer_open, R.string.drawer_close) {
//
//			public void onDrawerClosed(View view) {
//				super.onDrawerClosed(view);
//				invalidateOptionsMenu();
//			}
//
//			public void onDrawerOpened(View drawerView) {
//				super.onDrawerOpened(drawerView);
//				invalidateOptionsMenu();
//			}
//		};
//		mDrawerLayout.setDrawerListener(mDrawerToggle);
//		mDrawerToggle.syncState();
//	}
//	//菜单
//	private void initNavdrawer(){
//		String[] items=getResources().getStringArray(R.array.main_left_menu);
//		List<Map<String, String>> data=new ArrayList<Map<String,String>>();
//		Map<String, String> map=new HashMap<String, String>();
//		for (String item : items) {
//			map.put("item", item);
//		}
//		data.add(map);
////		navdrawer.setAdapter(new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, new String[]{"item"},new int[]{android.R.id.text1}));
//		navdrawer.setAdapter(new ArrayAdapter<String>(this,
//	            R.layout.list_item_1, R.id.item_text, items));
//		navdrawer.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> root, View view, int index,
//					long position) {
////				String text=((TextView)view.findViewById(R.id.item_text)).getText().toString();
////				if ("登陆".equals(text.trim())) {
////					Intent intent=new Intent(MainActivity.this,LoginActivity.class);
////					startActivity(intent);
////				}else if(true){
////					
////				}
//			}
//		});
//	}
//	// 添加fragment
//	@SuppressWarnings("deprecation")
//	private void addFragment() {
//		fragments.add(new TestFragment());
//		tabHost.addTab(tabHost
//				.newTab()
//				.setIcon(
//						getResources().getDrawable(
//								R.drawable.ic_person_black_24dp))
//				.setTabListener(this));
//		fragments.add(new TestFragment1());
//		tabHost.addTab(tabHost
//				.newTab()
//				.setIcon(
//						getResources().getDrawable(
//								R.drawable.ic_person_black_24dp))
//				.setTabListener(this));
//		fragments.add(new TestFragment2());
//		tabHost.addTab(tabHost
//				.newTab()
//				.setIcon(
//						getResources().getDrawable(
//								R.drawable.ic_person_black_24dp))
//				.setTabListener(this));
//		fragments.add(new TestFragment());
//		tabHost.addTab(tabHost
//				.newTab()
//				.setIcon(
//						getResources().getDrawable(
//								R.drawable.ic_person_black_24dp))
//				.setTabListener(this));
//		fragments.add(new TestFragment1());
//		tabHost.addTab(tabHost
//				.newTab()
//				.setIcon(
//						getResources().getDrawable(
//								R.drawable.ic_person_black_24dp))
//				.setTabListener(this));
//		fragments.add(new TestFragment2());
//		tabHost.addTab(tabHost
//				.newTab()
//				.setIcon(
//						getResources().getDrawable(
//								R.drawable.ic_person_black_24dp))
//				.setTabListener(this));
//	}
//
//	@Override
//	public void onTabSelected(Tab tab) {
//		pager.setCurrentItem(tab.getPosition(), true);
//	}
//
//	@Override
//	public void onTabReselected(Tab tab) {
//
//	}
//
//	@Override
//	public void onTabUnselected(Tab tab) {
//
//	}
//}
//
//class ViewPagerAdapter extends FragmentStatePagerAdapter {
//	private List<BaseFragment> fragments;
//
//	public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
//		super(fm);
//		this.fragments = fragments;
//	}
//
//	@Override
//	public Fragment getItem(int index) {
//		return fragments.get(index);
//	}
//
//	@Override
//	public int getCount() {
//		return fragments.size();
//	}
//
//	@Override
//	public CharSequence getPageTitle(int position) {
//		return fragments.get(position).name;
//	}
//
//}
