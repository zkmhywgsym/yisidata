package cn.com.yisi.entity;
//明细实体
public class EntityDetails {
	private String provider;//供应商/客户
	private String metariel;//物料
	private String carNum;//车牌号/车数
	private String weight;//净重
	private String time;//轻车时间/重车时间
	private String type;//发货/收货
	private String statue;//状态
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
