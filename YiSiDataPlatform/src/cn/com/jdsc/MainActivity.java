package cn.com.jdsc;

import cn.com.util.ExitManager;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;
import android.widget.Toast;
//ึ๗าณ
public class MainActivity extends TabActivity implements TabContentFactory {
	TabHost tabHost;
	private RadioButton main_tab_home, main_tab_catagory, main_tab_car,main_tab_bang, main_tab_more;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        initTab();
        init();
        ExitManager.getInstance().addActivity(this);
    }
    
    public void init(){
    	main_tab_home=(RadioButton)findViewById(R.id.main_tab_home);
    	main_tab_catagory = (RadioButton) findViewById(R.id.main_tab_catagory);
		main_tab_car = (RadioButton) findViewById(R.id.main_tab_car);
		main_tab_more = (RadioButton) findViewById(R.id.main_tab_more);
		main_tab_bang = (RadioButton) findViewById(R.id.main_tab_bang);
		main_tab_home.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				tabHost.setCurrentTabByTag("home");
				
			}
		});

		main_tab_catagory.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				tabHost.setCurrentTabByTag("catagory");

			}
		});
		main_tab_car.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				tabHost.setCurrentTabByTag("car");

			}
		});
		main_tab_more.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				tabHost.setCurrentTabByTag("more");
				
			}
		});
		main_tab_bang.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				tabHost.setCurrentTabByTag("bang");
				
			}
		});
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode==100&&resultCode==1001) {
			Toast.makeText(this, data.getStringExtra("result"), 2000).show();
		}else if (requestCode==200&&resultCode==1001) {
			Toast.makeText(this, data.getStringExtra("result"), 2000).show();
		}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    private void initTab(){
    	tabHost=getTabHost();
    	tabHost.addTab(tabHost.newTabSpec("home").setIndicator("home")
				.setContent(this));
//    	tabHost.addTab(tabHost.newTabSpec("home").setIndicator("home")
//    			.setContent(new Intent(this, HomeActivity.class)));
    	tabHost.addTab(tabHost.newTabSpec("catagory").setIndicator("catagory")
				.setContent(new Intent(this, SearchConditionActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("car").setIndicator("car")
				.setContent(new Intent(this, CarActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("buy").setIndicator("buy")
				.setContent(new Intent(this, BuyActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("more").setIndicator("more")
				.setContent(new Intent(this, MoreActivity.class)));
    }
    
    public boolean dispatchKeyEvent( KeyEvent event) {
		int keyCode=event.getKeyCode();
	      if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (event.getRepeatCount() == 0) {
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
							}
						});
				alertDialog.show();
			}
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public View createTabContent(String tag) {
		View view=LayoutInflater.from(this).inflate(R.layout.main_fragment_statistic_daily, tabHost.getTabContentView(),false);
		((TextView)view.findViewById(R.id.titleText)).setText(getResources().getString(R.string.statistic_daily_title));
		return view;
	}

}