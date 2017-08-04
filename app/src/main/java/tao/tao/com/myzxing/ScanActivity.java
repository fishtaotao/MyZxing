package tao.tao.com.myzxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import com.afun.zxinglib.CodeUtils;
import com.afun.zxinglib.ImageUtil;
import com.afun.zxinglib.QrScanActivity;
import com.google.zxing.Result;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class ScanActivity extends QrScanActivity {

    // 扫描结果处理
    @Override
    public void handleResult(Result rawResult) {
        super.handleResult(rawResult);
        handResult(rawResult.toString());
    }

    // s输入按钮事件
    @Override
    public void inputWrite() {
        super.inputWrite();
        Toast.makeText(ScanActivity.this, "跳转", Toast.LENGTH_LONG).show();
    }

    // 相册扫描结果处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            handResult(result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(ScanActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handResult(String resultString) {
        Toast.makeText(ScanActivity.this, resultString, Toast.LENGTH_LONG).show();
    }
}
