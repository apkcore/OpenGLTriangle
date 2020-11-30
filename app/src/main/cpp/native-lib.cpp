#include <jni.h>
#include <string>
#include "GLES3/gl3.h"
#include "log/ApkcoreLog.h"
#include "shader/ShaderUtils.h"

GLint vPosition;
GLint program;
extern "C"
JNIEXPORT void JNICALL
Java_com_apkcore_opengltriangle_natives_NativeOperate_glInit(JNIEnv *env, jobject thiz) {

    char *vertexShaderSource = "#version 300 es\n"
                               "\n"
                               "layout(location = 0) in vec4 vPosition;\n"
                               "\n"
                               "void main() {\n"
                               "    gl_Position = vPosition;\n"
                               "}";
    char *fragmentShaderSource = "#version 300 es\n"
                                 "\n"
                                 "precision mediump float;\n"
                                 "out vec4 fragColor;\n"
                                 "\n"
                                 "void main() {\n"
                                 "    fragColor = vec4 ( 1.0, 0.0, 0.0, 1.0 );\n"
                                 "}";
    program = createProgram(vertexShaderSource, fragmentShaderSource);
    if (program == GL_NONE) {
        LOGE("gl init faild!");
    }
    vPosition = glGetAttribLocation(program, "vPosition");
    LOGD("vPosition: %d", vPosition);
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // 背景颜色设置为黑色 RGBA (range: 0.0 ~ 1.0)

}extern "C"
JNIEXPORT void JNICALL
Java_com_apkcore_opengltriangle_natives_NativeOperate_glResize(JNIEnv *env, jobject thiz, jint width,
                                                       jint height) {
    glViewport(0, 0, width, height); // 设置视距窗口

}extern "C"
JNIEXPORT void JNICALL
Java_com_apkcore_opengltriangle_natives_NativeOperate_glDraw(JNIEnv *env, jobject thiz) {

    GLint vertexCount = 3;
    // OpenGL的世界坐标系是 [-1, -1, 1, 1]
    GLfloat vertices[] = {
            0.0f, 0.5f, 0.0f, // 第一个点（x, y, z）
            -0.5f, -0.5f, 0.0f, // 第二个点（x, y, z）
            0.5f, -0.5f, 0.0f // 第三个点（x, y, z）
    };
    glClear(GL_COLOR_BUFFER_BIT); // clear color buffer
    // 1. 选择使用的程序
    glUseProgram(program);
    // 2. 加载顶点数据
    glVertexAttribPointer(vPosition, vertexCount, GL_FLOAT, GL_FALSE, 3 * sizeof(float), vertices);
    glEnableVertexAttribArray(vPosition);
    // 3. 绘制
    glDrawArrays(GL_TRIANGLES, 0, vertexCount);
}