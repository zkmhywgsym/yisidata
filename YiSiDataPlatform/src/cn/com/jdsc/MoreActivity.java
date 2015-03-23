package cn.com.jdsc;

import cn.com.util.ExitManager;
import android.app.Activity;
import android.os.Bundle;

public class MoreActivity  extends Activity{
   @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main_more);
	ExitManager.getInstance().addActivity(this);
}
}
