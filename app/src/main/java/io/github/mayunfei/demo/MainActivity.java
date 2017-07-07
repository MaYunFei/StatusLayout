package io.github.mayunfei.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import io.github.mayunfei.statuslayout.StatusLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    StatusLayout mStatusLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusLayout = (StatusLayout) findViewById(R.id.statusLayout);
        if (!NetworkUtils.isConnected(this)){
            mStatusLayout.showNetWorkError();
        }
        findViewById(R.id.loading).setOnClickListener(this);
        findViewById(R.id.empty).setOnClickListener(this);
        findViewById(R.id.neterror).setOnClickListener(this);
        findViewById(R.id.error).setOnClickListener(this);
        findViewById(R.id.content).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading:
                mStatusLayout.showLoading();
                break;
            case R.id.empty:
                mStatusLayout.showEmpty();
                break;
            case R.id.neterror:
                mStatusLayout.showNetWorkError();
                break;
            case R.id.error:
                mStatusLayout.showError();
                break;
            case R.id.content:
                mStatusLayout.showContent();
                break;
        }
    }
}
