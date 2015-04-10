package cn.com.yisi.util;

public class Constants {
	public static final String FLAG="flag";
	public static final String FLAG_SEND="send";
	public static final String FLAG_RECEIVE="receive";

	public static final String TYPE="type";
	public static final int TYPE_ENTER=0;
	public static final int TYPE_EXIT=3;
	public static final int TYPE_ALL=-1;
	public static final String TYPE_DETAILS="details";
	public static final String TYPE_GATHER="gather";
	public static final String TYPE_CONTACTS_DETAILS="contactsDetails";

	public static final String MODE="mode";
	
	public static final String LOGIN_STATUS_SUCCESS="success";
	public static final String LOGIN_STATUS_USERORPWD_ERR="userOnPwdErr";
	public static final String LOGIN_STATUS_OTHER="other";
	
	public static final String HAS_INITED="inited";
	
	public static final String LOGIN_ACCOUNT="loginAccount";
	public static final String LOGIN_PWD="loginPwd";
	public static final String LOGIN_REPWD="rememberPwd";
	public static final String LOGIN_AUTO="autoLogin";
	
	private static String URL="http://192.168.1.117:8080/webDemo/";
	public static final String URL_LOGIN=URL+"base/userLogin.action";
	public static final String URL_CUR_WEIGHT=URL+"inventory/wbWeightStatistics.action";
	public static final String URL_CONDITION=URL+"inventory/getInventoryDetails.action";
	public static final String URL_DETAILS_LIST=URL+"inventory/getInventoryDetailsList.action";
	public static final String URL_GATHER_LIST=URL+"inventory/getInventoryDetailsGroupCollect.action";
	public static final String URL_DETAILS_STATISTICS=URL+"inventory/getInventoryDetailsListCount.action";
//	private static String URL="http://192.168.1.2:8080/app/YiSiApp.aspx?";
//	public static final String URL_LOGIN=URL+"option=login&";
//	public static final String URL_CUR_WEIGHT=URL+"option=getRecentlyWeight&";
	public static final String json="[{'cars':'11263','drivers':null,'gross':26.870,'lightdate':'2015-03-24 10:05:03','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':25.870,'weightdate':'2015-03-24 10:52:39','weightno':'01 '},{'cars':'11231','drivers':null,'gross':27.870,'lightdate':'2015-03-24 11:18:51','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':26.870,'weightdate':'2015-03-24 11:19:39','weightno':'01 '},{'cars':'11215','drivers':null,'gross':29.870,'lightdate':'2015-03-24 11:31:59','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':2.000,'tare':27.870,'weightdate':'2015-03-24 11:32:57','weightno':'01 '},{'cars':'11181','drivers':null,'gross':29.870,'lightdate':'2015-03-25 11:11:50','lightno':'01 ','material':'石灰石','store':null,'supply':'唐山市飞翔机械设备有限公司','suttle':2.000,'tare':27.870,'weightdate':'2015-03-25 11:24:25','weightno':'01 '},{'cars':'11177','drivers':null,'gross':27.870,'lightdate':'2015-03-25 11:47:20','lightno':'01 ','material':'石灰石','store':null,'supply':'唐山市飞翔机械设备有限公司','suttle':3.000,'tare':24.870,'weightdate':'2015-03-25 11:50:12','weightno':'01 '},{'cars':'11117','drivers':null,'gross':25.060,'lightdate':'2015-03-27 12:02:04','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'青岛东兴锅炉设备制造有限公司','suttle':15.060,'tare':10.000,'weightdate':'2015-03-27 12:02:04','weightno':'01 '},{'cars':'11225','drivers':null,'gross':21.870,'lightdate':'2015-03-27 17:03:37','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':2.000,'tare':19.870,'weightdate':'2015-03-27 17:04:27','weightno':'01 '},{'cars':'11124','drivers':null,'gross':20.000,'lightdate':'2015-03-27 17:14:10','lightno':'01 ','material':'萤石','store':null,'supply':'唐山市飞翔机械设备有限公司','suttle':8.130,'tare':11.870,'weightdate':'2015-03-27 17:14:53','weightno':'01 '},{'cars':'11128','drivers':null,'gross':12.870,'lightdate':'2015-03-27 19:09:01','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':11.870,'weightdate':'2015-03-27 19:10:07','weightno':'01 '},{'cars':'11431','drivers':null,'gross':13.870,'lightdate':'2015-03-27 19:45:52','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':12.870,'weightdate':'2015-03-27 19:46:44','weightno':'01 '},{'cars':'11129','drivers':null,'gross':19.870,'lightdate':'2015-03-27 19:49:09','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':3.000,'tare':16.870,'weightdate':'2015-03-27 19:50:00','weightno':'01 '},{'cars':'11128','drivers':null,'gross':20.870,'lightdate':'2015-03-28 10:52:34','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':19.870,'weightdate':'2015-03-28 10:53:33','weightno':'01 '},{'cars':'11606','drivers':null,'gross':21.870,'lightdate':'2015-03-28 15:26:10','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':20.870,'weightdate':'2015-03-28 15:26:58','weightno':'01 '},{'cars':'11266','drivers':null,'gross':21.870,'lightdate':'2015-03-28 17:14:12','lightno':'01 ','material':'石灰石','store':null,'supply':'唐山市飞翔机械设备有限公司','suttle':10.000,'tare':11.870,'weightdate':'2015-03-28 17:15:16','weightno':'01 '}]";
}
