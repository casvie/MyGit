package com.longrise.sharelib.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.longrise.sharelib.model.ShareBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by Administrator on 2017-8-23.
 */

public class ShareHelper {

    private Activity activity;

    private UMShareListener listener;

    public ShareHelper(Activity activity) {
        this.activity = activity;
    }

    public void setListener(UMShareListener listener) {
        this.listener = listener;
    }

    //分享到某项应用中
    public void share(ShareBean bean, SHARE_MEDIA var1) {
        ShareAction action = new ShareAction(activity);
        action.setPlatform(var1);
        action.withMedia(getWebData(bean));//附带的图片，音乐，视频等多媒体对象
        action.setCallback(listener);//设置友盟分享后状态监听
        action.share();//发起分享，调起微信，QQ，微博客户端进行分享
    }

    //分享到默认应用列表中
    public void open(ShareBean bean) {
        open(bean, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE);
    }

    public void open(ShareBean bean, SHARE_MEDIA... list) {
        ShareAction action = new ShareAction(activity);
        action.setDisplayList(list);
        action.withMedia(getWebData(bean));
        action.setCallback(listener);
        action.open();
    }

    private UMWeb getWebData(ShareBean bean) {
        UMWeb web = new UMWeb(bean.getUrl());
        web.setTitle(bean.getTitle());//标题
        web.setDescription(bean.getDesc());//描述
        String img = bean.getImage();
        UMImage thumb = null;
        if (TextUtils.isEmpty(img)) {
            thumb = new UMImage(activity, bean.getThumb());
        } else {
            thumb = new UMImage(activity, img);
        }
        web.setThumb(thumb);  //缩略图

        return web;
    }
}
