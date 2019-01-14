package com.nrec.commonwidget;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by 18099 on 2019/1/14.
 */

public class NotificationUtil {
    private static NotificationManager mNotificationManager;
    private static Notification.Builder mBuilder;
    private static Context parentContext;
    //该应用里面的独一无二的id
    //如果该值不变系统会一直覆盖上次的通知
    //变化的值会产生新的通知
    private int notificationId = 1;

    protected NotificationUtil(){

    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public static class Builder{
        private Context mContext;
        private String notificationId = "default_notification";
        private String notificationName = "default_name";
        private String notificationDescription = "default_description";
        private boolean isVibrated = false;
        private boolean isLighted = false;
        private int notificationImage = R.drawable.icon_notification;
        private String notificationTitle = "default_title";
        private String notificationText = "default_text";

        //设置相关的属性
        public Builder setNotificationId(String notificationId) {
            this.notificationId = notificationId;
            return this;
        }

        public Builder setNotificationName(String notificationName) {
            this.notificationName = notificationName;
            return this;
        }

        public Builder setNotificationDescription(String notificationDescription) {
            this.notificationDescription = notificationDescription;
            return this;
        }

        public Builder setVibrated(boolean vibrated) {
            isVibrated = vibrated;
            return this;
        }

        public Builder setLighted(boolean lighted) {
            isLighted = lighted;
            return this;
        }

        public Builder setNotificationImage(int notificationImage) {
            this.notificationImage = notificationImage;
            return this;
        }

        public Builder setNotificationTitle(String notificationTitle) {
            this.notificationTitle = notificationTitle;
            return this;
        }

        public Builder setNotificationText(String notificationText) {
            this.notificationText = notificationText;
            return this;
        }

        public Builder(Context context){
            this.mContext = context;
            mNotificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        /**
         * 使用建造者模式，可以动态设置Builder的属性
         * @return
         */
        public NotificationUtil build(){
            //设置channel
            NotificationChannel mChannel;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                //用于Android8.0的系统，Android8.0一下的系统使用其他方法进行操作
                mChannel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_HIGH);
                mChannel.setDescription(notificationDescription);
                mChannel.enableVibration(isVibrated);
                mChannel.enableLights(isLighted);
                //声音暂时不做设置
                mChannel.setSound(null,null);
                mNotificationManager.createNotificationChannel(mChannel);
                //暂时没有设置进度条
                mBuilder = new Notification.Builder(mContext)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationText)
                        .setSmallIcon(notificationImage)
                        .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),
                                R.drawable.icon_notification))
                        .setAutoCancel(false)
                        .setOnlyAlertOnce(true)
                        .setChannelId(notificationId);
            }else{
                //8.0以下版本手机的notification的开启的办法
                mBuilder =new Notification.Builder(mContext)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationText)
                        .setSmallIcon(notificationImage)
                        .setAutoCancel(false)
                        .setOnlyAlertOnce(true);
            }
            return new NotificationUtil();
        }
    }

    /**
     * 继承初始化的通知
     */
    public void show(){
        //notification确定了显示的id
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            if(mNotificationManager != null){
                //设置一个该应用里面独一无二的id
                mNotificationManager.notify(notificationId,mBuilder.build());
            }
        }else{
            mNotificationManager.notify(notificationId,mBuilder.build());
        }
    }

    /**
     * 显示新的通知：传入与原来id不同的Notification id
     * @param newNotificationId
     */
    public void show(int newNotificationId){
        //显示新的通知通知
        //notification确定了显示的id
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            if(mNotificationManager != null){
                //设置一个该应用里面独一无二的id
                mNotificationManager.notify(newNotificationId,mBuilder.build());
            }
        }else{
            if(mNotificationManager != null){
                mNotificationManager.notify(newNotificationId,mBuilder.build());
            }
        }
    }

}
