package com.example.content.utility;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageCache {
    private LruCache<String, Bitmap> memoryCache;
    private File cacheDir;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private Handler handler = new Handler(Looper.getMainLooper());

    public ImageCache(Context context) {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
        cacheDir = context.getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    public void loadBitmap(final String url, final ImageLoadCallback callback) {
        final String key = hashKeyForDisk(url);
        final Bitmap bitmapFromMemory = memoryCache.get(key);
        if (bitmapFromMemory != null) {
            callback.onBitmapLoaded(bitmapFromMemory);
        } else {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmapFromFile = loadBitmapFromFile(url, key);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onBitmapLoaded(bitmapFromFile);
                        }
                    });
                }
            });
        }
    }

    private Bitmap loadBitmapFromFile(String url, String key) {
        final File file = new File(cacheDir, key);
        Bitmap bitmapFromFile = null;
        if (file.exists()) {
            bitmapFromFile = BitmapFactory.decodeFile(file.getAbsolutePath());
        } else {
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmapFromFile = BitmapFactory.decodeStream(inputStream);
                if (bitmapFromFile != null) {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmapFromFile.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmapFromFile;
    }

    private String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public interface ImageLoadCallback {
        void onBitmapLoaded(Bitmap bitmap);
    }
}