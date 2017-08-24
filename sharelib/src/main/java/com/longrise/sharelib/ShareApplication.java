package com.longrise.sharelib;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by Administrator on 2017-8-23.
 */

public class ShareApplication extends Application {

    //各个平台的分享配置，建议放在全局Application或者程序入口
    {
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx854b0d531e67d094", "fbddb098a228bd752aee54236d3f1a5a");
        //QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1105798446", "VMjaHACQOYVwaPtr");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
    }
}
