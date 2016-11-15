package com.iboxpay.zbarsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iboxpay.zbarandroid.CaptureActivity;

import java.util.ArrayList;
import java.util.Date;

import net.sourceforge.zbar.Symbol;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_SCAN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.activity_main2).setOnClickListener(this);
    }

    private void startCaptureActivity() {
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
    }

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

    @Override
    public void onClick(View view) {
        startCaptureActivity();
    }
}
