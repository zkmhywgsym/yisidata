package cn.com.yisi.widget.chart;


public class QuadEaseOut implements BaseEasingMethod{
    
	public QuadEaseOut() {
        super();
    }

    @Override
    public float next(float time) {
        return -time * (time - 2);
    }
}
