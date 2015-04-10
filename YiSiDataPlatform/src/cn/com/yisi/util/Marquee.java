package cn.com.yisi.util;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

public class Marquee extends TextView{
	public Marquee(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public Marquee(Context context){
		super(context);
	}

	
	@Override
	public void setFocusable(boolean focusable) {
		super.setFocusable(true);
	}
	@Override
	public void setFocusableInTouchMode(boolean focusableInTouchMode) {
		super.setFocusableInTouchMode(true);
	}
	@Override
	public void setSingleLine() {
		super.setSingleLine(true);
	}
	@Override
	public void setEllipsize(TruncateAt where) {
		super.setEllipsize(TruncateAt.MARQUEE);
	}
	@Override
	public void setMarqueeRepeatLimit(int marqueeLimit) {
		super.setMarqueeRepeatLimit(-1);
	}
	@Override
	public void setHorizontallyScrolling(boolean whether) {
		super.setHorizontallyScrolling(true);
	}
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		return true;
	}

}
