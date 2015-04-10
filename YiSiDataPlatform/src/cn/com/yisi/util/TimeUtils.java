package cn.com.yisi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
//时间工具类
public class TimeUtils {
	private static Calendar cal=Calendar.getInstance();
	private static Date initDate;
	public static void setTime(Date date){
		if (date==null) {
			cal.setTimeInMillis(System.currentTimeMillis());
		}else{
			initDate=date;
			cal.setTime(date);
		}
	}
	public static String getCurTime(){
		return getCurTime("yyyy-MM-dd HH:mm");
	}
	public static String getCurTime(String format){
		cal.clear();//时间类是单例，不复原会导致以后查询出错
		SimpleDateFormat sdf=new SimpleDateFormat(format,Locale.CHINA);
		return sdf.format(cal.getTime());
	}
	//获取前推几天的日期类型，输入负时为后推几天的时间
	public static Date getOtherDate(int backDay){
		cal.clear();//时间类是单例，不复原会导致以后查询出错
		cal.add(Calendar.DAY_OF_MONTH, -backDay);
		Date date=cal.getTime();
		cal.clear();//时间类是单例，不复原会导致以后查询出错
		return date;
	}
	public static void reSet(){
		if (initDate!=null) {
			cal.setTime(initDate);
		}else{
			cal.clear();
		}
	}
}
