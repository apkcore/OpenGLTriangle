#include <jni.h>
#include <string>
#include "GLES3/gl3.h"
#include "log/ApkcoreLog.h"
#include "shader/ShaderUtils.h"

AAssetManager *g_pAssetManager = NULL;
GLint vPosition;
GLint program;
extern "C"
JNIEXPORT void JNICALL
Java_com_apkcore_opengltriangle_natives_NativeOperate_glInit(JNIEnv *env, jobject thiz) {

    char *vertexShaderSource = readAssetFile("vertex.vsh", g_pAssetManager);
    char *fragmentShaderSource = readAssetFile("fragment.fsh", g_pAssetManager);
    program = createProgram(vertexShaderSource, fragmentShaderSource);
    if (program == GL_NONE) {
        LOGE("gl init faild!");
    }
    vPosition = glGetAttribLocation(program, "vPosition");
    LOGD("vPosition: %d", vPosition);
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // 背景颜色设置为黑色 RGBA (range: 0.0 ~ 1.0)

}extern "C"
JNIEXPORT void JNICALL
Java_com_apkcore_opengltriangle_natives_NativeOperate_glResize(
        JNIEnv *env, jobject thiz,
        jint width,
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
}extern "C"
JNIEXPORT void JNICALL
Java_com_apkcore_opengltriangle_natives_NativeOperate_registerAssetManager(
        JNIEnv *env,
        jobject thiz,
        jobject asset_manager) {
    if (asset_manager) {
        g_pAssetManager = AAssetManager_fromJava(env, asset_manager);
    } else {
        LOGE("assetManager is null!")
    }
}