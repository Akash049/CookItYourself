package com.curl.ciykit.curl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Akash Chandra on 03-02-2017.
 */

public class DownloadServices extends Service{

    private static final String DEBUG_TAG = "DownloaderService";
    private int pageCount;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

}
