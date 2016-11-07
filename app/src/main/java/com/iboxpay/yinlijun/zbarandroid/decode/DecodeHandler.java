package com.iboxpay.yinlijun.zbarandroid.decode;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.iboxpay.yinlijun.zbarandroid.CaptureActivity;
import com.iboxpay.yinlijun.zbarandroid.R;
import com.iboxpay.yinlijun.zbarandroid.bitmap.PlanarYUVLuminanceSource;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Size;

import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;

/**
 * 作者: 陈涛(1076559197@qq.com)
 * 
 * 时间: 2014年5月9日 下午12:24:13
 * 
 * 版本: V_1.0.0
 * 
 * 描述: 接受消息后解码
 */
final class DecodeHandler extends Handler {

	CaptureActivity activity = null;
	private ImageScanner mScanner;

	DecodeHandler(CaptureActivity activity) {
		this.activity = activity;
		mScanner = new ImageScanner();
		mScanner.setConfig(0, Config.X_DENSITY, 1);
		mScanner.setConfig(0, Config.Y_DENSITY, 1);
		mScanner.setConfig(0, Config.ENABLE, 0);
		mScanner.setConfig(Symbol.QRCODE, Config.ENABLE, 1);
	}

	@Override
	public void handleMessage(Message message) {
		switch (message.what) {
		case R.id.decode:
			decode((byte[]) message.obj, message.arg1, message.arg2);
			break;
		case R.id.quit:
			Looper.myLooper().quit();
			break;
		}
	}

	public String GetResultByCode(int CodeType)
	{
		String mResult="";
		switch(CodeType)
		{
			//条形码
			case Symbol.CODABAR:
				mResult="条形码";
				break;
			//128编码格式二维码)
			case Symbol.CODE128:
				mResult="二维码";
				break;
			//QR码二维码
			case Symbol.QRCODE:
				mResult="二维码";
				break;
			//ISBN10图书查询
			case Symbol.ISBN10:
				mResult="图书ISBN号";
				break;
			//ISBN13图书查询
			case Symbol.ISBN13:
				mResult="图书ISBN号";
				break;
		}
		return mResult;
	}

	private void decode(byte[] data, int width, int height) {
		Image image = new Image(width, height, "Y800");
		image.setData(data);
        image.convert("Y800");
		image.setCrop(activity.getY(), activity.getX(), activity.getCropHeight(), activity.getCropWidth());
		int resultCode = mScanner.scanImage(image);
		StringBuilder sb = new StringBuilder();
		if (resultCode != 0)
		{
			SymbolSet Syms = mScanner.getResults();
			for (Symbol mSym : Syms) {
				sb.append("扫描类型:");
				sb.append(GetResultByCode(mSym.getType()));
				sb.append("\n");
				sb.append(mSym.getData());
				sb.append("\n");
			}

			if (null != activity.getHandler()) {
				Message msg = new Message();
				msg.obj = sb.toString();
				msg.what = R.id.decode_succeeded;
				activity.getHandler().sendMessage(msg);
			}
		} else {
			if (null != activity.getHandler()) {
				activity.getHandler().sendEmptyMessage(R.id.decode_failed);
			}
		}
	}
}