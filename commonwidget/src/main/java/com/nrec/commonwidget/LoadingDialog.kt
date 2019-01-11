package com.nrec.commonwidget

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.opengl.Visibility
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find

/**
 * message:dialog显示出的提示文字，""不显示
 * Created by 18099 on 2019/1/10.
 */

class LoadingDialog constructor(context: Context, message: String){
    private lateinit var mDialog: Dialog
    private lateinit var animDrawable: AnimationDrawable

    init{
        val dialogBuilder = AlertDialog.Builder(context,R.style.LoadingDialog)
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.layout_progress_dialog,null)
        val loadingImageView = view.find<ImageView>(R.id.img_loading)
        animDrawable = loadingImageView.background as AnimationDrawable

        val loadingMsgTextView = view.find<TextView>(R.id.mesg_loading)
        if(message.equals("")){
            loadingMsgTextView.visibility = View.GONE
        }else{
            loadingMsgTextView.visibility = View.VISIBLE
            loadingMsgTextView.setText(message)
        }
        dialogBuilder.setCancelable(false)
        dialogBuilder.setView(view)
        mDialog = dialogBuilder.create()
    }

    fun showLoading(){
        mDialog.show()
        animDrawable?.start()
    }

    fun hideLoading(){
        mDialog.dismiss()
        animDrawable.stop()
    }
}
