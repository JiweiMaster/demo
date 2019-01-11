package com.nrec.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.hjq.bar.OnTitleBarListener
import com.nrec.commonwidget.FastClickFilter
import com.nrec.commonwidget.LoadingDialog
import com.nrec.commonwidget.SuccessOrFailedDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val success = SuccessOrFailedDialog(this,false)

        titleBar.onTitleBarListener = object : OnTitleBarListener{
            override fun onLeftClick(v: View?) {

            }

            override fun onRightClick(v: View?) {
                success.show()
            }

            override fun onTitleClick(v: View?) {

            }
        }
        val netUtil = NetworkUtil(this)

        doubleClick.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                when(netUtil.networkType){
                    NetworkUtil.NetworkType.NETWORK_WIFI -> {
                        Log.e("jiwei","wifi")
                    }
                    NetworkUtil.NetworkType.NETWORK_2G -> {
                        Log.e("jiwei","2G")

                    }

                    NetworkUtil.NetworkType.NETWORK_3G -> {
                        Log.e("jiwei","3G")

                    }

                    NetworkUtil.NetworkType.NETWORK_4G -> {
                        Log.e("jiwei","4G")

                    }
                }
            }
        })





//        val netWorkManager = NetWorkManager()
//        netWorkManager.setOnSignalStrengthChangeListener(object : NetWorkManager.OnSignalStrengthChangeListener{
//            override fun onSignalStengthChange(signalNum: Int) {
//                signalStrengthTextView.text = signalStrengthTextView.text.toString() + "\n"+ signalNum
//            }
//        })
//        netWorkManager.getNowSignalStrength(this)



//        doubleClick.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//                if(FastClickFilter.filter()){
//                    Log.e("Test","hahah")
//                }
//            }
//        })


//        AlertView.Builder().setContext(this).setStyle(AlertView.Style.ActionSheet)
//                .setTitle("是否更新")
//                .setMessage(null)
//                .setCancelText("取消")
//                .setDestructive("拍照", "从相册中选择")
//                .setOthers(null)
//                .setOnItemClickListener(object : OnItemClickListener{
//                    override fun onItemClick(o: Any?, position: Int) {
//
//                    }
//                })
//                .build()
//                .show();

    }
}
