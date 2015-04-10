package cn.com.yisi.service;

import java.util.ArrayList;
import java.util.List;

import cn.com.yisi.entity.EntityDetails;
import cn.com.yisi.entity.EntityGather;
import cn.com.yisi.entity.Test;
import cn.com.yisi.util.WebHelper;

public class Service {
	public static List<EntityDetails> getDateFromWeb(){
		List<EntityDetails>  results=new ArrayList<EntityDetails>();
		EntityDetails e=null;
		WebHelper<Test> h=new WebHelper<Test>(Test.class);
		List<Test> list=h.getArray("http://www.baidu.com", null);
		for (Test test : list) {
			e=new EntityDetails();
			e.setCarCode(test.getCars());
			e.setCompany(test.getSupply());
			e.setMaterialType(test.getMaterial());
			e.setTime(test.getLightdate());
			e.setWeight(test.getSuttle());
			results.add(e);
		}
		return results;
	}
	public static List<EntityGather> getDateFromWeb1(){
		List<EntityGather>  results=new ArrayList<EntityGather>();
		EntityGather e=null;
		WebHelper<Test> h=new WebHelper<Test>(Test.class);
		List<Test> list=h.getArray("http://www.baidu.com", null);
		for (Test test : list) {
			e=new EntityGather();
			e.setCarTimes(test.getCars());
			e.setCompany(test.getSupply());
			e.setMaterialType(test.getMaterial());
			e.setTime(test.getLightdate());
//			e.setWeightCount(test.getSuttle());
			results.add(e);
		}
		return results;
	}
}
