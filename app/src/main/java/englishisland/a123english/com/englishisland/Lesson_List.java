package englishisland.a123english.com.englishisland;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import englishisland.a123english.com.englishisland.Adapter.LessonsLVAdapter;
import englishisland.a123english.com.englishisland.TO.Lesson;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;
import util.SecurePreferences;


public class Lesson_List extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Lesson> LessonsList = new ArrayList<>();
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    SecurePreferences prefs;    // Debug tag, for logging
    static final String TAG = "Mamaly";
    ListView lvMain;
    // SKUs for our products: the premium upgrade (non-consumable)
    static final String SKU2 = "Lesson2";
    static final String SKU3 = "Lesson3";
    static final String SKU4 = "Lesson4";
    static final String SKU5 = "Lesson5";
    static final String SKU6 = "Lesson6";
    static final String SKU7 = "Lesson7";
    static final String SKU8 = "Lesson8";
    static final String SKU9 = "Lesson9";
    static final String SKU10 = "Lesson10";

    // Does the user have the premium upgrade?
    int Lesson1_access;
    int Lesson2_access;
    int Lesson3_access;
    int Lesson4_access;
    int Lesson5_access;
    int Lesson6_access;
    int Lesson7_access;
    int Lesson8_access;
    int Lesson9_access;
    int Lesson10_access;

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 1001;
    LessonsLVAdapter LVAdapter;
    IabHelper mHelper;
    List<Integer> PremiumAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale = new Locale("fa");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.lesson_list);

        // prefs = new SecurePreferences(Lesson_List.this, "prefs", "SometopSecretKey1235", true);
