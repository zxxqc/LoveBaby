package com.honeyBaby.ninegrid;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hb.lovebaby.R;

/**
 * Created by Jaeger on 16/2/24.
 *
 * Email: chjie.jaeger@gamil.com
 * GitHub: https://github.com/laobie
 */
public class NineGridImageView<T> extends ViewGroup {

    public final static int STYLE_GRID = 0;     // ????
    public final static int STYLE_FILL = 1;     // ?????

    private int mRowCount;       // ??
    private int mColumnCount;    // ??

    private int mMaxSize;        // ?????
    private int mShowStyle;     // ????
    private int mGap;           // ????
    private int mSingleImgSize; // ????????
    private int mGridSize;   // ????,?????

    private List<ImageView> mImageViewList = new ArrayList<ImageView>();
    private List<T> mImgDataList;

    private NineGridImageViewAdapter<T> mAdapter;
    private ItemImageClickListener<T> mItemImageClickListener;

    public NineGridImageView(Context context) {
        this(context, null);
    }

    public NineGridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NineGridImageView);
        this.mGap = (int) typedArray.getDimension(R.styleable.NineGridImageView_imgGap, 0);
        this.mSingleImgSize = typedArray.getDimensionPixelSize(R.styleable.NineGridImageView_singleImgSize, -1);
        this.mShowStyle = typedArray.getInt(R.styleable.NineGridImageView_showStyle, STYLE_GRID);
        this.mMaxSize = typedArray.getInt(R.styleable.NineGridImageView_maxSize, 9);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height;
        int totalWidth = width - getPaddingLeft() - getPaddingRight();
        if (mImgDataList != null && mImgDataList.size() > 0) {
            if (mImgDataList.size() == 1 && mSingleImgSize != -1) {
                mGridSize = mSingleImgSize > totalWidth ? totalWidth : mSingleImgSize;
            } else {
                mImageViewList.get(0).setScaleType(ImageView.ScaleType.CENTER_CROP);
                mGridSize = (totalWidth - mGap * (mColumnCount - 1)) / mColumnCount;
            }
            height = mGridSize * mRowCount + mGap * (mRowCount - 1) + getPaddingTop() + getPaddingBottom();
            setMeasuredDimension(width, height);
        } else {
            height = width;
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildrenView();
    }

    /**
     * ?? ImageView
     */
    private void layoutChildrenView() {
        if (mImgDataList == null) {
            return;
        }
        int showCount = getNeedShowCount(mImgDataList.size());
        for (int i = 0; i < showCount; i++) {
            ImageView childrenView = (ImageView) getChildAt(i);
            if (mAdapter != null) {
                mAdapter.onDisplayImage(getContext(), childrenView, mImgDataList.get(i));
            }
            int rowNum = i / mColumnCount;
            int columnNum = i % mColumnCount;
            int left = (mGridSize + mGap) * columnNum + getPaddingLeft();
            int top = (mGridSize + mGap) * rowNum + getPaddingTop();
            int right = left + mGridSize;
            int bottom = top + mGridSize;

            childrenView.layout(left, top, right, bottom);
        }
    }

    /**
     * ??????
     *
     * @param lists ??????
     */
    public void setImagesData(List lists) {
        if (lists == null || lists.isEmpty()) {
            this.setVisibility(GONE);
            return;
        } else {
            this.setVisibility(VISIBLE);
        }

        int newShowCount = getNeedShowCount(lists.size());

        int[] gridParam = calculateGridParam(newShowCount, mShowStyle);
        mRowCount = gridParam[0];
        mColumnCount = gridParam[1];
        if (mImgDataList == null) {
            int i = 0;
            while (i < newShowCount) {
                ImageView iv = getImageView(i);
                if (iv == null) {
                    return;
                }
                addView(iv, generateDefaultLayoutParams());
                i++;
            }
        } else {
            int oldShowCount = getNeedShowCount(mImgDataList.size());
            if (oldShowCount > newShowCount) {
                removeViews(newShowCount, oldShowCount - newShowCount);
            } else if (oldShowCount < newShowCount) {
                for (int i = oldShowCount; i < newShowCount; i++) {
                    ImageView iv = getImageView(i);
                    if (iv == null) {
                        return;
                    }
                    addView(iv, generateDefaultLayoutParams());
                }
            }
        }
        mImgDataList = lists;
        requestLayout();
    }

    private int getNeedShowCount(int size) {
        if (mMaxSize > 0 && size > mMaxSize) {
            return mMaxSize;
        } else {
            return size;
        }
    }

    /**
     * ?? ImageView
     * ??? ImageView ???
     *
     * @param position ??
     */
    private ImageView getImageView(final int position) {
        if (position < mImageViewList.size()) {
            return mImageViewList.get(position);
        } else {
            if (mAdapter != null) {
                ImageView imageView = mAdapter.generateImageView(getContext());
                mImageViewList.add(imageView);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.onItemImageClick(getContext(), (ImageView) v, position, mImgDataList);
                        if (mItemImageClickListener != null) {
                            mItemImageClickListener.onItemImageClick(getContext(), (ImageView) v, position, mImgDataList);
                        }
                    }
                });
                return imageView;
            } else {
                Log.e("NineGirdImageView", "Your must set a NineGridImageViewAdapter for NineGirdImageView");
                return null;
            }
        }
    }

    /**
     * ?? ????
     *
     * @param imagesSize ????
     * @param showStyle  ????
     * @return ???? gridParam[0] ???? gridParam[1] ????
     */
    protected static int[] calculateGridParam(int imagesSize, int showStyle) {
        int[] gridParam = new int[2];
        switch (showStyle) {
            case STYLE_FILL:
                if (imagesSize < 3) {
                    gridParam[0] = 1;
                    gridParam[1] = imagesSize;
                } else if (imagesSize <= 4) {
                    gridParam[0] = 2;
                    gridParam[1] = 2;
                } else {
                    gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
                    gridParam[1] = 3;
                }
                break;
            default:
            case STYLE_GRID:
                gridParam[0] = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
                gridParam[1] = 3;
        }
        return gridParam;
    }

    /**
     * ?????
     *
     * @param adapter ???
     */
    public void setAdapter(NineGridImageViewAdapter adapter) {
        mAdapter = adapter;
    }

    /**
     * ??????
     *
     * @param gap ???? px
     */
    public void setGap(int gap) {
        mGap = gap;
    }

    /**
     * ??????
     *
     * @param showStyle ????
     */
    public void setShowStyle(int showStyle) {
        mShowStyle = showStyle;
    }

    /**
     * ??????????????
     *
     * @param singleImgSize ?????????
     */
    public void setSingleImgSize(int singleImgSize) {
        mSingleImgSize = singleImgSize;
    }

    /**
     * ???????
     *
     * @param maxSize ?????
     */
    public void setMaxSize(int maxSize) {
        mMaxSize = maxSize;
    }

    public void setItemImageClickListener(ItemImageClickListener<T> itemImageViewClickListener) {
        mItemImageClickListener = itemImageViewClickListener;
    }
}
