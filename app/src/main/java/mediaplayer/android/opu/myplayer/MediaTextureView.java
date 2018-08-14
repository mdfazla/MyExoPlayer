package mediaplayer.android.opu.myplayer;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.TextureView;


public class MediaTextureView extends TextureView {

    private int  mRatioWidth;
    private int mRatioHeight;
    public MediaTextureView(Context context) {
        super(context);
        Point res=Utils.getRes();
        mRatioWidth=res.x;
        mRatioHeight=res.y;

    }

    public MediaTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width =MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width < height * (mRatioWidth / mRatioHeight)) {
                setMeasuredDimension(height * (mRatioWidth / mRatioHeight), height);
            } else {
                setMeasuredDimension(width, width * (mRatioHeight / mRatioWidth));
            }
        }
       //  }


    }
}
