package com.demo.test.Pdf;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.support.annotation.MainThread;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.demo.test.MyApplication;
import com.demo.test.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDFMainActivity extends AppCompatActivity {
    private ViewPager mPdfVp;
    private LayoutInflater mInflater;
    private ParcelFileDescriptor mDescriptor;
    private PdfRenderer mRenderer;
    public static final String FILE_NAME = "alibaba.pdf";
    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfmain);
        mInflater = LayoutInflater.from(this);
        myApplication = (MyApplication) getApplication();

        mPdfVp = (ViewPager) findViewById(R.id.pdf_vp);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    openRender();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    /**
     * 打开pdf
     */
    private void openRender() throws IOException {

        File file = new File(getExternalCacheDir(), FILE_NAME);

        //        if (!file.exists()) {
        URL url = new URL("http://192.168.1.227/alibaba.pdf");
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        //        InputStreamReader asset=new InputStreamReader(urlCon.getInputStream());
        //复制文件到本地存储
        //            InputStream asset = getAssets().open(FILE_NAME);
        InputStream asset = urlCon.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];

        int size;
        while ((size = asset.read(buffer)) != -1) {
            fos.write(buffer, 0, size);
        }

        asset.close();
        fos.close();
        //        }

        //初始化PdfRender
        mDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);

        if (mDescriptor != null) {
            mRenderer = new PdfRenderer(mDescriptor);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //初始化ViewPager的适配器并绑定
                MyAdapter adapter = new MyAdapter();
                mPdfVp.setAdapter(adapter);
            }
        });


    }

    @Override
    protected void onDestroy() {
        //销毁页面的时候释放资源,习惯
        try {
            closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void closeRenderer() throws IOException {
        mRenderer.close();
        mDescriptor.close();

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mRenderer.getPageCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mInflater.inflate(R.layout.item_pdf, null);

            ImageView pvPdf = view.findViewById(R.id.iv_pdf);
            pvPdf.setEnabled(true);

            if (getCount() <= position) {
                return view;
            }

            PdfRenderer.Page currentPage = mRenderer.openPage(position);

            int pdfWH[] = getPDF_WH(currentPage.getWidth(), currentPage.getHeight(), myApplication.getWindowWidth(), myApplication.getWindowHeight());
            Bitmap bitmap = Bitmap.createBitmap(pdfWH[0], pdfWH[1], Bitmap.Config.ARGB_8888);
            currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_PRINT);
            pvPdf.setImageBitmap(bitmap);
            //关闭当前Page对象
            currentPage.close();

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //销毁需要销毁的视图
            container.removeView((View) object);
        }
    }

    private int[] getPDF_WH(int bitmapW, int bitmapH, int screenW, int screenH) {
        int[] WH = new int[2];
        if (bitmapW == 0 || bitmapH == 0 || screenW == 0 || screenH == 0) {
            return WH;
        }
        float bitmapP = bitmapW * 1.0f / bitmapH;
        float screenP = screenW * 1.0f / screenH;

        if (bitmapP < screenP) {//原图像长，定高不定宽
            WH[1] = screenH;
            /** x/图片高度=原宽度/原高度
             *  x=bitmapP*图片高度
             *  **/
            WH[0] = (int) (bitmapP * screenH);
        } else if (bitmapP > screenP) {//原图像宽，顶宽不定高
            WH[0] = screenW;
            /** 图片宽度/x=原宽度/原高度
             *  x=图片宽度/bitmapP
             *  **/
            WH[1] = (int) (screenW / bitmapP);

        } else {
            WH[0] = screenW;
            /** 图片宽度/x=原宽度/原高度
             *  x=图片宽度/bitmapP
             *  **/
            WH[1] = screenH;
        }


        return WH;

    }
}
