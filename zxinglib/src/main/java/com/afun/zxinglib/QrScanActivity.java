package com.afun.zxinglib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afun.zxinglib.ScanView.ZXingScannerViewNew;
import com.afun.zxinglib.decoding.InactivityTimer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;


public class QrScanActivity extends Activity implements ZXingScannerViewNew.ResultHandler, ZXingScannerViewNew.QrSize, View.OnClickListener {

    private ZXingScannerViewNew scanView;//扫码类
    public static final int REQUEST_IMAGE = 112;//选择系统图片Request Code
    public static boolean isOpen = false;//闪光灯
    private InactivityTimer inactivityTimer;//定时关闭扫码页面类
    private BeepAndSoundUtil beepAndSoundUtil;//声音震动辅助类
    private ImageView iv_light_icon;

    private RelativeLayout bottom_content;
    private View bottomView;
    private View bottom_content_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanView = new ZXingScannerViewNew(this);
        scanView.setContentView(R.layout.logistics_scan_qr);
        scanView.setQrSize(this);
        setContentView(scanView);
        setupFormats();
        initUI();

        inactivityTimer = new InactivityTimer(this);
        beepAndSoundUtil = new BeepAndSoundUtil(this);
    }

    private void initUI() {
        findViewById(R.id.open_photos).setOnClickListener(this);
        findViewById(R.id.flash).setOnClickListener(this);
        findViewById(R.id.scan_write).setOnClickListener(this);
        findViewById(R.id.top_back).setOnClickListener(this);

        iv_light_icon = (ImageView) findViewById(R.id.iv_light_icon);
        bottom_content = (RelativeLayout) findViewById(R.id.bottom_content);
        bottom_content_info = findViewById(R.id.bottom_content_info);
    }

    /***
     * 设置底部自定义内容
     *
     * @param resId
     *            资源文件ID
     */
    public void setBottomLayout(int resId) {
        if (resId != 0) {
            bottom_content_info.setVisibility(View.GONE);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            try {
                bottomView = inflater.inflate(resId, null);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                bottomView.setLayoutParams(layoutParams);
                bottomView.setBackgroundDrawable(null);
                if (null != bottom_content) {
                    bottom_content.addView(bottomView);
                }
            } catch (Exception e) {
                Toast.makeText(this, "未发现自定义布局", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanView.setResultHandler(this);
        scanView.startCamera(-1);
        scanView.setFlash(false);
        scanView.setAutoFocus(true);
        //初始声音震动
        beepAndSoundUtil.initResume();
    }

    @Override
    public void handleResult(Result rawResult) {
        inactivityTimer.onActivity();
        beepAndSoundUtil.playBeepSoundAndVibrate();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scanView.stopCamera();
    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);
        if (scanView != null) {
            scanView.setFormats(formats);
        }
    }

    @Override
    public Rect getDetectRect() {
        View view = findViewById(R.id.scan_window);
        int top = ((View) view.getParent()).getTop() + view.getTop();
        int left = view.getLeft();
        int width = view.getWidth();
        int height = view.getHeight();
        Rect rect = null;
        if (width != 0 && height != 0) {
            rect = new Rect(left, top, left + width, top + height);
            addLineAnim(rect);
        }
        return rect;
    }

    private void addLineAnim(Rect rect) {
        ImageView imageView = (ImageView) findViewById(R.id.scanner_line);
        imageView.setVisibility(View.VISIBLE);
        if (imageView.getAnimation() == null) {
            TranslateAnimation anim = new TranslateAnimation(0, 0, 0, rect.height());
            anim.setDuration(3000);
            anim.setRepeatCount(Animation.INFINITE);
            imageView.startAnimation(anim);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.open_photos) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE);
        } else if (i == R.id.flash) {
            if (!isOpen) {
                scanView.setFlash(true);
                isOpen = true;
                iv_light_icon.setImageResource(R.drawable.ic_light_open);
            } else {
                scanView.setFlash(false);
                isOpen = false;
                iv_light_icon.setImageResource(R.drawable.ic_light_close);
            }

        } else if (i == R.id.scan_write) {
            inputWrite();
        } else if (i == R.id.top_back) {
            this.finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        inactivityTimer.shutdown();
    }

    public void inputWrite() {

    }
}
