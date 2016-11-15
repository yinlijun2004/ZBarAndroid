This project contains two modules: app, zbarscanner.

app is a module introduce to how to use module zbarscanner.
zbarscanner is a module contains zbar source code.

Usage:

- import zbarscanner in build.gradle dependencies
```
    compile 'com.terminald:zbarscanner:1.0.2'
```

- Start activity with code types you want to support.
```java
        ArrayList<Integer> symbols = new ArrayList<Integer>();
        //supported code type
        symbols.add(Symbol.QRCODE);
        symbols.add(Symbol.EAN13);
        Intent intent = new Intent();
        intent.setClassName("com.iboxpay.zbarandroid", "com.iboxpay.zbarandroid.CaptureActivity");
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