package com.app.grocerybazzar;

import android.app.Application;
import android.content.Context;

import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.FontsOverride;


/**
 * Created by Ady on 9/2/2017.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = this.getApplicationContext();
        FontsOverride.setDefaultFont(this, "DEFAULT", C.FONT);
    /*    FontsOverride.setDefaultFont(this, "MONOSPACE", C.FONT);
        FontsOverride.setDefaultFont(this, "SERIF", C.FONT);
        FontsOverride.setDefaultFont(this, "SANS_SERIF", C.FONT);*/
        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }
}
