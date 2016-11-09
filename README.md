
目前只支持QRCODE扫码，如需要支持其他扫码，可以在app/src/main/java/com/iboxpay/yinlijun/zbarandroid/decode/DecodeHandler.java中修改:
```
		mScanner.setConfig(0, Config.ENABLE, 0);
		mScanner.setConfig(Symbol.QRCODE, Config.ENABLE, 1);
```
修改为：
```
		mScanner.setConfig(0, Config.ENABLE, 1);
```