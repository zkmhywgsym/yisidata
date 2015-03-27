package cn.com.yisi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;
import cn.com.ysdp.R;

public class DialogActivity extends Activity{
	protected RelativeLayout contentLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.customDialogActivity);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog_layout);
		contentLayout=(RelativeLayout) findViewById(R.id.activity_dialog_content);
		init();
	}
	protected void init(){
		
	}
}
