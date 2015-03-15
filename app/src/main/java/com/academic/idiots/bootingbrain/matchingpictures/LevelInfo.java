package com.academic.idiots.bootingbrain.matchingpictures;

/**
 * Created by HuyThien on 3/15/2015.
 * this class store level info
 */
public class LevelInfo {
    public static LevelInfo[] LEVEL_INFOS ={new LevelInfo(2,2),new LevelInfo(4,4),new LevelInfo(8,8)};
    int mWidthSize;
    int mHeightSize;
    int matchingCount = 0;
    public void inscreaseMatChing()
    {
        matchingCount++;
    };

    public boolean isFinished(){
        if (mWidthSize*mHeightSize/2 == matchingCount){
            return true;
        }
        return false;
    }

    public LevelInfo() {
        mWidthSize = 1;
        mHeightSize = 1;
    }

    public LevelInfo(int widthSize, int heightSize) {
        this.mWidthSize = widthSize;
        this.mHeightSize = heightSize;
    }

    public int getWidthSize() {
        return mWidthSize;
    }

    public void setWidthSize(int WidthSize) {
        this.mWidthSize = WidthSize;
    }

    public int getHeightSize() {
        return mHeightSize;
    }

    public void setHeightSize(int heightSize) {
        this.mHeightSize = mHeightSize;
    }
}
