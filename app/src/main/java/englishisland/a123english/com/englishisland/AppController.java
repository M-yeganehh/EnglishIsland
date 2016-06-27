package englishisland.a123english.com.englishisland;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Mohamad on 6/27/2016.
 */
public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("BYekan.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}