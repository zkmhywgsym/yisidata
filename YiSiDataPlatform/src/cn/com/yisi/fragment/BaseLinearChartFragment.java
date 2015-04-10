package cn.com.yisi.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.com.yisi.widget.chart.Animation;
import cn.com.yisi.widget.chart.BaseEasingMethod;
import cn.com.yisi.widget.chart.DashAnimation;
import cn.com.yisi.widget.chart.LineChartView;
import cn.com.yisi.widget.chart.LineSet;
import cn.com.yisi.widget.chart.MyChartSetEntity;
import cn.com.yisi.widget.chart.MyLineSet;
import cn.com.yisi.widget.chart.OnEntryClickListener;
import cn.com.yisi.widget.chart.QuintEaseOut;
import cn.com.yisi.widget.chart.Tools;
import cn.com.ysdp.R;
//折线图父类
public abstract class BaseLinearChartFragment extends BaseFragment {
	private  LineChartView mLineChart;//图表
	private TextView mLineTooltip;//弹框
	private Paint mLineGridPaint;//风格
	private final TimeInterpolator enterInterpolator = new DecelerateInterpolator(
			1.5f);
	private final TimeInterpolator exitInterpolator = new AccelerateInterpolator();
	private  float[][] lineValues = {
			{ -5f, 6f, 2f, 9f, 8f, 1f, 5f },
			{ -9f, -2f, -4f, -3f, -7f, -5f, -3f },{1f,1f,1f,1f,1f,1f,1f}};//默认表值
	private  String[] lineLabels = { "", "ANT", "GNU", "OWL",
			"APE", "JAY", "" };//默认标签
	protected float[][] currentValues;//当前值
	protected MyChartSetEntity currentSets;
	protected int[] defaultColors=new int[]{Color.RED,Color.BLUE,Color.CYAN,Color.DKGRAY,Color.GREEN,Color.GRAY,Color.MAGENTA,Color.YELLOW};//默认颜色
	private  float mCurrOverlapFactor;
	private  int[] mCurrOverlapOrder;
	private  float mOldOverlapFactor;
	private  int[] mOldOverlapOrder;
	private  BaseEasingMethod mCurrEasing;
	private  BaseEasingMethod mOldEasing;
	private  float mCurrStartX;
	private  float mCurrStartY;
	private  float mOldStartX;
	private  float mOldStartY;
	private  int mCurrAlpha;
	private  int mOldAlpha;

