package cn.com.yisi;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.com.yisi.util.Constants;
import cn.com.yisi.util.ExitManager;
import cn.com.ysdp.R;

@SuppressLint("HandlerLeak")
public class GuideActivity extends Activity {
	/** 开始初始化 */
	private static final int START_INIT_GUIDE = 0;
	/** 开始登陆 */
	private static final int START_LOGIN = 1;
	/** 当前页面 */
	private static final int CUR_PAGE = 2;
	private int[] pageViewImages;
	private ViewPager pager;
	private ArrayList<View> pageViews;
	private RelativeLayout guideWelcom;
	boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide_layout);
		ExitManager.getInstance().addActivity(this);
		initView();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case START_LOGIN: {
				goLogin(null);
				break;
			}
			case START_INIT_GUIDE: {
				guideWelcom.setVisibility(View.GONE);
				break;
			}
			case CUR_PAGE: {
				pager.setCurrentItem(msg.arg1);
				break;
			}
			}
		}
	};

	private void initView() {
		pageViews = new ArrayList<View>();
		pageViewImages = new int[] { R.drawable.home_btn_bg_s,
				R.drawable.main_index };
		pager = (ViewPager) findViewById(R.id.guideViewPager);
		guideWelcom = (RelativeLayout) findViewById(R.id.guide_welcom);
		if (Boolean.valueOf(YisiApp.getValue(Constants.HAS_INITED, "false"))) {
			handler.sendEmptyMessageDelayed(START_LOGIN, 3000);
		} else {

			handler.sendEmptyMessageDelayed(START_INIT_GUIDE, 3000);
			for (int i = 0; i < pageViewImages.length; i++) {
				LinearLayout layout = new LinearLayout(this);
				layout.setBackgroundResource(pageViewImages[i]);
				pageViews.add(layout);
			}
			GuidePagerAdapter adapter = new GuidePagerAdapter(pageViews);
			pager.setAdapter(adapter);
			pager.setOnPageChangeListener(new OnPageChangeListener() {
				private int curPage;

				@Override
				public void onPageSelected(int index) {
					curPage = index;
				}

				@Override
				public void onPageScrolled(int paramInt1, float paramFloat,
						int paramInt2) {

				}

				@Override
				public void onPageScrollStateChanged(int state) {
					if (state == ViewPager.SCROLL_STATE_IDLE
							&& curPage == pager.getAdapter().getCount() - 1) {
						YisiApp.setValue(Constants.HAS_INITED, true + "");
						handler.sendEmptyMessageDelayed(START_LOGIN, 3000);
					}
				}
			});
		}
	}

	/**
	 * 进入登陆界面
	 */
	public void goLogin(View v) {
		Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	class GuidePagerAdapter extends PagerAdapter {
		ArrayList<View> myLayouts;

		public GuidePagerAdapter(ArrayList<View> layouts) {
			myLayouts = layouts;
		}

		@Override
		public int getCount() {
			return myLayouts.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(myLayouts.get(position), 0);
			return myLayouts.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(myLayouts.get(position));
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

	}

}
