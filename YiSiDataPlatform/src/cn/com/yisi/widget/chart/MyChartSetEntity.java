package cn.com.yisi.widget.chart;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cn.com.yisi.widget.chart.AxisController.LabelPosition;
import cn.com.yisi.widget.chart.ChartView.GridType;

//折线图设置实体
public class MyChartSetEntity {
	private List<MyLineSet> lineSets;
	private float borderSpacing;
	private GridType gridType;
	private boolean hideXAxis;
	private boolean hideYAxis;
	private LabelPosition xLabelsPosition;
	private LabelPosition yLabelsPosition;
	private int axisBorderMax;
	private int axisBorderMin;
	private int axisBorderDistance;
	private String decimalFormatPattern;
	
	public MyChartSetEntity(Context context) {
		super();
		this.lineSets=new ArrayList<MyLineSet>();
		this.lineSets.add(new MyLineSet(context));
		this.borderSpacing = Tools.fromDpToPx(4);
		this.gridType = LineChartView.GridType.HORIZONTAL;
		this.hideXAxis = false;
		this.hideYAxis = false;
		this.xLabelsPosition = XController.LabelPosition.OUTSIDE;
		this.yLabelsPosition = YController.LabelPosition.OUTSIDE;
		this.axisBorderMax = 10;
		this.axisBorderMin = -10;
		this.axisBorderDistance = 6;
		this.decimalFormatPattern = "##'T'";
	}
	public List<MyLineSet> getLineSets() {
		return lineSets;
	}
	/**每条线设置*/
	public void setLineSets(List<MyLineSet> lineSets) {
		this.lineSets = lineSets;
	}
	public float getBorderSpacing() {
		return borderSpacing;
	}
	/**左右距离Tools.fromDpToPx(4)*/
	public void setBorderSpacing(float borderSpacing) {
		this.borderSpacing = borderSpacing;
	}
	public GridType getGridType() {
		return gridType;
	}
	/**显示网格LineChartView.GridType.HORIZONTAL/vertical/full*/
	public void setGridType(GridType gridType) {
		this.gridType = gridType;
	}
	public boolean isHideXAxis() {
		return hideXAxis;
	}
	/**隐藏x标签false*/
	public void setHideXAxis(boolean hideXAxis) {
		this.hideXAxis = hideXAxis;
	}
	public boolean isHideYAxis() {
		return hideYAxis;
	}
	/**隐藏Y标签false*/
	public void setHideYAxis(boolean hideYAxis) {
		this.hideYAxis = hideYAxis;
	}
	public LabelPosition getxLabelsPosition() {
		return xLabelsPosition;
	}
	/**设置x坐标位置xController.LabelPosition.outside /inside/none*/
	public void setxLabelsPosition(LabelPosition xLabelsPosition) {
		this.xLabelsPosition = xLabelsPosition;
	}
	public LabelPosition getyLabelsPosition() {
		return yLabelsPosition;
	}
	/**设置Y坐标位置YController.LabelPosition.outside /inside/none*/
	public void setyLabelsPosition(LabelPosition yLabelsPosition) {
		this.yLabelsPosition = yLabelsPosition;
	}
	public int getAxisBorderMax() {
		return axisBorderMax;
	}
	/**设置纵坐标最大10*/
	public void setAxisBorderMax(int axisBorderMax) {
		this.axisBorderMax = axisBorderMax;
	}
	public int getAxisBorderMin() {
		return axisBorderMin;
	}
	/**设置纵坐标最小-10*/
	public void setAxisBorderMin(int axisBorderMin) {
		this.axisBorderMin = axisBorderMin;
	}
	public int getAxisBorderDistance() {
		return axisBorderDistance;
	}
	/**设置纵坐标距离6*/
	public void setAxisBorderDistance(int axisBorderDistance) {
		this.axisBorderDistance = axisBorderDistance;
	}
	public String getDecimalFormatPattern() {
		return decimalFormatPattern;
	}
	/**设置纵坐标单位"##'T'"*/
	public void setDecimalFormatPattern(String decimalFormatPattern) {
		this.decimalFormatPattern = decimalFormatPattern;
	}
	
	
	
	
}
