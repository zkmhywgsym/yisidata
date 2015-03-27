package cn.com.yisi.entity;

import java.util.List;

public class EntityHomtItemParent {
	private String title;
	private int backGroundColor;
	private List<EntityHomtItemChild> childs;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<EntityHomtItemChild> getChilds() {
		return childs;
	}
	public void setChilds(List<EntityHomtItemChild> childs) {
		this.childs = childs;
	}
	public int getBackGroundColor() {
		return backGroundColor;
	}
	public void setBackGroundColor(int backGroundColor) {
		this.backGroundColor = backGroundColor;
	}
	
	
}
