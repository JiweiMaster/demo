package com.nrec.demo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by 18099 on 2019/1/11.
 */

/**
 * Created by 18099 on 2019/1/10.
 */

public class NetworkUtil {
    private static String TAG = "NetWorkManager";

    Context context;
    public NetworkUtil(Context context){
        this.context = context;
    }

    public enum NetworkType {
        NETWORK_ETHERNET,
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    public NetworkUtil.NetworkType getNetworkType() {
        NetworkUtil.NetworkType netType = NetworkUtil.NetworkType.NETWORK_NO;
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {
                netType = NetworkUtil.NetworkType.NETWORK_ETHERNET;
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                netType = NetworkUtil.NetworkType.NETWORK_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {

                    case TelephonyManager.NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        netType = NetworkUtil.NetworkType.NETWORK_2G;
                        break;

                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        netType = NetworkUtil.NetworkType.NETWORK_3G;
                        break;

                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        netType = NetworkUtil.NetworkType.NETWORK_4G;
                        break;
                    default:

                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            netType = NetworkUtil.NetworkType.NETWORK_3G;
                        } else {
                            netType = NetworkUtil.NetworkType.NETWORK_UNKNOWN;
                        }
                        break;
                }
            } else {
                netType = NetworkUtil.NetworkType.NETWORK_UNKNOWN;
            }
        }
        return netType;
    }

    public boolean isUseCache(){
        NetworkUtil.NetworkType networkType = getNetworkType();
        if(networkType == NetworkType.NETWORK_WIFI){
            return false;
        }
        if(networkType == NetworkType.NETWORK_2G){
            return true;
        }
        if(networkType == NetworkType.NETWORK_3G){
            return false;
        }
        if(networkType == NetworkType.NETWORK_4G){
            return false;
        }
        if(networkType == NetworkType.NETWORK_UNKNOWN){
            return true;
        }
        if(networkType == NetworkType.NETWORK_NO){
            return true;
        }
        return true;
    }



    public  interface OnSignalStrengthChangeListener{
        void onSignalStengthChange(int signalNum);
    }

    OnSignalStrengthChangeListener mOnSignalStrengthChangeListener;


    public void setOnSignalStrengthChangeListener(OnSignalStrengthChangeListener onSignalStrengthChangeListener) {
        this.mOnSignalStrengthChangeListener = onSignalStrengthChangeListener;
    }

    public void getNowSignalStrength(){
        final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener phoneStateListener = new PhoneStateListener(){
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);
                String signalStrengthStr = signalStrength.toString();
//                Log.e(TAG,signalStrengthStr);
                String[] parts = signalStrengthStr.split(" ");
                int ltedbm = Integer.parseInt(parts[9]);
                int asu = signalStrength.getGsmSignalStrength();
                int dbm = -113+2*asu;

                if(telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE){
                    Log.d(TAG,"当前网络信号强度=>"+ltedbm);
                    if(mOnSignalStrengthChangeListener != null){
                        mOnSignalStrengthChangeListener.onSignalStengthChange(ltedbm);
                    }
                }else if(telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSDPA ||
                        telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSPA ||
                        telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSUPA ||
                        telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS){
                    Log.d(TAG,"当前网络信号强度=>"+dbm);
                    if(mOnSignalStrengthChangeListener!= null){
                        mOnSignalStrengthChangeListener.onSignalStengthChange(dbm);
                    }
                }else{
                    Log.d(TAG,"当前网络信号强度=>"+dbm);
                    if(mOnSignalStrengthChangeListener!= null){
                        mOnSignalStrengthChangeListener.onSignalStengthChange(dbm);
                    }
                }
            }
        };
        telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }





}

