package com.apkcore.opengltriangle

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.apkcore.opengltriangle.java.JavaRenderer
import com.apkcore.opengltriangle.natives.NativeRenderer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var glSurfaceView: GLSurfaceView;

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkOpenGLES30()) {
            Log.e(TAG, "不支持openGLes3.0")
        }
        setContentView(R.layout.activity_main)
        glSurfaceView = findViewById(R.id.gl)
        glSurfaceView.setEGLContextClientVersion(3)
        glSurfaceView.setRenderer(getRenderer(0))
        //只渲染一次，当有数据更新时要手动调用渲染,使用glSurfaceView.requestRender()调用刷新
        //RENDERMODE_CONTINUOUSLY自动连续渲染
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        bt.setOnClickListener {
            glSurfaceView.requestRender()
        }
    }

    private fun getRenderer(type: Int = 0): GLSurfaceView.Renderer? {
        return if (type == 0) {
            NativeRenderer(this)
        } else {
            JavaRenderer(this)
        }
    }

    private fun checkOpenGLES30(): Boolean {
        val am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info = am.deviceConfigurationInfo
        return info.reqGlEsVersion >= 0x30000
    }

    override fun onResume() {
        glSurfaceView.onResume()
        super.onResume()
    }

    override fun onPause() {
        glSurfaceView.onPause()
        super.onPause()
    }
}
