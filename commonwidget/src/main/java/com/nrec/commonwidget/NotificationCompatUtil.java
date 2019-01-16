package com.nrec.commonwidget;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * Created by 18099 on 2019/1/14.
 */

public class NotificationCompatUtil {
    private static NotificationManager mNotificationManager;
    private static NotificationCompat.Builder mBuilder;
    //该应用里面的独一无二的id
    //如果该值不变系统会一直覆盖上次的通知
    //变化的值会产生新的通知
    private int notificationId = 1;

    protected NotificationCompatUtil(){

    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public static class Builder{
        private Context mContext;
        private String notificationIdStr = "default_notification";
        private int notificationImage = R.drawable.icon_notification;
        private String notificationTitle = "default_title";
        private String notificationText = "default_text";

        private boolean isAutoCancel = true;

        //设置相关的属性
        public Builder setNotificationIdStr(String notificationIdStr) {
            this.notificationIdStr = notificationIdStr;
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

        public Builder setAutoCancel(boolean autoCancel) {
            isAutoCancel = autoCancel;
            return this;
        }


        public NotificationCompatUtil build(){
            //低于8.0的版本，系统会有默认实现的NotificationChannel
            mBuilder = new NotificationCompat.Builder(mContext,
                    notificationIdStr)
                    .setWhen(System.currentTimeMillis())
                    .setContentText(notificationText)
                    .setContentTitle(notificationTitle)
                    .setSmallIcon(R.drawable.icon_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),
                        notificationImage))
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setFullScreenIntent(null,true)
                    .setAutoCancel(isAutoCancel);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new
                        NotificationChannel(notificationIdStr,
                        notificationIdStr, NotificationManager.IMPORTANCE_HIGH);
                mNotificationManager.createNotificationChannel(channel);
            }

            return new NotificationCompatUtil();
        }
    }

    public void  show(){
        mNotificationManager.notify(notificationId,mBuilder.build());
    }

    public void setOnPendingIntent(PendingIntent pendingIntent){
        mBuilder.setContentIntent(pendingIntent);
    }
}
