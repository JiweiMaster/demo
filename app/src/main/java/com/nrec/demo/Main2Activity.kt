package com.nrec.demo

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.nrec.commonwidget.NotificationCompatUtil
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

        val builder = NotificationCompatUtil.Builder(this)
        val notificationCompatUtil = builder.build()



//        val resultIntent = Intent(this,Main3Activity::class.java)
//        val stackBuilder = TaskStackBuilder.create(this)
//        stackBuilder.addParentStack(Main3Activity::class.java)
//        stackBuilder.addNextIntent(resultIntent)

//        val resultPendingIntent = stackBuilder
//                .getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
//
//        notificationCompatUtil.setOnPendingIntent(resultPendingIntent)

        btn1.setOnClickListener({
            notificationCompatUtil.show()
        })



    }
}
