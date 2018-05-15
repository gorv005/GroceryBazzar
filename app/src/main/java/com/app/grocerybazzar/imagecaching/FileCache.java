package com.app.grocerybazzar.imagecaching;

/**
 * Created by gaurav.garg on 06-04-2016.
 */


import android.content.Context;
import android.util.Log;

import java.io.File;

public class FileCache {

    private File cacheDir;

    public FileCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            Log.e("DEBUG","MOUNTED");
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "TTImages_cache");
        }
        else {
            Log.e("DEBUG","NOT MOUNTED");
            cacheDir = context.getCacheDir();
        }
        if(!cacheDir.exists()) {
            Log.e("DEBUG","NOT MOUNTED1");
            cacheDir.mkdirs();
        }
    }

    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename= String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }

}