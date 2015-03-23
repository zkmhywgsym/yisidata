package cn.com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
//时间工具类
public class TimeUtils {
	private static Calendar cal=Calendar.getInstance();
	public static void setTime(Date date){
		if (date==null) {
			cal.setTimeInMillis(System.currentTimeMillis());
		}else{
			cal.setTime(date);
		}
	}
	public static String getCurTime(){
		return getCurTime("yyyy-MM-dd HH:mm");
	}
	public static String getCurTime(String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format,Locale.CHINA);
		return sdf.format(cal.getTime());
	}
	public static void main(String[] args) {
		System.out.println(getCurTime());
	}
}
