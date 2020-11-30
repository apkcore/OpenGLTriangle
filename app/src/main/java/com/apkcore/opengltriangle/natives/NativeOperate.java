package com.apkcore.opengltriangle.natives;

import android.content.res.AssetManager;

public class NativeOperate {
    static {
        System.loadLibrary("native-lib");
    }

    public native void glInit();

    public native void glResize(int width, int height);

    public native void glDraw();

    public native void registerAssetManager(AssetManager assetManager);
}
