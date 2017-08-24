package com.longrise.sharelib;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.longrise.sharelib.adapter.ShareAdapter;
import com.longrise.sharelib.model.ShareBean;
import com.longrise.sharelib.model.TypeBean;
import com.longrise.sharelib.utils.PermissionHelper;
import com.longrise.sharelib.utils.QRCodeHelper;
import com.longrise.sharelib.utils.ShareHelper;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-8-23.
 */

public class ShareActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView imageView;

    private GridView gridView;

    private ShareAdapter mAdapter;

    private List<TypeBean> shareLists;

    private ProgressDialog umDialog;

    private Dialog noticeDialog;

    private ShareBean shareBean;//分享的数据

    private ShareHelper shareHelper;

    private QRCodeHelper qrCodeHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umeng_activity_share);

        initViews();
        initValues();
    }

    private void initViews() {
        findViewById(R.id.umeng_ll_back).setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.umeng_iv_qrcode);
        gridView = (GridView) findViewById(R.id.umeng_gv_sharetype);
        mAdapter = new ShareAdapter(this);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);
    }

    private void initValues() {
        shareBean = (ShareBean) getIntent().getSerializableExtra("data");
        shareHelper = new ShareHelper(this);
        shareHelper.setListener(umShareListener);

        getShareData();
        mAdapter.setDataLists(shareLists);

        int qrcode = shareBean.getQrCode();
        if (qrcode > 1) {//直接设置二维码图片
            imageView.setBackgroundResource(qrcode);
        } else {//生成二维码图片
            showImage(shareBean.getUrl(), shareBean.getThumb());
        }
    }

    //生成二维码
    private void showImage(String url, int thumb) {
        try {
//            byte[] bys = str.getBytes("utf-8");//utf-8编码
//            if (null != bys && 0 < bys.length) {
//                for (int i = 0; i < bys.length; i++) {
//                    bys[i] = (byte) (bys[i] ^ 1);//异或加密
//                }
//                str = new String(bys, "ISO-8859-1");//ISO-8859-1编码
//            }
            if (null == qrCodeHelper) {
                qrCodeHelper = new QRCodeHelper();
            }
            Bitmap logo = BitmapFactory.decodeResource(getResources(), thumb);
            imageView.setImageBitmap(qrCodeHelper.createQRImage(url, 300, logo));//参数1：二维码字串；参数2：二维码尺寸
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qrCodeHelper = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.umeng_ll_back) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!PermissionHelper.checkPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return;
        }
        TypeBean bean = shareLists.get(position);
        shareHelper.share(shareBean, bean.getVar1());
    }

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onStart(SHARE_MEDIA platform) {
            if (umDialog == null) {
                umDialog = new ProgressDialog(ShareActivity.this);
            }
            SocializeUtils.safeShowDialog(umDialog);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(ShareActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ShareActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            SocializeUtils.safeCloseDialog(umDialog);
            Toast.makeText(ShareActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            UiUtil.showToast(context, platform + " 分享取消啦");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        SocializeUtils.safeCloseDialog(umDialog);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();

        if (null != shareLists) {
            shareLists.clear();
            shareLists = null;
        }
        if (null != gridView) {
            gridView.setAdapter(null);
            gridView.setOnItemClickListener(null);
            gridView = null;
        }
        umShareListener = null;
    }

    //权限回调处理
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 233) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意授权

            } else {//用户不同意
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {//被拒绝

                } else {//勾选不再提醒
                    if (TextUtils.equals(permissions[0], Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        showPermissionDialog();
                    }
                }
            }
        }
    }

    private void showPermissionDialog() {
        if (noticeDialog == null) {
            noticeDialog = new Dialog(this, R.style.umeng_AlertDialogStyle);
            View mView = LayoutInflater.from(this).inflate(R.layout.umeng_dialog_prompt, null);
            TextView infoTV = (TextView) mView.findViewById(R.id.umeng_dialog_tv_info);
            infoTV.setText(R.string.umeng_socialize_storgestate);
            mView.findViewById(R.id.umeng_dialog_tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noticeDialog.dismiss();
                }
            });
            mView.findViewById(R.id.umeng_dialog_tv_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                }
            });
            noticeDialog.setContentView(mView);
            //设置大小
            Window dialogWindow = noticeDialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = this.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
            lp.width = (int) (d.widthPixels * 0.85);
            dialogWindow.setAttributes(lp);
        }
        noticeDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //获取分享的类型
    private void getShareData() {
        if (shareLists == null) {
            shareLists = new ArrayList<>();
        } else {
            shareLists.clear();
        }
        TypeBean wxfBean = new TypeBean();
        wxfBean.setVar1(SHARE_MEDIA.WEIXIN_CIRCLE);
        wxfBean.setInfo("微信朋友圈");
        wxfBean.setRedid(R.mipmap.umeng_icon_wxf);
        shareLists.add(wxfBean);
        wxfBean = null;

        TypeBean wxBean = new TypeBean();
        wxBean.setVar1(SHARE_MEDIA.WEIXIN);
        wxBean.setInfo("微信");
        wxBean.setRedid(R.mipmap.umeng_icon_wx);
        shareLists.add(wxBean);
        wxBean = null;

        TypeBean qqsBean = new TypeBean();
        qqsBean.setVar1(SHARE_MEDIA.QZONE);
        qqsBean.setInfo("QQ空间");
        qqsBean.setRedid(R.mipmap.umeng_icon_qqs);
        shareLists.add(qqsBean);
        qqsBean = null;

        TypeBean qqBean = new TypeBean();
        qqBean.setVar1(SHARE_MEDIA.QQ);
        qqBean.setInfo("QQ好友");
        qqBean.setRedid(R.mipmap.umeng_icon_qq);
        shareLists.add(qqBean);
        qqBean = null;
    }
}
