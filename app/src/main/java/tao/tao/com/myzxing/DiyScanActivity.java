package tao.tao.com.myzxing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.afun.zxinglib.CodeUtils;
import com.afun.zxinglib.QrScanActivity;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class DiyScanActivity extends QrScanActivity {

        private ImageView iv_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBottomLayout(R.layout.bottom);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        findViewById(R.id.set_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap mBitmap = CodeUtils.createImage("666", 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                iv_logo.setImageBitmap(mBitmap);
            }
        });
        findViewById(R.id.set_notimg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap mBitmap = CodeUtils.createImage("666", 400, 400, null);
                iv_logo.setImageBitmap(mBitmap);
            }
        });



    }
}
