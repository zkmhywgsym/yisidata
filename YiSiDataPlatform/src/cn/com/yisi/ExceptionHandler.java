package cn.com.yisi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;
import cn.com.ysdp.R;

/**
 * 应用程序异常类：用于捕获异常和提示错误信息
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressWarnings("serial")
public class ExceptionHandler extends Exception implements UncaughtExceptionHandler{

	private final static boolean Debug = false;//是否保存错误日志
	
	/** 定义异常类型 */
	public final static byte TYPE_NETWORK 	= 0x01;
	public final static byte TYPE_SOCKET	= 0x02;
	public final static byte TYPE_HTTP_CODE	= 0x03;
	public final static byte TYPE_HTTP_ERROR= 0x04;
	public final static byte TYPE_XML	 	= 0x05;
	public final static byte TYPE_IO	 	= 0x06;
	public final static byte TYPE_RUN	 	= 0x07;
	public final static byte TYPE_JSON	 	= 0x08;
	
	private byte type;
	private int code;
	
	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	
	private ExceptionHandler(){
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}
	
	private ExceptionHandler(byte type, int code, Exception excp) {
		super(excp);
		this.type = type;
		this.code = code;		
		if(Debug){
			this.saveErrorLog(excp);
		}
	}
	public int getCode() {
		return this.code;
	}
	public int getType() {
		return this.type;
	}
	
	/**
	 * 提示友好的错误信息
	 * @param ctx
	 */
	public void makeToast(Context ctx){
		switch(this.getType()){
		case TYPE_HTTP_CODE:
			String err = ctx.getString(R.string.http_status_code_error, this.getCode());
			Toast.makeText(ctx, err, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_HTTP_ERROR:
			Toast.makeText(ctx, R.string.http_exception_error, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_SOCKET:
			Toast.makeText(ctx, R.string.socket_exception_error, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_NETWORK:
			Toast.makeText(ctx, R.string.network_not_connected, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_XML:
			Toast.makeText(ctx, R.string.xml_parser_failed, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_JSON:
			Toast.makeText(ctx, R.string.xml_parser_failed, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_IO:
			Toast.makeText(ctx, R.string.io_exception_error, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_RUN:
			Toast.makeText(ctx, R.string.app_run_code_error, Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	/**
	 * 保存异常日志
	 * @param excp
	 */
	public void saveErrorLog(Exception excp) {
		String errorlog = "errorlog.txt";
		String savePath = "";
		String logFilePath = "";
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			//判断是否挂载了SD卡
			String storageState = Environment.getExternalStorageState();		
			if(storageState.equals(Environment.MEDIA_MOUNTED)){
				savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/OSChina/Log/";
				File file = new File(savePath);
				if(!file.exists()){
					file.mkdirs();
				}
				logFilePath = savePath + errorlog;
			}
			//没有挂载SD卡，无法写文件
			if(logFilePath == ""){
				return;
			}
			File logFile = new File(logFilePath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			fw = new FileWriter(logFile,true);
			pw = new PrintWriter(fw);
			pw.println("--------------------"+(DateFormat.getDateTimeInstance().format(new Date()))+"---------------------");	
			excp.printStackTrace(pw);
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();		
		}finally{ 
			if(pw != null){ pw.close(); } 
			if(fw != null){ try { fw.close(); } catch (IOException e) { }}
		}

	}
	
	public static ExceptionHandler http(int code) {
		return new ExceptionHandler(TYPE_HTTP_CODE, code, null);
	}
	
	public static ExceptionHandler http(Exception e) {
		return new ExceptionHandler(TYPE_HTTP_ERROR, 0 ,e);
	}

	public static ExceptionHandler socket(Exception e) {
		return new ExceptionHandler(TYPE_SOCKET, 0 ,e);
	}
	
	public static ExceptionHandler io(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new ExceptionHandler(TYPE_NETWORK, 0, e);
		}
		else if(e instanceof IOException){
			return new ExceptionHandler(TYPE_IO, 0 ,e);
		}
		return run(e);
	}
	
	public static ExceptionHandler xml(Exception e) {
		return new ExceptionHandler(TYPE_XML, 0, e);
	}
	
	public static ExceptionHandler json(Exception e) {
		return new ExceptionHandler(TYPE_JSON, 0, e);
	}
	
//	public static ExceptionHandler network(Exception e) {
//		if(e instanceof UnknownHostException || e instanceof ConnectException){
//			return new ExceptionHandler(TYPE_NETWORK, 0, e);
//		}
//		else if(e instanceof HttpException){
//			return http(e);
//		}
//		else if(e instanceof SocketException){
//			return socket(e);
//		}
//		return http(e);
//	}
	
	public static ExceptionHandler run(Exception e) {
		return new ExceptionHandler(TYPE_RUN, 0, e);
	}

	/**
	 * 获取APP异常崩溃处理对象
	 * @param context
	 * @return
	 */
	public static ExceptionHandler getAppExceptionHandler(){
		return new ExceptionHandler();
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if(!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		}

	}
	/**
	 * 自定义异常处理:收集错误信息&发送错误报告
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if(ex == null) {
			return false;
		}
		
		final Context context=null;//TODO
		if(context == null) {
			return false;
		}
		
		final String crashReport = getCrashReport(context, ex);
		//显示异常信息&发送报告
		new Thread() {
			public void run() {
				Looper.prepare();
				try {
					System.out.println("context:"+context);
					sendAppCrashReport(context, crashReport);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Looper.loop();
			}

		}.start();
		return true;
	}
	/**
	 * 发送App异常崩溃报告
	 * 
	 * @param cont
	 * @param crashReport
	 */
	public void sendAppCrashReport(final Context cont,
			final String crashReport) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_error);
		builder.setMessage(R.string.app_error_message);
		builder.setPositiveButton(R.string.submit_report,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 发送异常报告
						Intent i = new Intent(Intent.ACTION_SEND);
						// i.setType("text/plain"); //模拟器
						i.setType("message/rfc822"); // 真机
						i.putExtra(Intent.EXTRA_EMAIL,
								new String[] { "zhangdeyi@oschina.net" });
						i.putExtra(Intent.EXTRA_SUBJECT,
								"开源中国Android客户端 - 错误报告");
						i.putExtra(Intent.EXTRA_TEXT, crashReport);
						cont.startActivity(Intent.createChooser(i, "发送错误报告"));
						// 退出
//						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.setNegativeButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 退出
//						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.show();
	}
	/**
	 * 获取APP崩溃异常报告
	 * @param ex
	 * @return
	 */
	private String getCrashReport(Context context, Throwable ex) {
		PackageInfo info=null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		StringBuffer exceptionStr = new StringBuffer();
		exceptionStr.append("Version: "+info.versionName+"("+info.versionCode+")\n");
		exceptionStr.append("Android: "+android.os.Build.VERSION.RELEASE+"("+android.os.Build.MODEL+")\n");
		exceptionStr.append("Exception: "+ex.getMessage()+"\n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			exceptionStr.append(elements[i].toString()+"\n");
		}
		return exceptionStr.toString();
	}
}
