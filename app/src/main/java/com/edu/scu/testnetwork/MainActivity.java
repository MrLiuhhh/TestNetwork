package com.edu.scu.testnetwork;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button1;//发起请求
    private Button button2;//丢包率
    private Button button3;//时延
    private Button button4;//抖动率
    private TextView info;//信息区
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    info.setText("使用代理IP连接网络成功");
                    break;
                case 1:
                    info.setText("使用代理IP连接网络失败");
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        button1= (Button) findViewById(R.id.request);
        button2= (Button) findViewById(R.id.lost);
        button3= (Button) findViewById(R.id.delay_time);
        button4= (Button) findViewById(R.id.shake);
        info= (TextView) findViewById(R.id.area);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //发起请求
            case R.id.request:
               final Message msg =new Message();
                //设置使用代理
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("101.200.89.170",8080));
                        try {
                            HttpURLConnection connection = (HttpURLConnection) new URL("http://www.baidu.com/").openConnection();
                            connection.setConnectTimeout(6000);
                            connection.setReadTimeout(6000);
                            connection.setUseCaches(false);
                            Log.d("网络请求码－－－－－－", String.valueOf(connection.getResponseCode()));

                            if (connection.getResponseCode() == 200) {
                                msg.what=0;
                                handler.sendMessage(msg);
                            } else {
                                msg.what=1;
                                handler.sendMessage(msg);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            //
            case R.id.lost:
                break;
            //
            case R.id.delay_time:
                break;
            //
            case R.id.shake:
                break;
            default:
                break;
        }
    }
}
