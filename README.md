
目前只支持QRCODE扫码，如需要支持其他扫码，可以在zbar/src/main/jni/zbar/Android.mk中修改如下配置，
```
#ENABLE_EAN := true
#ENABLE_CODE39 := true
#ENABLE_I25 := true
#ENABLE_CODE128 := true
ENABLE_QRCODE := true
#ENABLE_CODABAR := true
#ENABLE_CODE93 := true
#ENABLE_DATABAR := true
```