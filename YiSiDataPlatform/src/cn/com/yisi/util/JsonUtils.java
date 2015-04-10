package cn.com.yisi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.yisi.entity.EntityContidion;
import cn.com.yisi.entity.EntityLogin;
import cn.com.yisi.entity.Test;

import com.alibaba.fastjson.JSON;

//fastJson用法
public class JsonUtils {
	// json转实体
	public static <T> T jsonToObj(String json, Class<T> t) {
		return JSON.parseObject(json, t);
	}

	// 实体转json
	public static <T> String objToJson(T t) {
		return JSON.toJSONString(t);
	}

	public static <T> List<T> jsonToList(String json, Class<T> className) {
		return JSON.parseArray(json, className);
	}

	public static void main(String[] args) {
//		String json = "[{'cars':'11263','drivers':null,'gross':26.870,'lightdate':'2015-03-24 10:05:03','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':25.870,'weightdate':'2015-03-24 10:52:39','weightno':'01 '},{'cars':'11231','drivers':null,'gross':27.870,'lightdate':'2015-03-24 11:18:51','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':26.870,'weightdate':'2015-03-24 11:19:39','weightno':'01 '},{'cars':'11215','drivers':null,'gross':29.870,'lightdate':'2015-03-24 11:31:59','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':2.000,'tare':27.870,'weightdate':'2015-03-24 11:32:57','weightno':'01 '},{'cars':'11181','drivers':null,'gross':29.870,'lightdate':'2015-03-25 11:11:50','lightno':'01 ','material':'石灰石','store':null,'supply':'唐山市飞翔机械设备有限公司','suttle':2.000,'tare':27.870,'weightdate':'2015-03-25 11:24:25','weightno':'01 '},{'cars':'11177','drivers':null,'gross':27.870,'lightdate':'2015-03-25 11:47:20','lightno':'01 ','material':'石灰石','store':null,'supply':'唐山市飞翔机械设备有限公司','suttle':3.000,'tare':24.870,'weightdate':'2015-03-25 11:50:12','weightno':'01 '},{'cars':'11117','drivers':null,'gross':25.060,'lightdate':'2015-03-27 12:02:04','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'青岛东兴锅炉设备制造有限公司','suttle':15.060,'tare':10.000,'weightdate':'2015-03-27 12:02:04','weightno':'01 '},{'cars':'11225','drivers':null,'gross':21.870,'lightdate':'2015-03-27 17:03:37','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':2.000,'tare':19.870,'weightdate':'2015-03-27 17:04:27','weightno':'01 '},{'cars':'11124','drivers':null,'gross':20.000,'lightdate':'2015-03-27 17:14:10','lightno':'01 ','material':'萤石','store':null,'supply':'唐山市飞翔机械设备有限公司','suttle':8.130,'tare':11.870,'weightdate':'2015-03-27 17:14:53','weightno':'01 '},{'cars':'11128','drivers':null,'gross':12.870,'lightdate':'2015-03-27 19:09:01','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':11.870,'weightdate':'2015-03-27 19:10:07','weightno':'01 '},{'cars':'11431','drivers':null,'gross':13.870,'lightdate':'2015-03-27 19:45:52','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':12.870,'weightdate':'2015-03-27 19:46:44','weightno':'01 '},{'cars':'11129','drivers':null,'gross':19.870,'lightdate':'2015-03-27 19:49:09','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':3.000,'tare':16.870,'weightdate':'2015-03-27 19:50:00','weightno':'01 '},{'cars':'11128','drivers':null,'gross':20.870,'lightdate':'2015-03-28 10:52:34','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':19.870,'weightdate':'2015-03-28 10:53:33','weightno':'01 '},{'cars':'11606','drivers':null,'gross':21.870,'lightdate':'2015-03-28 15:26:10','lightno':'01 ','material':'32.5复合袋装水泥','store':null,'supply':'新疆永兴汇源机电设备制造有限公司','suttle':1.000,'tare':20.870,'weightdate':'2015-03-28 15:26:58','weightno':'01 '},{'cars':'11266','drivers':null,'gross':21.870,'lightdate':'2015-03-28 17:14:12','lightno':'01 ','material':'石灰石','store':null,'supply':'唐山市飞翔机械设备有限公司','suttle':10.000,'tare':11.870,'weightdate':'2015-03-28 17:15:16','weightno':'01 '}]";
//		List<Test> list = jsonToList(json, Test.class);
//		for (Test test2 : list) {
//			System.out.println(test2.getCars());
//		}
		EntityContidion con=new EntityContidion();
		Map<String, String> person=new HashMap<String, String>();
		person.put("大同煤业", "deg121");
		person.put("大同煤业2", "deg122");
		person.put("大同煤业3", "deg123");
		Map<String, String> material=new HashMap<String, String>();
		material.put("石灰", "1s451");
		material.put("石灰2", "1s452");
		material.put("石灰3", "1s453");
		Map<String,String> car=new HashMap<String, String>();
		car.put("123456", "晋A123456");
		car.put("123457", "晋A123457");
		car.put("123458", "晋A123458");
		con.setCars("晋");
		con.setStatus("已交票，已发货，已派车");
		con.setPersons(person);
		con.setMaterials(material);
		con.setType(0);
		
		EntityContidion con1=new EntityContidion();
		con1.setCars("晋");
		con1.setStatus("已交票，已发货，已派车");
		con1.setPersons(person);
		con1.setMaterials(material);
		con1.setType(3);
		List<EntityContidion> list=new ArrayList<EntityContidion>();
		list.add(con);
		list.add(con1);
		System.out.println(objToJson(list));
//		[{"cars":"晋,甘,赣,桂,黑,冀,津,晋,京,辽,蒙,宁,青,琼,苏,湘,新,渝,豫,云,浙","materials":{"石灰":"1s451","石灰2":"1s452","石灰3":"1s453"},"persons":{"大同煤业":"deg121","大同煤业2":"deg122","大同煤业3":"deg123"},"status":"已交票，已发货，已派车","type":0},{"cars":"晋","materials":{"$ref":"$[0].materials"},"persons":{"$ref":"$[0].persons"},"status":"已交票，已发货，已派车","type":3}]
//		[{"cars":"川,甘,赣,桂,黑,冀,津,晋,京,辽,蒙,宁,青,琼,苏,湘,新,渝,豫,云,浙","materials":[{"0001A1100000000GT9L0":"32.5R普通袋装水泥"},{"0001A1100000000GT9L2":"32.5复合袋装水泥"},{"0001G2100000000A5OZ6":"风化煤"},{"0001H11000000001MLWQ":"石灰石"},{"0001H11000000001MLWT":"黄土"},{"0001H11000000001MLWZ":"萤石"}],"persons":[{"0001A1100000000URARY":"新疆永兴汇源机电设备制造有限公司"}],"status":"已交票,已发货,已派车","type":"0"},{"cars":"川,甘,赣,桂,黑,冀,津,晋,京,辽,蒙,宁,青,琼,苏,湘,新,渝,豫,云,浙","materials":[{"0001A1100000000GT9L0":"32.5R普通袋装水泥"},{"0001A1100000000GT9L2":"32.5复合袋装水泥"},{"0001G2100000000A5OZ6":"风化煤"},{"0001H11000000001MLWQ":"石灰石"},{"0001H11000000001MLWT":"黄土"},{"0001H11000000001MLWZ":"萤石"}],"persons":[{"0001A5100000000068IT":"四平鼓风机股份有限公司"},{"0001A51000000000691Z":"中国建筑材料检验认证中心"},{"0001A51000000000AT38":"青岛东兴锅炉设备制造有限公司"},{"0001A51000000000B4O4":"扬州市飞龙化工环保设备厂"},{"0001A51000000000FBQ2":"大连海华控制系统工程有限公司"},{"0001N81000000003Z7IZ":"唐山市飞翔机械设备有限公司"},{"GY01000001":"张三"}],"status":"已交票,已发货,已派车","type":"3"}]
	}
}
