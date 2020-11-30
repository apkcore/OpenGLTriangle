package com.apkcore.opengltriangle.java;

import android.opengl.GLES30;
import android.util.Log;

public class ShaderUtils {
    private static final String TAG = "ShaderUtils";

    public static int loadShader(int shaderType, String shaderSource) {
        int shader = GLES30.glCreateShader(shaderType);
        if (shader != 0) {
            GLES30.glShaderSource(shader, shaderSource);
            GLES30.glCompileShader(shader);
        }
        return shader;
    }

    //https://learnopengl-cn.github.io/01%20Getting%20started/04%20Hello%20Triangle/#_3
    //对了，在把着色器对象链接到程序对象以后，记得删除着色器对象，我们不再需要它们了：
    //glDeleteShader(vertexShader);
    //glDeleteShader(fragmentShader);
    public static int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexSource);
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentSource);
        int program = GLES30.glCreateProgram();
        GLES30.glAttachShader(program, vertexShader);
        GLES30.glAttachShader(program, fragmentShader);
        GLES30.glDeleteShader(vertexShader);
        GLES30.glDeleteShader(fragmentShader);
        GLES30.glLinkProgram(program);
        return program;
    }
}
