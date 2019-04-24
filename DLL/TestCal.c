#include "TestCal.h"
#include <stdio.h>
#include <jni.h>

JNIEXPORT jint JNICALL Java_TestCal_add
  (JNIEnv *a, jobject b , jint c, jint d)
{
	jint res;
	res = c + d;
	return res;
}

JNIEXPORT jint JNICALL Java_TestCal_sub
  (JNIEnv *a, jobject b, jint c, jint d)
{
	jint res;
	res = c - d;
	return res;
}


