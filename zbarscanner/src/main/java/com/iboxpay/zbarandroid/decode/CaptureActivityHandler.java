package com.iboxpay.zbarandroid.decode;

import android.os.Handler;
import android.os.Message;

import com.iboxpay.zbarandroid.CaptureActivity;
import com.iboxpay.zbarandroid.R;
import com.iboxpay.zbarandroid.camera.CameraManager;

import java.util.ArrayList;
import java.util.Map;

/**
 */
public final class CaptureActivityHandler extends Handler {

	DecodeThread decodeThread = null;
	CaptureActivity activity = null;
	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(CaptureActivity activity) {
		this.activity = activity;
		decodeThread = new DecodeThread(activity);
		decodeThread.start();
		state = State.SUCCESS;
		CameraManager.get().startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {
		int what = message.what;
		if(what == R.id.auto_focus) {
			if (state == State.PREVIEW) {
				CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
			}
		} else if(what == R.id.restart_preview) {
			restartPreviewAndDecode();
		} else if(what == R.id.decode_succeeded) {
			state = State.SUCCESS;
			activity.handleDecode((String) message.obj);// 解析成功，回调
		} else if(what == R.id.decode_failed) {
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					R.id.decode);
		}
	}

	public void quitSynchronously() {
		state = State.DONE;
		CameraManager.get().stopPreview();
		removeMessages(R.id.decode_succeeded);
		removeMessages(R.id.decode_failed);
		removeMessages(R.id.decode);
		removeMessages(R.id.auto_focus);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					R.id.decode);
			CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
		}
	}

}
