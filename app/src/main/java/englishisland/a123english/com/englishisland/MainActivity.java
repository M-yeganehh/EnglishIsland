package englishisland.a123english.com.englishisland;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import englishisland.a123english.com.englishisland.Adapter.LessonsLVAdapter;
import englishisland.a123english.com.englishisland.TO.Lesson;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Lesson> LessonsList = new ArrayList<>();
//1231231234564567897897889
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String languageToLoad = "fa"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.Title);
        setSupportActionBar(toolbar);


        ListView lvMain = (ListView) findViewById(R.id.LessonsLV);
        ArrayList<Integer> picId = new ArrayList<>();
        picId.add(R.drawable.ic_play_circle_filled_black_24dp);
        picId.add(R.drawable.ic_lock_black_24dp);
        picId.add(R.drawable.ic_lock_black_24dp);
        picId.add(R.drawable.ic_lock_black_24dp);
        picId.add(R.drawable.ic_lock_black_24dp);
        picId.add(R.drawable.ic_lock_black_24dp);
        picId.add(R.drawable.ic_lock_black_24dp);
        picId.add(R.drawable.ic_lock_black_24dp);
        picId.add(R.drawable.ic_lock_black_24dp);
        picId.add(R.drawable.ic_lock_black_24dp);

        ArrayList<String> names = new ArrayList<>();
        names.add(getString(R.string.session1));
        names.add(getString(R.string.session2));
        names.add(getString(R.string.session3));
        names.add(getString(R.string.session4));
        names.add(getString(R.string.session5));
        names.add(getString(R.string.session6));
        names.add(getString(R.string.session7));
        names.add(getString(R.string.session8));
        names.add(getString(R.string.session9));
        names.add(getString(R.string.session10));



        for (int i = 0; i < 10; i++) {
            Lesson lesson = new Lesson();
            lesson.setName(names.get(i));
            lesson.setPic(picId.get(i));
            LessonsList.add(lesson);
        }

        LessonsLVAdapter LVAdapter = new LessonsLVAdapter(getApplicationContext(), LessonsList);
        lvMain.setAdapter(LVAdapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), position+1+"" , Toast.LENGTH_SHORT).show();


            }
        });


      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_intro) {
            // Handle the camera action
        } else if (id == R.id.nav_news) {

        } else if (id == R.id.nav_clips) {

        } else if (id == R.id.nav_dls) {

        } else if (id == R.id.nav_buy) {

        } else if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