	protected void initLineChart(LineChartView mLineChartView) {

		mLineChart = mLineChartView;
		mLineChart.setOnClickListener(lineClickListener);//点击事件
		mLineChart.setOnEntryClickListener(lineEntryListener);
		mLineGridPaint = new Paint();
		mLineGridPaint
				.setColor(this.getResources().getColor(R.color.line_grid));
		mLineGridPaint
				.setPathEffect(new DashPathEffect(new float[] { 5, 5}, 0));
		mLineGridPaint.setStyle(Paint.Style.STROKE);
		mLineGridPaint.setAntiAlias(true);
		mLineGridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));
		initValue();
		updateLineChart(getLineSets());
	}
	protected void update(){
		initValue();
		updateLineChart(getLineSets());
	}
	protected void showSingelLine(int index){
		lineValues=new float[][]{setChartValues()[index]};
		ArrayList<MyLineSet> lineSet=new ArrayList<MyLineSet>();
		lineSet.add(getLineSets().getLineSets().get(index));
		currentSets.setLineSets(lineSet);
		updateLineChart(currentSets);
	}
	protected void showAllLine(){
		initValue();
		updateLineChart(getLineSets());
	}
	//获取当前的表设置，没设置时取新建一个默认设置
	protected MyChartSetEntity getCurrentSets(){
		if (currentSets==null) {
			currentSets=new MyChartSetEntity(mainActivity);
			List<MyLineSet> lineSets=new ArrayList<MyLineSet>();
			lineSets.add(new MyLineSet(mainActivity));
			currentSets.setLineSets(lineSets);
		}
		return currentSets;
	}
	private void initValue() {
		mCurrOverlapFactor = 1;
		mCurrEasing = new QuintEaseOut();
		mCurrStartX = -1;
		mCurrStartY = 0;
		mCurrAlpha = -1;

		mOldOverlapFactor = 1;
		mOldEasing = new QuintEaseOut();
		mOldStartX = -1;
		mOldStartY = 0;
		mOldAlpha = -1;
		if (setChartLabels().length>0&&setChartValues().length>0) {
			lineLabels=setChartLabels();
			lineValues=setChartValues();
		}
	}

	protected abstract String[] setChartLabels();
	protected abstract float[][] setChartValues();
	//获取定义的折线图设置
	protected abstract MyChartSetEntity getLineSets();
	private final OnClickListener lineClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mLineTooltip != null)
				dismissLineTooltip(-1, -1, null);
		}
	};
	private final OnEntryClickListener lineEntryListener = new OnEntryClickListener() {
		@Override
		public void onClick(int setIndex, int entryIndex, Rect rect) {

			if (mLineTooltip == null)
				showLineTooltip(setIndex, entryIndex, rect);
			else
				dismissLineTooltip(setIndex, entryIndex, rect);
		}
	};

	@SuppressLint("NewApi")
	private void dismissLineTooltip(final int setIndex, final int entryIndex,
			final Rect rect) {

		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			mLineTooltip.animate().setDuration(100).scaleX(0).scaleY(0)
					.alpha(0).setInterpolator(exitInterpolator)
					.withEndAction(new Runnable() {
						@Override
						public void run() {
							mLineChart.removeView(mLineTooltip);
							mLineTooltip = null;
							if (entryIndex != -1)
								showLineTooltip(setIndex, entryIndex, rect);
						}
					});
		} else {
			mLineChart.dismissTooltip(mLineTooltip);
			mLineTooltip = null;
			if (entryIndex != -1)
				showLineTooltip(setIndex, entryIndex, rect);
		}
	}

	@SuppressLint("NewApi")
	private void showLineTooltip(int setIndex, int entryIndex, Rect rect) {

		mLineTooltip = (TextView) getActivity().getLayoutInflater().inflate(
				R.layout.circular_tooltip, null);
		mLineTooltip.setText(Integer
				.toString((int) lineValues[setIndex][entryIndex]));

		LayoutParams layoutParams = new LayoutParams(
				(int) Tools.fromDpToPx(35), (int) Tools.fromDpToPx(35));
		layoutParams.leftMargin = rect.centerX() - layoutParams.width / 2;
		layoutParams.topMargin = rect.centerY() - layoutParams.height / 2;
		mLineTooltip.setLayoutParams(layoutParams);

		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			mLineTooltip.setPivotX(layoutParams.width / 2);
			mLineTooltip.setPivotY(layoutParams.height / 2);
			mLineTooltip.setAlpha(0);
			mLineTooltip.setScaleX(0);
			mLineTooltip.setScaleY(0);
			mLineTooltip.animate().setDuration(150).alpha(1).scaleX(1)
					.scaleY(1).rotation(360).setInterpolator(enterInterpolator);
		}

		mLineChart.showTooltip(mLineTooltip);
	}

	private void updateLineChart(MyChartSetEntity sets) {
		mLineChart.reset();
		sets.setAxisBorderMax((int) getMaxValFromSets());
		LineSet dataSet = null;
		MyLineSet mLineSet;
		for (int i = 0; i < lineValues.length; i++) {
			dataSet=new LineSet();
			if (lineValues.length>sets.getLineSets().size()) {
				mLineSet=sets.getLineSets().get(0);
			}else{
				mLineSet=sets.getLineSets().get(i);
			}
			dataSet.addPoints(lineLabels, lineValues[i]);
			dataSet.setDots(mLineSet.isHasDots())//加圆点
			.setDotsColor(mLineSet.getDotsColor())//圆点颜色
			.setDotsRadius(mLineSet.getDotsRadius())//圆点大小
			.setDotsStrokeThickness(mLineSet.getDotStrokeThickness())//圆线粗
			.setDotsStrokeColor(mLineSet.getDotStrokeColor())//圆线颜色
			.setLineColor(mLineSet.getLineColor())//折线颜色
			.setSmooth(mLineSet.isSmooth())//连续
			.setDashed(mLineSet.isDashed())//破折号
			.setLineThickness(mLineSet.getLineThickness());//折线密度
			if (mLineSet.getBeginAt()<0||mLineSet.getBeginAt()>lineLabels.length-1) {
				dataSet.beginAt(mLineSet.getBeginAt());//开始点
			}else{
				dataSet.beginAt(0);//开始点
			}
			if (mLineSet.getEndAt()<mLineSet.getBeginAt()||mLineSet.getEndAt()>lineLabels.length-1) {
				dataSet.endAt(mLineSet.getEndAt());//开始点
			}else{
				dataSet.endAt(lineLabels.length );//开始点
			}
			mLineChart.addData(dataSet);
		}
		mLineChart.setBorderSpacing(sets.getBorderSpacing())//边框空间（左右边距）
				.setGrid(sets.getGridType(), mLineGridPaint)//网格
				.setXAxis(sets.isHideXAxis())//X是否不可见
				.setXLabels(sets.getxLabelsPosition())//标签位置(内，处，无)
				.setYAxis(sets.isHideYAxis()).setYLabels(sets.getyLabelsPosition())
				.setAxisBorderValues(sets.getAxisBorderMin(), sets.getAxisBorderMax(),(sets.getAxisBorderMin()+sets.getAxisBorderMax())/2)//纵坐标（最小，最大，距离）
				.setLabelsFormat(new DecimalFormat(sets.getDecimalFormatPattern()))//单位
//				.show(getAnimation(true))
				.show()
		;

//		mLineChart.animateSet(1, new DashAnimation());
	}

	private float getMaxValFromSets(){
		float max=0;
		for (float[] f : lineValues) {
			for (float g : f) {
				max=max>g?max:g;
			}
		}
		return max>0?max:10;
	}
	// 获取动画
	private Animation getAnimation(boolean newAnim) {
		if (newAnim)
			return new Animation().setAlpha(mCurrAlpha).setEasing(mCurrEasing)
					.setOverlap(mCurrOverlapFactor, mCurrOverlapOrder)
					.setStartPoint(mCurrStartX, mCurrStartY);
		else
			return new Animation().setAlpha(mOldAlpha).setEasing(mOldEasing)
					.setOverlap(mOldOverlapFactor, mOldOverlapOrder)
					.setStartPoint(mOldStartX, mOldStartY);
	}
}
