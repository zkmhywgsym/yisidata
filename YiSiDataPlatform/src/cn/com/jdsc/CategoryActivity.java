package cn.com.jdsc;

import cn.com.util.ExitManager;
import android.app.Activity;
import android.os.Bundle;

public class CategoryActivity  extends Activity{
  @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main_category);
	ExitManager.getInstance().addActivity(this);
}
}
