package cn.com.yisi.widget.chart;


public class QuintEaseOut implements BaseEasingMethod{

	public QuintEaseOut() {
        super();
    }

    @Override
    public float next(float time) {
        return (float) Math.pow(time - 1, 5) + 1;
    }
}
