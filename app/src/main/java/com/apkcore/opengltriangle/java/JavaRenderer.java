package com.apkcore.opengltriangle.java;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class JavaRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "JavaRenderer";
    private Context mContext;
    private int program;
    private int vPosition;

    public JavaRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        String vertexShaderSource = "#version 300 es\n" +
                "layout(location = 0) in vec4 vPosition;\n" +
                "\n" +
                "void main() {\n" +
                "    gl_Position = vPosition;\n" +
                "}";
        String fragmentShaderSource = "#version 300 es\n" +
                "\n" +
                "precision mediump float;\n" +
                "out vec4 fragColor;\n" +
                "\n" +
                "void main() {\n" +
                "    fragColor = vec4 ( 1.0, 0.0, 0.0, 1.0 );\n" +
                "}";
        program = ShaderUtils.createProgram(vertexShaderSource, fragmentShaderSource);
        if (program == GLES30.GL_NONE) {
            Log.e(TAG, "gl init faild!");
        }
        vPosition = GLES30.glGetAttribLocation(program, "vPosition");
        Log.d(TAG, "vPosition:" + vPosition);
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // 背景颜色设置为黑色 RGBA (range: 0.0 ~ 1.0)
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.d(TAG, "JAVA ------- onDrawFrame: ");
        int vertexCount = 3;
        // OpenGL的世界坐标系是 [-1, -1, 1, 1]
        float[] vertices = {
                0.0f, 0.5f, 0.0f, // 第一个点（x, y, z）
                -0.5f, -0.5f, 0.0f, // 第二个点（x, y, z）
                0.5f, -0.5f, 0.0f // 第三个点（x, y, z）
        };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4); // 一个 float 是四个字节
        vbb.order(ByteOrder.nativeOrder()); // 必须要是 native order
        FloatBuffer vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0); // 这一行不要漏了

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT); // clear color buffer
        // 1. 选择使用的程序
        GLES30.glUseProgram(program);
        // 2. 加载顶点数据
        GLES30.glVertexAttribPointer(vPosition, vertexCount, GLES30.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES30.glEnableVertexAttribArray(vPosition);
        // 3. 绘制
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);
    }
}
