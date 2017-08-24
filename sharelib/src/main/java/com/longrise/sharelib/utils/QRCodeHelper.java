package com.longrise.sharelib.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/13.
 */
public class QRCodeHelper {

    //生成二维码
    public Bitmap createQRImage(String contents, int WidthAndHeight, Bitmap logo) {
        int wh = 160;
        Hashtable hints = null;
        BitMatrix bitMatrix = null;
        try {
            if (contents == null || "".equals(contents)) {
                return null;
            }
            if (WidthAndHeight > 0) {
                wh = WidthAndHeight;
            }
            hints = new Hashtable();
            if (hints != null) {
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                //设置空白边距的宽度
                hints.put(EncodeHintType.MARGIN, Integer.valueOf(0));
                //容错级别
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
                // 图像数据转换
//                bitMatrix = this.encode(contents, BarcodeFormat.QR_CODE, wh, wh, hints);
                bitMatrix = new QRCodeWriter().encode(contents, BarcodeFormat.QR_CODE, wh, wh, hints);
                if (bitMatrix != null) {
                    Bitmap bitmap = toBitmap(bitMatrix, wh);
                    if (null != logo) {
                        bitmap = addLogo(bitmap, logo);
                    }
                    return bitmap;
                }
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            hints = null;
            contents = null;
            bitMatrix = null;
        }
        return null;
    }

    //在二维码中间增加logo
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        Bitmap bitmap = null;
        try {
            if (null == src) {
                return null;
            }
            if (null == logo) {
                return src;
            }
            int srcW = src.getWidth();
            int srcH = src.getHeight();
            int logoW = logo.getWidth();
            int logoH = logo.getHeight();
            if (srcW == 0 || srcH == 0) {
                return null;
            }
            if (logoW == 0 || logoH == 0) {
                return src;
            }
            //logo大小为二维码大小的1/5
            float scale = srcW * 1.0f / 5 / logoW;

            bitmap = Bitmap.createBitmap(srcW, srcH, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scale, scale, srcW / 2, srcH / 2);
            canvas.drawBitmap(logo, (srcW - logoW) / 2, (srcH - logoH) / 2, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap toBitmap(BitMatrix matrix, int whPix) {
        Bitmap image = null;
        try {
            if (matrix == null) {
                return null;
            } else {
                int[] pixels = new int[whPix * whPix];
                for (int y = 0; y < whPix; y++) {
                    for (int x = 0; x < whPix; x++) {
                        if (matrix.get(x, y)) {
                            pixels[y * whPix + x] = 0xff000000;
                        } else {
                            pixels[y * whPix + x] = 0xffffffff;
                        }
                    }
                }
                image = Bitmap.createBitmap(whPix, whPix, Bitmap.Config.ARGB_8888);
                image.setPixels(pixels, 0, whPix, 0, 0, whPix, whPix);

                return image;
            }
        } catch (Exception var10) {
            return null;
        } finally {
            image = null;
        }
    }

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        Object writer = null;

        try {
            if (contents != null && !"".equals(contents)) {
                switch (format.ordinal()) {
                    case 2:
                        writer = new CodaBarWriter();
                        break;
                    case 3:
                        writer = new Code39Writer();
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 10:
                    case 13:
                    case 14:
                    default:
                        writer = new QRCodeWriter();
                        break;
                    case 5:
                        writer = new Code128Writer();
                        break;
                    case 7:
                        writer = new EAN8Writer();
                        break;
                    case 8:
                        writer = new EAN13Writer();
                        break;
                    case 11:
                        writer = new PDF417Writer();
                        break;
                    case 12:
                        writer = new QRCodeWriter();
                        break;
                    case 15:
                        writer = new UPCAWriter();
                }
                if (writer == null) {
                    return null;
                }
                BitMatrix var9 = ((Writer) writer).encode(contents, format, width, height, hints);
                return var9;
            }
        } catch (Exception var12) {
            return null;
        } finally {
            writer = null;
        }
        return null;
    }
}

