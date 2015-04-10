package cn.com.yisi;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class YisiApp extends Application{
	private static YisiApp app;
	private static SharedPreferences sp;
	private static ProgressDialog progress;
	public static Map<String, Object> temp=new HashMap<String, Object>();
	@Override
	public void onCreate() {
		super.onCreate();
//		Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.getAppExceptionHandler());
		app=this;
		sp=app.getSharedPreferences(app.getPackageName(), Context.MODE_PRIVATE);
	}
	public static YisiApp getInstance(){
		return app;
	}
	public static void setValue(String name,String value){
		Editor edit=sp.edit();
		edit.putString(name, value);
		edit.commit();
	}
	public static String getValue(String name,String defaultValue){
		return sp.getString(name, defaultValue);
	}
	public static void showProgressDialog(Context context,String title,String msg) {
		if (progress!=null) {
			if (progress.isShowing()) {
				progress.dismiss();
			}
		}
		progress = new ProgressDialog(context);
		progress.setTitle(title);
		progress.setMessage(msg);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setCancelable(false);
		progress.show();
	}
	public static void disMissProgressDialog(){
		if (progress!=null&&progress.isShowing()) {
			progress.dismiss();
			progress=null;
		}
	}
}
