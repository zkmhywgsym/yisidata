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