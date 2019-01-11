package com.nrec.commonwidget

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.opengl.Visibility
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find

/**
 * isSuccess决定显示成功还是失败的dialog
 * Created by 18099 on 2019/1/10.
 */

class SuccessOrFailedDialog constructor(context: Context, isSuccess: Boolean){
    private var mDialog: Dialog

    init{
        val dialogBuilder = AlertDialog.Builder(context,R.style.LoadingDialog)
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.layout_success_failed_dialog,null)
        val loadingImageView: ImageView = view.find<ImageView>(R.id.img_loading)
        val loadingTextView = view.find<TextView>(R.id.mesg_loading)
        if(isSuccess){
            loadingImageView.setImageResource(R.drawable.icon_success_indictor)
            loadingTextView.setText("成功")
        }else{
            loadingImageView.setImageResource(R.drawable.icon_fail_indictor)
            loadingTextView.setText("失败")

        }
        dialogBuilder.setCancelable(false)
        dialogBuilder.setView(view)
        mDialog = dialogBuilder.create()
    }

    fun show(){
        mDialog.show()
        Handler().postDelayed({
            hide();
        },1000);
    }

    private fun hide(){
        mDialog.dismiss()
    }
}
