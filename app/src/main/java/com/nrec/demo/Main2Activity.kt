package com.nrec.demo

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import com.nrec.commonwidget.NotificationUtil
import kotlinx.android.synthetic.main.activity_main2.*
import net.lingala.zip4j.core.ZipFile

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
//        解压文件
//        val cacheDir:String = cacheDir.absolutePath
//        val zipFile = ZipFile("/data/data/com.nrec.demo/cache/index.zip")
//        zipFile.extractAll("/data/data/com.nrec.demo/cache")
//        Log.e("Test",cacheDir)
//        设置通知栏
        val notificationUtil = NotificationUtil.Builder(this)
                .setNotificationText("hahahha")
                .setNotificationTitle("Jiwei").build()

        btn1.setOnClickListener({
            notificationUtil.show()
        })

//        notificationUtil.show()
    }
}
