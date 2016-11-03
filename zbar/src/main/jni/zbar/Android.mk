# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


LOCAL_PATH := $(call my-dir)
#APP_PATH:= $(NDK)/$(call my-dir) 

include $(CLEAR_VARS) 

LOCAL_MODULE    := zbarjni

LOCAL_C_INCLUDES := $(LOCAL_PATH)/../include/

LOCAL_CFLAGS := -I$(LOCAL_PATH) -I$(LOCAL_PATH)/$(LIBICONV)
LOCAL_LDLIBS := -llog

LOCAL_STATIC_LIBRARIES := libiconv

LOCAL_SRC_FILES :=	convert.c \
					decoder.c \
					error.c \
					image.c \
					img_scanner.c \
					refcnt.c \
					scanner.c \
					symbol.c \
					video.c \
					window.c \
					qrcode/bch15_5.c \
					qrcode/binarize.c \
					qrcode/isaac.c \
					qrcode/qrdec.c \
					qrcode/qrdectxt.c \
					qrcode/rs.c \
					qrcode/util.c \
					processor/null.c \
					video/null.c \
					window/null.c \
					config.c \
					zbarjni.c
#decoder/qr_finder.c \
#decoder/code128.c \
#decoder/code39.c \
#decoder/code93.c \
#decoder/codabar.c \
#decoder/ean.c \
#decoder/databar.c \
#decoder/i25.c \

#ENABLE_EAN := true
#ENABLE_CODE39 := true
#ENABLE_I25 := true
#ENABLE_CODE128 := true
ENABLE_QRCODE := true
#ENABLE_CODABAR := true
#ENABLE_CODE93 := true
#ENABLE_DATABAR := true

ifdef ENABLE_EAN
LOCAL_CFLAGS += -DENABLE_EAN
LOCAL_SRC_FILES += decoder/ean.c
endif

ifdef ENABLE_CODE39
LOCAL_CFLAGS += -DENABLE_CODE39
LOCAL_SRC_FILES += decoder/code39.c
endif

ifdef ENABLE_I25
LOCAL_CFLAGS += -DENABLE_I25
LOCAL_SRC_FILES += decoder/i25.c
endif

ifdef ENABLE_CODE128
LOCAL_CFLAGS += -DENABLE_CODE128
LOCAL_SRC_FILES += decoder/code128.c
endif

ifdef ENABLE_CODABAR
LOCAL_CFLAGS += -DENABLE_CODABAR
LOCAL_SRC_FILES += decoder/codabar.c
endif

ifdef ENABLE_CODE93
LOCAL_CFLAGS += -DENABLE_CODE93
LOCAL_SRC_FILES += decoder/code93.c
endif

ifdef ENABLE_DATABAR
LOCAL_CFLAGS += -DENABLE_DATABAR
LOCAL_SRC_FILES += decoder/databar.c

endif

ifdef ENABLE_QRCODE
LOCAL_CFLAGS += -DENABLE_QRCODE
LOCAL_SRC_FILES += decoder/qr_finder.c
endif

$(warning $(LOCAL_CFLAGS))

include $(BUILD_SHARED_LIBRARY)

