package cn.com.yisi.entity;

import java.util.List;
import java.util.Map;

public class EntityContidion {
	private int type;//出、入库（）
	private Map<String, String> persons;
	private String cars;
	private Map<String, String> materials;
	private String status;
	public Map<String, String> getPersons() {
		return persons;
	}
	public void setPersons(Map<String, String> persons) {
		this.persons = persons;
	}
	
	public String getCars() {
		return cars;
	}
	public void setCars(String cars) {
		this.cars = cars;
	}
	public Map<String, String> getMaterials() {
		return materials;
	}
	public void setMaterials(Map<String, String> materials) {
		this.materials = materials;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
