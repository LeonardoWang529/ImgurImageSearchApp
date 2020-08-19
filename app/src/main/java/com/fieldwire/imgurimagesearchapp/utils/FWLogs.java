package com.fieldwire.imgurimagesearchapp.utils;

import android.util.Log;

import com.fieldwire.imgurimagesearchapp.BuildConfig;


public class FWLogs {
    public static void v(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.v(tag,msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr){
        if(BuildConfig.DEBUG){
            Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr){
        if(BuildConfig.DEBUG){
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr){
        if(BuildConfig.DEBUG){
            Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr){
        if(BuildConfig.DEBUG){
            Log.w(tag, msg, tr);
        }
    }

    public static void w(String tag, Throwable tr){
        if(BuildConfig.DEBUG){
            Log.w(tag, tr);
        }
    }

    public static void e(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr){
        if(BuildConfig.DEBUG){
            Log.e(tag, msg, tr);
        }
    }

    public static void wtf(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.wtf(tag, msg);
        }
    }

    public static void wtf(String tag, String msg, Throwable tr){
        if(BuildConfig.DEBUG){
            Log.wtf(tag, msg, tr);
        }
    }

    public static void wtf(String tag, Throwable tr){
        if(BuildConfig.DEBUG){
            Log.wtf(tag, tr);
        }
    }
}
