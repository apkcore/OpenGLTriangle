//
// Created by Lenovo on 2020/11/30.
//

#ifndef OPENGLTRIANGLE_SHADERUTILS_H
#define OPENGLTRIANGLE_SHADERUTILS_H

#include <stdlib.h>
#include <android/asset_manager_jni.h>
#include "GLES3/gl3.h"
#include "../log/ApkcoreLog.h"


static GLuint loadShader(GLenum shaderType, const char *shaderSource) {
    int shader = glCreateShader(shaderType);
    if (shader != 0) {
        glShaderSource(shader, 1, &shaderSource, 0);
        glCompileShader(shader);
    }
    return shader;
}

static GLuint createProgram(const char *vertexSource, const char *fragmentSource) {
    int vertexShader = loadShader(GL_VERTEX_SHADER, vertexSource);
    int fragmentShader = loadShader(GL_FRAGMENT_SHADER, fragmentSource);
    int program = glCreateProgram();
    glAttachShader(program, vertexShader);
    glAttachShader(program, fragmentShader);
    glDeleteShader(vertexShader);
    glDeleteShader(fragmentShader);
    glLinkProgram(program);
    return program;
}

#endif //OPENGLTRIANGLE_SHADERUTILS_H
