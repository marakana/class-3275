LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Fib
LOCAL_SRC_FILES := Fib.cpp

include $(BUILD_SHARED_LIBRARY)
