package cn.com.yisi;

import cn.com.jdsc.R;
import cn.com.util.ExitManager;
import android.app.Activity;
import android.os.Bundle;

public class BuyActivity  extends Activity{
   @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main_buy);
	ExitManager.getInstance().addActivity(this);
}
}
