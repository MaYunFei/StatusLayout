package io.github.mayunfei.statuslayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

/**
 * 状态
 * Created by mayunfei on 17-7-7.
 */

public class StatusLayout extends FrameLayout {


    public interface OnRetryListener {
        void onRetry(View view);
    }

    private OnRetryListener retryListener;
    public View mContentView;
    public ViewStub mLoadingView;
    public ViewStub mEmptyView;
    public ViewStub mNetWorkView;
    public ViewStub mErrorView;

    private int mEmptyViewResID; // emptyVIewId
    private int mNetWorkViewResID;
    private int mLoadingViewResID;
    private int mContentViewResID;
    private int mErrorViewResID;
    private int mRetryResID;

    /**
     * 存放布局集合
     */
    private SparseArray<View> layoutSparseArray = new SparseArray();


    public StatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StatusLayout, defStyleAttr, 0);

        try {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.StatusLayout_emptyView) {
                    mEmptyViewResID = typedArray.getResourceId(R.styleable.StatusLayout_emptyView, -1);
                    mEmptyView = new ViewStub(context);
                    mEmptyView.setLayoutResource(mEmptyViewResID);
                } else if (attr == R.styleable.StatusLayout_networkView) {
                    mNetWorkViewResID = typedArray.getResourceId(R.styleable.StatusLayout_networkView, -1);
                    mNetWorkView = new ViewStub(context);
                    mNetWorkView.setLayoutResource(mNetWorkViewResID);
                } else if (attr == R.styleable.StatusLayout_loadingView) {
                    mLoadingViewResID = typedArray.getResourceId(R.styleable.StatusLayout_loadingView, -1);
                    mLoadingView = new ViewStub(context);
                    mLoadingView.setLayoutResource(mLoadingViewResID);
                } else if (attr == R.styleable.StatusLayout_errorView) {
                    mErrorViewResID = typedArray.getResourceId(R.styleable.StatusLayout_errorView, -1);
                    mErrorView = new ViewStub(context);
                    mErrorView.setLayoutResource(mErrorViewResID);
                } else if (attr == R.styleable.StatusLayout_retryId) {
                    mRetryResID = typedArray.getResourceId(R.styleable.StatusLayout_retryId, -1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();

        if (getChildCount() != 1) {
            throw new IllegalStateException("StatusLayout can host only one direct child");
        }
        mContentView = getChildAt(0);
        mContentViewResID = mContentView.getId();
        layoutSparseArray.put(mContentViewResID, mContentView);

        addViewSub(mEmptyView);
        addViewSub(mErrorView);
        addViewSub(mNetWorkView);
        addViewSub(mLoadingView);

    }

    private void addViewSub(ViewStub viewStub) {
        if (viewStub != null)
            addView(viewStub);
    }


    public void showEmpty() {
        showView(mEmptyViewResID, mEmptyView);
    }

    public void showNetWorkError() {
        showView(mNetWorkViewResID, mNetWorkView);
    }

    public void showLoading() {
        showView(mLoadingViewResID, mLoadingView);
    }

    public void showError() {
        showView(mErrorViewResID, mErrorView);
    }

    public void showContent() {
        showView(mContentView);
    }

    public void showView(int viewResId, ViewStub subView) {
        if (viewResId == -1)
            return;

        View view = layoutSparseArray.get(viewResId);
        if (view == null && subView != null) {
            view = subView.inflate();
            layoutSparseArray.put(viewResId, view);
            View retryView = view.findViewById(mRetryResID);
            if (retryView != null && retryListener != null) {
                retryListener.onRetry(view);
            }
        }
        showView(view);
    }


    private void showView(View showView) {
        if (showView == null) {
            return;
        }
        for (int i = 0; i < layoutSparseArray.size(); i++) {
            View view = layoutSparseArray.valueAt(i);
            if (view.equals(showView)) {
                view.setVisibility(VISIBLE);
            } else {
                view.setVisibility(GONE);
            }
        }
    }

    public OnRetryListener getRetryListener() {
        return retryListener;
    }

    public void setRetryListener(OnRetryListener retryListener) {
        this.retryListener = retryListener;
    }
}
