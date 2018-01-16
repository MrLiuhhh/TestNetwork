package com.edu.scu.testnetwork;

import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
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
import java.util.Properties;

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
                        Properties pop = System.getProperties();
                        String proxyHost ="124.193.33.233";
                        String proxyPort="3128";
                        pop.put("proxySet","true");
                        pop.put("proxyHost",proxyHost);
                        pop.put("proxyPort",proxyPort);
                        HttpURLConnection connection=null;
                        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("124.193.33.233",3128));
                        try {
                            connection = (HttpURLConnection) new URL("http://www.baidu.com/").openConnection();
                            connection.setRequestMethod("GET");
                            connection.setInstanceFollowRedirects(false);
                            connection.setConnectTimeout(10000);
                            connection.setReadTimeout(10000);
                            connection.setUseCaches(false);
                            connection.connect();
                            Log.d("信息－－－－－－", String.valueOf(connection.getContent()));
                            Log.d("地址", connection.getHeaderField("Location"));
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
                        }finally {
                            if (connection !=null){
                                connection.disconnect();
                            }
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
/*
public static void main(String[] args) {
    	try {
    		StringBuffer buffer = new StringBuffer();

            String url = "http://localhost:8080/istock/login?u=name&p=pass";
            System.out.println("访问地址:" + url);

            //发送get请求
            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setRequestMethod("GET");
            //必须设置false，否则会自动redirect到重定向后的地址
            conn.setInstanceFollowRedirects(false);
            conn.addRequestProperty("Accept-Charset", "UTF-8;");
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
            conn.addRequestProperty("Referer", "http://matols.com/");
            conn.connect();

            //判定是否会进行302重定向
            if (conn.getResponseCode() == 302) {
                //如果会重定向，保存302重定向地址，以及Cookies,然后重新发送请求(模拟请求)
                String location = conn.getHeaderField("Location");
                String cookies = conn.getHeaderField("Set-Cookie");

	            serverUrl = new URL(location);
	            conn = (HttpURLConnection) serverUrl.openConnection();
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Cookie", cookies);
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");
	            conn.addRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
	            conn.addRequestProperty("Referer", "http://matols.com/");
	            conn.connect();
	            System.out.println("跳转地址:" + location);
            }

            //将返回的输入流转换成字符串
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;

            System.out.println(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 */
