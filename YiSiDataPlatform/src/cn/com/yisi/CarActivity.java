package cn.com.yisi;

import cn.com.jdsc.R;
import cn.com.util.ExitManager;
import android.app.Activity;
import android.os.Bundle;

public class CarActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main_car);
    	
    	ExitManager.getInstance().addActivity(this);
    	
    }
}
