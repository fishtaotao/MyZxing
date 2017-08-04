package tao.tao.com.myzxing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()) {
            case R.id.btn_1:
                mIntent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btn_2:
                mIntent = new Intent(MainActivity.this, DiyScanActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
