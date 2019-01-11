package com.nrec.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import net.lingala.zip4j.core.ZipFile

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val cacheDir:String = cacheDir.absolutePath
        val zipFile = ZipFile("/data/data/com.nrec.demo/cache/index.zip")
        zipFile.extractAll("/data/data/com.nrec.demo/cache")

        Log.e("Test",cacheDir)
    }
}
