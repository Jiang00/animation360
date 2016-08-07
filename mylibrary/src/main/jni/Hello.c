#include "com_example_administrator_mylibrary_NdkString.h"

JNIEXPORT jstring JNICALL Java_com_example_administrator_mylibrary_NdkString_getFromC
        (JNIEnv * env, jclass jclass){

    return (*env) ->NewStringUTF(env,"From C");
}