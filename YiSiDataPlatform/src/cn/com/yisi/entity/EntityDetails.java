package cn.com.yisi.entity;
//详情实体
public class EntityDetails {
	private String provider;//供应商/客户
	private String metariel;//物料
	private String carNum;//车号
	private String weight;//净重
	private String weightTime;//入库/轻车时间
	private String lightTime;//出库/重车时间
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getMetariel() {
		return metariel;
	}
	public void setMetariel(String metariel) {
		this.metariel = metariel;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getWeightTime() {
		return weightTime;
	}
	public void setWeightTime(String weightTime) {
		this.weightTime = weightTime;
	}
	public String getLightTime() {
		return lightTime;
	}
	public void setLightTime(String lightTime) {
		this.lightTime = lightTime;
	}
	
}
