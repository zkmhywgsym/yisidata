package cn.com.yisi.widget.chart;

import android.content.Context;
import android.content.res.Resources;

//折线设置实体
public class MyLineSet {
	private float dotsRadius;
	private float dotStrokeThickness;
	private int dotsColor;
	private int dotStrokeColor;
	private int lineColor;
	private float lineThickness;
	private int beginAt=0;
	private int endAt=0;
	private boolean hasDots;
	private boolean isSmooth;
	private boolean isDashed;
	public MyLineSet(Context context) {
		super();
		this.dotsColor = Resources.getSystem().getColor(android.R.color.white);
		this.dotStrokeColor = Resources.getSystem().getColor(android.R.color.white);
		this.lineColor = Resources.getSystem().getColor(android.R.color.white);
		this.hasDots=true;
		this.isDashed=false;
		this.isSmooth=false;
		this.dotsRadius=Tools.fromDpToPx(2);
		this.dotStrokeThickness=Tools.fromDpToPx(2);
		this.lineThickness=Tools.fromDpToPx(3);
	}
	public boolean isHasDots() {
		return hasDots;
	}
	/**是否加圆点*/
	public void setHasDots(boolean hasDots) {
		this.hasDots = hasDots;
	}
	
	public float getDotsRadius() {
		return dotsRadius;
	}
	/**圆半径Tools.fromDpToPx(2)*/
	public void setDotsRadius(float dotsRadius) {
		this.dotsRadius = dotsRadius;
	}
	public int getDotsColor() {
		return dotsColor;
	}
	
	/**圆边颜色getResources().getColor(R.color.xxx)*/
	public void setDotsColor(int dotsColor) {
		this.dotsColor = dotsColor;
	}
	public float getDotStrokeThickness() {
		return dotStrokeThickness;
	}
	/**圆边密度Tools.fromDpToPx(2)*/
	public void setDotStrokeThickness(int dotStrokeThickness) {
		this.dotStrokeThickness = dotStrokeThickness;
	}
	public int getDotStrokeColor() {
		return dotStrokeColor;
	}
	/**圆边色getResources().getColor(R.color.xxx)*/
	public void setDotStrokeColor(int dotStrokeColor) {
		this.dotStrokeColor = dotStrokeColor;
	}
	public int getLineColor() {
		return lineColor;
	}
	/**线色getResources().getColor(R.color.xxx)*/
	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}
	public float getLineThickness() {
		return lineThickness;
	}
	/**线密度Tools.fromDpToPx(3)*/
	public void setLineThickness(int lineThickness) {
		this.lineThickness = lineThickness;
	}
	public int getBeginAt() {
		return beginAt;
	}
	/**开始点0*/
	public void setBeginAt(int beginAt) {
		this.beginAt = beginAt;
	}
	public int getEndAt() {
		return endAt;
	}
	/**结束点size-1*/
	public void setEndAt(int endAt) {
		this.endAt = endAt;
	}
	public boolean isSmooth() {
		return isSmooth;
	}
	/**平滑*/
	public void setSmooth(boolean isSmooth) {
		this.isSmooth = isSmooth;
	}
	
	public boolean isDashed() {
		return isDashed;
	}
	/**破折*/
	public void setDashed(boolean isDashed) {
		this.isDashed = isDashed;
	}
	
}