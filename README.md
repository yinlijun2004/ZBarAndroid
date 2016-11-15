This project contains two modules: app, zbarscanner.

app is a module introduce to how to use module zbarscanner.
zbarscanner is a module contains zbar source code.

Usage:

- import zbarscanner in build.gradle dependencies
```
    compile 'com.terminald:zbarscanner:1.0.5'
```

- Start activity with code types you want to support.
```java
        ArrayList<Integer> symbols = new ArrayList<Integer>();
        //supported code type
        symbols.add(Symbol.QRCODE);
        symbols.add(Symbol.EAN13);
        
        //way 1
        //declare activity in AndroidManifest.xml first
        //Intent intent = new Intent(MainActivity.this, CaptureActivity.class);

        //way 2
        //Intent intent = new Intent("com.iboxpay.zbarandroid.scan");
        //这里的packetname 一定记住填写 掉用方的packetname 因为 aar最终会被合并到调用方的manifest文件
        //intent.setComponent(new ComponentName("com.iboxpay.zbarsample", "com.iboxpay.zbarandroid.CaptureActivity"));

        //way 3
        Intent intent = new Intent();
        intent.setClassName(Main2Activity.class.getPackage().getName(), CaptureActivity.class.getCanonicalName());

        intent.putExtra("symbols", symbols);
        intent.putExtra("start", new Date().getTime());
        startActivityForResult(intent, REQUEST_CODE_SCAN);
```

- Get scan result in activity onActivityResult callback.
```java
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Main2Activity.REQUEST_CODE_SCAN  && resultCode == RESULT_OK) {
            String result = data.getStringExtra("result");
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "scan failed", Toast.LENGTH_SHORT).show();
        }
    }
 ```