// Put (all puts are automatically committed)
// Get
        prefs = SecurePreferences.getInstance(Lesson_List.this,"tutorialsFACE_Prefs"); //provide context & preferences name.

        //Storing the username inside shared preferences

        //Retrieving username from encrypted shared preferences


          //      prefs = new SecurePreferences(Lesson_List.this);

                Lesson1_access = prefs.getInt("Lesson1", 1);
                Lesson2_access = prefs.getInt("Lesson2", 0);
                Lesson3_access = prefs.getInt("Lesson3", 0);
                Lesson4_access = prefs.getInt("Lesson4", 0);
                Lesson5_access = prefs.getInt("Lesson5", 0);
                Lesson6_access = prefs.getInt("Lesson6", 0);
                Lesson7_access = prefs.getInt("Lesson7", 0);
                Lesson8_access = prefs.getInt("Lesson8", 0);
                Lesson9_access = prefs.getInt("Lesson9", 0);
                Lesson10_access = prefs.getInt("Lesson10", 0);



        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.Title);
        setSupportActionBar(toolbar);


        String base64EncodedPublicKey = getString(R.string.key);


        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                List<String> additionalSkuList = new ArrayList<String>();
                additionalSkuList.add(SKU2);
                additionalSkuList.add(SKU3);
                additionalSkuList.add(SKU4);
                additionalSkuList.add(SKU5);
                additionalSkuList.add(SKU6);
                additionalSkuList.add(SKU7);
                additionalSkuList.add(SKU8);
                additionalSkuList.add(SKU9);
                additionalSkuList.add(SKU10);
               /* mHelper.queryInventoryAsync(false, additionalSkuList,
                        mGotInventoryListener);  */          }
        });

       PopulateListView();

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: {
                        Toast.makeText(getApplicationContext(), "you have access to lesson" + position++, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Lesson_List.this, Class_Activity.class).putExtra("Lesson", 1));
                        break;
                    }
                    case 1: {
                        if (Lesson2_access == 0){
                            if (mHelper != null) mHelper.flagEndAsync();
                            String payload = "mamad1376";
                            mHelper.launchPurchaseFlow(Lesson_List.this, SKU2, RC_REQUEST,
                                    mPurchaseFinishedListener, payload);
                        }else if (Lesson2_access == 1){
                            Toast.makeText(getApplicationContext(), "you have access to lesson" + position++, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    }case 2: {
                        if (Lesson3_access == 0){
                            if (mHelper != null) mHelper.flagEndAsync();
                            String payload = "mamad1376";
                            mHelper.launchPurchaseFlow(Lesson_List.this, SKU3, RC_REQUEST,
                                    mPurchaseFinishedListener, payload);
                        }else if (Lesson3_access == 1){
                            Toast.makeText(getApplicationContext(), "you have access to lesson" + position++, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    }case 3: {
                        if (Lesson4_access == 0){
                            if (mHelper != null) mHelper.flagEndAsync();
                            String payload = "mamad1376";
                            mHelper.launchPurchaseFlow(Lesson_List.this, SKU4, RC_REQUEST,
                                    mPurchaseFinishedListener, payload);
                        }else if (Lesson4_access == 1){
                            Toast.makeText(getApplicationContext(), "you have access to lesson" + position++, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }case 4: {
                        if (Lesson5_access == 0){
                            if (mHelper != null) mHelper.flagEndAsync();
                            String payload = "mamad1376";
                            mHelper.launchPurchaseFlow(Lesson_List.this, SKU5, RC_REQUEST,
                                    mPurchaseFinishedListener, payload);
                        }else if (Lesson5_access == 1){
                            Toast.makeText(getApplicationContext(), "you have access to lesson" + position++, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    }case 5: {
                        if (Lesson6_access == 0){
                            if (mHelper != null) mHelper.flagEndAsync();
                            String payload = "mamad1376";
                            mHelper.launchPurchaseFlow(Lesson_List.this, SKU6, RC_REQUEST,
                                    mPurchaseFinishedListener, payload);
                        }else if (Lesson6_access == 1){
                            Toast.makeText(getApplicationContext(), "you have access to lesson" + position++, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    }case 6: {
                        if (Lesson7_access == 0){
                            if (mHelper != null) mHelper.flagEndAsync();
                            String payload = "mamad1376";
                            mHelper.launchPurchaseFlow(Lesson_List.this, SKU7, RC_REQUEST,
                                    mPurchaseFinishedListener, payload);
                        }else if (Lesson7_access == 1){
                            Toast.makeText(getApplicationContext(), "you have access to lesson" + position++, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    }case 7: {
                        if (Lesson8_access == 0){
                            if (mHelper != null) mHelper.flagEndAsync();
                            String payload = "mamad1376";
                            mHelper.launchPurchaseFlow(Lesson_List.this, SKU8, RC_REQUEST,
                                    mPurchaseFinishedListener, payload);
                        }else if (Lesson8_access == 1){
                            Toast.makeText(getApplicationContext(), "you have access to lesson" + position++, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    }case 8: {
                        if (Lesson9_access == 0){
                            if (mHelper != null) mHelper.flagEndAsync();
                            String payload = "mamad1376";
                            mHelper.launchPurchaseFlow(Lesson_List.this, SKU9, RC_REQUEST,
                                    mPurchaseFinishedListener, payload);
                        }else if (Lesson9_access == 1){
                            Toast.makeText(getApplicationContext(), "you have access to lesson" + position++, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    }case 9: {
                        if (Lesson10_access == 0){
                            if (mHelper != null) mHelper.flagEndAsync();
                            String payload = "mamad1376";
                            mHelper.launchPurchaseFlow(Lesson_List.this, SKU10, RC_REQUEST,
                                    mPurchaseFinishedListener, payload);
                        }else if (Lesson10_access == 1){
                            Toast.makeText(getApplicationContext(), "you have access to lesson" + position++, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    }


                }

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    private void PopulateListView() {
        lvMain = (ListView) findViewById(R.id.LessonsLV);
        LessonsList.clear();
        PremiumAccess = new ArrayList<Integer>();
        PremiumAccess.add(Lesson1_access);
        PremiumAccess.add(Lesson2_access);
        PremiumAccess.add(Lesson3_access);
        PremiumAccess.add(Lesson4_access);
        PremiumAccess.add(Lesson5_access);
        PremiumAccess.add(Lesson6_access);
        PremiumAccess.add(Lesson7_access);
        PremiumAccess.add(Lesson8_access);
        PremiumAccess.add(Lesson9_access);
        PremiumAccess.add(Lesson10_access);
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
            lesson.setIsPremium(PremiumAccess.get(i));
            LessonsList.add(lesson);
        }
        LVAdapter = new LessonsLVAdapter(getApplicationContext(), LessonsList);
        lvMain.setAdapter(LVAdapter);
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                Log.d(TAG, "Failed to query inventory: ");
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase Lesson2 = inventory.getPurchase(SKU2);
            Purchase Lesson3 = inventory.getPurchase(SKU3);
            Purchase Lesson4 = inventory.getPurchase(SKU4);
            Purchase Lesson5 = inventory.getPurchase(SKU5);
            Purchase Lesson6 = inventory.getPurchase(SKU6);
            Purchase Lesson7 = inventory.getPurchase(SKU7);
            Purchase Lesson8 = inventory.getPurchase(SKU8);
            Purchase Lesson9 = inventory.getPurchase(SKU9);
            Purchase Lesson10 = inventory.getPurchase(SKU10);



            if (Lesson2 != null) {
                Lesson2_access = 1;
            } else if (Lesson3 != null && verifyDeveloperPayload(Lesson3)) {
                Lesson3_access = 1;
            } else if (Lesson4 != null && verifyDeveloperPayload(Lesson4)) {
                Lesson4_access = 1;
            } else if (Lesson5 != null && verifyDeveloperPayload(Lesson5)) {
                Lesson5_access = 1;
            } else if (Lesson6 != null && verifyDeveloperPayload(Lesson6)) {
                Lesson6_access = 1;
            } else if (Lesson7 != null && verifyDeveloperPayload(Lesson7)) {
                Lesson7_access = 1;
            } else if (Lesson8 != null && verifyDeveloperPayload(Lesson8)) {
                Lesson8_access = 1;
            } else if (Lesson9 != null && verifyDeveloperPayload(Lesson9)) {
                Lesson9_access = 1;
            } else if (Lesson10 != null && verifyDeveloperPayload(Lesson10)) {
                Lesson10_access = 1;
            }


            PopulateListView();
            LVAdapter.notifyDataSetChanged();
            saveData();


        }



    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }
    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU2)) {
                Lesson2_access = 1;
                Toast.makeText(getApplicationContext(), "purchase successful 2", Toast.LENGTH_SHORT).show();
            }
            if (purchase.getSku().equals(SKU3)) {
                Lesson3_access = 1;
                Toast.makeText(getApplicationContext(), "purchase successful 3", Toast.LENGTH_SHORT).show();
            }
            if (purchase.getSku().equals(SKU4)) {
                Lesson4_access = 1;
                Toast.makeText(getApplicationContext(), "purchase successful 4", Toast.LENGTH_SHORT).show();
            }
            if (purchase.getSku().equals(SKU5)) {
                Lesson5_access = 1;
                Toast.makeText(getApplicationContext(), "purchase successful 5", Toast.LENGTH_SHORT).show();
            }
            if (purchase.getSku().equals(SKU6)) {
                Lesson6_access = 1;
                Toast.makeText(getApplicationContext(), "purchase successful 6", Toast.LENGTH_SHORT).show();
            }
            if (purchase.getSku().equals(SKU7)) {
                Lesson7_access = 1;
                Toast.makeText(getApplicationContext(), "purchase successful 7", Toast.LENGTH_SHORT).show();
            }
            if (purchase.getSku().equals(SKU8)) {
                Lesson8_access = 1;
                Toast.makeText(getApplicationContext(), "purchase successful 8", Toast.LENGTH_SHORT).show();
            }
            if (purchase.getSku().equals(SKU9)) {
                Lesson9_access = 1;
                Toast.makeText(getApplicationContext(), "purchase successful 9", Toast.LENGTH_SHORT).show();
            }
            if (purchase.getSku().equals(SKU10)) {
                Lesson10_access = 1;
                Toast.makeText(getApplicationContext(), "purchase successful 10", Toast.LENGTH_SHORT).show();
            }

            PopulateListView();
            LVAdapter.notifyDataSetChanged();
          saveData();

        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_intro) {

            startActivity(new Intent(Lesson_List.this, Class_Activity.class));
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }


    void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        //alert("Error: " + message);
    }
/*
    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }*/

    void saveData() {
        prefs.putInt("Lesson2", Lesson2_access);
        prefs.putInt("Lesson3", Lesson3_access);
        prefs.putInt("Lesson4", Lesson4_access);
        prefs.putInt("Lesson5", Lesson5_access);
        prefs.putInt("Lesson6", Lesson6_access);
        prefs.putInt("Lesson7", Lesson7_access);
        prefs.putInt("Lesson8", Lesson8_access);
        prefs.putInt("Lesson9", Lesson9_access);
        prefs.putInt("Lesson10", Lesson10_access);
        prefs.commit();


    }


}
