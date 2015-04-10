package cn.com.yisi.entity;
//汇总实体
public class EntityGather {
	private String company;//供应商/客户
	private String materialType;//物料
	private String carTimes;//车数
	private String weight;//净重
	private String time;//轻车时间/重车时间
	private String type;//发货/收货
	private String statue;//状态
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	
	public String getCarTimes() {
		return carTimes;
	}
	public void setCarTimes(String carTimes) {
		this.carTimes = carTimes;
	}
	
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatue() {
		return statue;
	}
	public void setStatue(String statue) {
		this.statue = statue;
	}
	
	
}
