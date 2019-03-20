package yokastore.youkagames.com.yokastore.view.rollpagerview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import yokastore.youkagames.com.yokastore.view.OnItemClickListener;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 支持轮播和提示的的viewpager
 */
public class RollTimerPagerView extends RollPagerView {

    private OnItemClickListener mOnItemClickListener;
    private GestureDetector mGestureDetector;

    private long mRecentTouchTime;
    private Timer timer;

    public RollTimerPagerView(Context context) {
        this(context, null);
    }

    public RollTimerPagerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public RollTimerPagerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    /**
     * 读取提示形式  和   提示位置   和    播放延迟
     *
     * @param attrs
     */
    private void initView(AttributeSet attrs) {
        //手势处理
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (mOnItemClickListener != null) {
                    if (mAdapter instanceof LoopPagerAdapter) {
                        mOnItemClickListener.onItemClick(mViewPager.getCurrentItem() % ((LoopPagerAdapter) mAdapter).getRealCount());
                    } else {
                        mOnItemClickListener.onItemClick(mViewPager.getCurrentItem());
                    }
                }
                return super.onSingleTapUp(e);
            }
        });
    }

    private final static class TimeTaskHandler extends Handler {
        private WeakReference<RollTimerPagerView> mRollPagerViewWeakReference;

        public TimeTaskHandler(RollTimerPagerView rollPagerView) {
            this.mRollPagerViewWeakReference = new WeakReference<>(rollPagerView);
        }

        @Override
        public void handleMessage(Message msg) {
            RollTimerPagerView rollPagerView = mRollPagerViewWeakReference.get();
            int cur = rollPagerView.getViewPager().getCurrentItem() + 1;
            if (cur >= rollPagerView.mAdapter.getCount()) {
                cur = 0;
            }
            rollPagerView.getViewPager().setCurrentItem(cur);
            rollPagerView.mHintViewDelegate.setCurrentPosition(cur, (HintView) rollPagerView.mHintView);
            if (rollPagerView.mAdapter.getCount() <= 1) rollPagerView.stopPlay();

        }
    }

    private TimeTaskHandler mHandler = new TimeTaskHandler(this);

    private static class WeakTimerTask extends TimerTask {
        private WeakReference<RollTimerPagerView> mRollPagerViewWeakReference;

        public WeakTimerTask(RollTimerPagerView mRollPagerView) {
            this.mRollPagerViewWeakReference = new WeakReference<>(mRollPagerView);
        }

        @Override
        public void run() {
            RollTimerPagerView rollPagerView = mRollPagerViewWeakReference.get();
            if (rollPagerView != null) {
                if (rollPagerView.isShown() && System.currentTimeMillis() - rollPagerView.mRecentTouchTime > rollPagerView.delay) {
                    rollPagerView.mHandler.sendEmptyMessage(0);
                }
            } else {
                cancel();
            }
        }
    }

    /**
     * 开始播放
     * 仅当view正在显示 且 触摸等待时间过后 播放
     */
    public void startPlay() {
        if (delay <= 0 || mAdapter == null || mAdapter.getCount() <= 1) {
            return;
        }
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        //用一个timer定时设置当前项为下一项
        timer.schedule(new WeakTimerTask(this), delay, delay);
    }

    private void stopPlay() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void setHintViewDelegate(HintViewDelegate delegate) {
        this.mHintViewDelegate = delegate;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * 为了实现触摸时和过后一定时间内不滑动,这里拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mRecentTouchTime = System.currentTimeMillis();
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        mHintViewDelegate.setCurrentPosition(arg0, (HintView) mHintView);
    }

}
