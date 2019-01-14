package com.nrec.webcontain;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import net.lingala.zip4j.core.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 18099 on 2019/1/11.
 */

public class WebContainerActivity extends AppCompatActivity {
    private String TAG = "WebContainerActivity";
    public String HostUrl = "https://app.nrec.com/EngineerAccountApp/index.zip";
    WebView contentWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webviewcontainer);
        ImageView returnImageView = findViewById(R.id.returnImageView);
        contentWebView = findViewById(R.id.webview);
        returnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
        initWebView(contentWebView);
    }




    private void initData() {
        //服务器获取数据
        DownloadApi downloadApi = DownloadRetrofitFactory.getInstance()
                .initRetrofit("https://app.nrec.com").create(DownloadApi.class);
        Call<ResponseBody> call = downloadApi.downloadFile("/EngineerAccountApp/index.zip");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    //文件写入磁盘
                    writeResponseBodyToDisk(response.body());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("",t.getMessage());
            }
        });
        //初始化文件下载监听器
        mOnFileDownloadCompleteListener = new OnFileDownloadCompleteListener() {
            @Override
            public void onFileDownloadComplete(String filePath) {
                Log.e(TAG,"文件下载成功=>"+filePath);
//                String url = "file://"+filePath;
                String destFile = unZip(filePath);
                if(!destFile.equals("")){
                    contentWebView.loadUrl("file://"+destFile+"/index.html");
                }

//                Log.e(TAG,""+url);
            }

            @Override
            public void onFileDownloadFailed() {
                Log.e(TAG,"文件下载失败");
            }
        };
    }
    //解压文件
    private String unZip(String sourFile) {
        try{
            String destFile = getCacheDir().getAbsolutePath();
            Log.e(TAG,""+destFile);
            ZipFile zipFile =  new ZipFile(sourFile);
            zipFile.extractAll(destFile);
            return destFile;
        }catch(Exception e){
            Log.e(TAG,"文件解压失败=>"+e.toString());
            return  null;
        }
    }

    private void initWebView(WebView webView) {
        //在应用内部加载网页
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //允许webview对文件的操作
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);

        //自适应屏幕的分辨率
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //设置缓存
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        if(Build.VERSION.SDK_INT >= 19){
            webSettings.setCacheMode(webSettings.LOAD_DEFAULT);
        }
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(this.getCacheDir().getAbsolutePath());

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setFitsSystemWindows(true);
        webView.setBackgroundColor(0);
        webView.setLayerType(View.LAYER_TYPE_NONE, null);

    }
    //文件下载监听器
    OnFileDownloadCompleteListener mOnFileDownloadCompleteListener;
    public interface OnFileDownloadCompleteListener{
        void onFileDownloadComplete(String filePath);
        void onFileDownloadFailed();
    }

    public void setOnFileDownloadCompleteListener(OnFileDownloadCompleteListener onFileDownloadCompleteListener){
        this.mOnFileDownloadCompleteListener = onFileDownloadCompleteListener;
    }


    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File dir = this.getCacheDir();
            //使用包名作为文件的命名
            File downloadFile = new File(dir.getAbsolutePath() +"/index.zip");
            if(downloadFile.exists()){
                downloadFile.delete();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(downloadFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                if(mOnFileDownloadCompleteListener != null){
                    mOnFileDownloadCompleteListener.onFileDownloadComplete(downloadFile.getAbsolutePath());
                }
                return true;
            } catch (IOException e) {
                Log.e(TAG,""+e.toString());
                mOnFileDownloadCompleteListener.onFileDownloadFailed();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            mOnFileDownloadCompleteListener.onFileDownloadFailed();
            Log.e(TAG,""+e.toString());
            return false;
        }
    }




    @Override
    protected void onDestroy() {
        if(contentWebView != null){
            ViewParent parent = contentWebView.getParent();
            if(parent != null){
                ((ViewGroup) parent).removeView(contentWebView);
            }
            contentWebView.stopLoading();
            contentWebView.getSettings().setJavaScriptEnabled(false);
            contentWebView.clearHistory();
            contentWebView.removeAllViews();
            contentWebView.destroy();
        }
        super.onDestroy();
    }
}
