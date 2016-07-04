package englishisland.a123english.com.englishisland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
      /*  new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Splash.this.finish();
            }
        }, 5000);*/
        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
