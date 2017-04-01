/**
 * 
 */
package com.hb.lovebaby.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * @author ×óÃ÷ºé
 *
 * 2013-8-26
 */
public class RefreshLoadScrollView extends ListView implements OnScrollListener{
	
	private float mLastY = -1; // save event y
	private Scroller mScroller; // used for scroll back
	private OnScrollListener mScrollListener; // user's scroll listener

	// the interface to trigger refresh and load more.
	private RefreshLoadScrollViewListener mListViewListener;


	// -- footer view
	private RefreshLoadScrollViewFooter mFooterView;
	private boolean mEnablePullLoad;
	private boolean mPullLoading;
	private boolean mIsFooterReady = false;
	
	// total list items, used to detect is at the bottom of listview.
	private int mTotalItemCount;

	// for mScroller, scroll back from header or footer.
	private int mScrollBack;
	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;

	private final static int SCROLL_DURATION = 400; // scroll back duration
	private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
														// at bottom, trigger
														// load more.
	private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
													// feature.

	private LinearLayout main_head;
	
	private int main_head_height;
	//×îÐ¡Ë¢ÐÂ¸ß¶È
	private int minRefreshHeight = 60;
	//µÈ´ýË¢ÐÂ
	private boolean waitRefresh;
	//ÕýÔÚË¢ÐÂ
	private boolean refreshing;
	//ÄÜ·ñÔÙÀ­Ë¢ÐÂ
	private boolean pullRefresh = true;
	//ÄÜ·ñ¼ÓÔØ¸ü¶à
	private boolean pullLoadMore = true;
	//ÊÇ·ñÈ«²¿¼ÓÔØÍê³É
	private boolean loadFinish = false;
	
	/**
	 * @param context
	 */
	public RefreshLoadScrollView(Context context) {
		super(context);
		initWithContext(context);
	}

	public RefreshLoadScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	public RefreshLoadScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}

	private void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		// XListView need the scroll event, and it will dispatch the event to
		// user's listener (as a proxy).
		super.setOnScrollListener(this);


		// init footer view
		mFooterView = new RefreshLoadScrollViewFooter(context);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		// make sure XListViewFooter is the last footer view, and only add once.
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}
	
	public void loadFinish(boolean flag){
		loadFinish = flag;
		if(loadFinish){
			mFooterView.hide();
		}else{
			mFooterView.show();
		}
	}

	

	/**
	 * enable or disable pull up load more feature.
	 * 
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.show();
			mFooterView.setState(RefreshLoadScrollViewFooter.STATE_NORMAL);
			// both "pull up" and "click" will invoke load more.
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	/**
	 * stop refresh, reset header view.
	 */
	public void stopRefresh() {
		waitRefresh = false;
		refreshing = false;
		pullRefresh = true;
	}

	/**
	 * stop load more, reset footer view.
	 */
	public void stopLoadMore() {
		pullLoadMore = true;
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(RefreshLoadScrollViewFooter.STATE_NORMAL);
		}
	}


	private void invokeOnScrolling() {
		if (mScrollListener instanceof OnXScrollListener) {
			OnXScrollListener l = (OnXScrollListener) mScrollListener;
			l.onXScrolling(this);
		}
	}

	private void updateHeaderHeight(float delta) {
		//´ïµ½×îÐ¡Ë¢ÐÂ¸ß¶È ¿ÉÒÔË¢ÐÂ ²¢ÇÒ²»ÔÙË¢ÐÂ×´Ì¬
		if(!refreshing && !waitRefresh && pullRefresh){
			if(main_head.getHeight()-main_head_height>=minRefreshHeight){
				waitRefresh = true;
			}
		}
		int hg = (int) (delta) + main_head.getHeight();
		if(hg>main_head_height+minRefreshHeight){
			hg=main_head_height+minRefreshHeight;
		}
        ViewGroup.LayoutParams lp = main_head.getLayoutParams();
		lp.height = hg;
		main_head.setLayoutParams(lp);
		setSelection(0); // scroll to top each time
	}

	/**
	 * reset header view's height.
	 */
	private void resetHeaderHeight() {
		ViewGroup.LayoutParams lp =  main_head.getLayoutParams();
		lp.height = main_head_height;
		main_head.setLayoutParams(lp);
		invalidate();
	}

	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
													// more.
				mFooterView.setState(RefreshLoadScrollViewFooter.STATE_READY);
			} else {
//				mFooterView.setState(RefreshLoadScrollViewHeader.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);

//		setSelection(mTotalItemCount - 1); // scroll to bottom
	}

	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
					SCROLL_DURATION);
			invalidate();
		}
	}

	private void startLoadMore() {
		if(pullLoadMore==false){
			return;
		}
		mPullLoading = true;
		pullLoadMore = false;
		mFooterView.setState(RefreshLoadScrollViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}
	
	public void startRefresh(){
		if (mListViewListener != null) {
			mListViewListener.onRefresh();
		}
		resetHeaderHeight();
	}
	
	@SuppressLint("NewApi") @Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			if (getFirstVisiblePosition() == 0
					&& ( deltaY > 0)) {
				// the first item is showing, header has shown or pull down.
				updateHeaderHeight(deltaY / OFFSET_RADIO);
				invokeOnScrolling();
                main_head.setScaleY(1.02f);
			} else if (getLastVisiblePosition() == mTotalItemCount - 1
					&& (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
				// last item, already pulled up or want to pull up.
				updateFooterHeight(-deltaY / OFFSET_RADIO);
				if(pullLoadMore && !loadFinish){
					setPullLoadEnable(true);
				}
			}
			break;
		default:
            main_head.setScaleY(1.0f);
			mLastY = -1; // reset
			if (getFirstVisiblePosition() == 0) {
				// invoke refresh
				if(waitRefresh && mListViewListener != null && pullRefresh){
					pullRefresh = false;
					mListViewListener.onRefresh();
				}
				resetHeaderHeight();
				
			} else if (getLastVisiblePosition() == mTotalItemCount - 1) {
				// invoke load more.
				if (mEnablePullLoad
						&& mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
					startLoadMore();
				}
				resetFooterHeight();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	public LinearLayout getMain_head() {
		return main_head;
	}

	public void setMain_head(LinearLayout main_head) {
		this.main_head = main_head;
	}

	public int getMain_head_height() {
		return main_head_height;
	}

	public void setMain_head_height(int main_head_height) {
		this.main_head_height = main_head_height;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
//				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} else {
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			invokeOnScrolling();
		}
		super.computeScroll();
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// send to user's listener
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
	}

	public void setRefreshLoadScrollViewListener(RefreshLoadScrollViewListener l) {
		mListViewListener = l;
	}

	/**
	 * you can listen ListView.OnScrollListener or this one. it will invoke
	 * onXScrolling when header/footer scroll back.
	 */
	public interface OnXScrollListener extends OnScrollListener {
		public void onXScrolling(View view);
	}

	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface RefreshLoadScrollViewListener {
		public void onRefresh();

		public void onLoadMore();
	}
}
