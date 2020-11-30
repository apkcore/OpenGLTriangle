package com.apkcore.opengltriangle.natives;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class NativeRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "NativeRenderer";

    private Context mContext;
    private NativeOperate nativeOperate;

    public NativeRenderer(Context mContext) {
        this.mContext = mContext;
        nativeOperate = new NativeOperate();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        nativeOperate.registerAssetManager(mContext.getAssets());
        nativeOperate.glInit();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        nativeOperate.glResize(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.d(TAG, "Native ------- onDrawFrame: ");
        nativeOperate.glDraw();
    }
}
