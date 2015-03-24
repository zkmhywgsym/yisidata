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
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;
import cn.com.jdsc.R;
import cn.com.util.ExitManager;
import cn.com.yisi.fragment.BaseFragment;
import cn.com.yisi.fragment.ConditionFragment;
import cn.com.yisi.fragment.HomeFragment;
import cn.com.yisi.fragment.MoreFragment;
//主页
public class MainActivity extends FragmentActivity {
	private TabHost tabHost;
	public String mode;//模块
	public String type;//类型
	private FragmentManager fragmentManager;
	private BaseFragment curFragment;
	private RadioButton main_tab_home, main_tab_details, main_tab_gather,main_tab_contacts_details, main_tab_more;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        initFragment();
        init();
        ExitManager.getInstance().addActivity(this);
    }
    
    public void init(){
    	main_tab_home=(RadioButton)findViewById(R.id.main_tab_home);
    	main_tab_details = (RadioButton) findViewById(R.id.main_tab_details);
		main_tab_gather = (RadioButton) findViewById(R.id.main_tab_gather);
		main_tab_more = (RadioButton) findViewById(R.id.main_tab_more);
		main_tab_contacts_details = (RadioButton) findViewById(R.id.main_tab_contacts_details);
		tabHost=(TabHost) findViewById(android.R.id.tabhost);
		main_tab_home.setOnClickListener(new OnClickListener() {//主页

			public void onClick(View view) {
				tabHost.setCurrentTabByTag("home");
				otherFragment(new HomeFragment());
			}
		});

		main_tab_details.setOnClickListener(new OnClickListener() {//明细

			public void onClick(View view) {
				type="details";
				tabHost.setCurrentTabByTag("details");
				otherFragment(new ConditionFragment());
			}
		});
		main_tab_gather.setOnClickListener(new OnClickListener() {//汇总

			public void onClick(View view) {
				tabHost.setCurrentTabByTag("gather");
				type="gather";
				otherFragment(new ConditionFragment());
			}
		});
		main_tab_contacts_details.setOnClickListener(new OnClickListener() {//往来明细
			
			public void onClick(View view) {
				tabHost.setCurrentTabByTag("contacts_details");
				type="contacts_details";
				otherFragment(new ConditionFragment());
			}
		});
		main_tab_more.setOnClickListener(new OnClickListener() {//更多
			
			public void onClick(View view) {
				tabHost.setCurrentTabByTag("more");
				otherFragment(new MoreFragment());
			}
		});
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
    AlertDialog dialog=null;
    @Override
    public void onBackPressed() {
    	Log.e(getClass().getSimpleName(), "st1");
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
						ExitManager.getInstance().exit();
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
    public void onClick(View view){
    	if (curFragment!=null) {
			curFragment.doBack(view);
		}
    }


}