package me.kalehv.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private String mLocation;
    private static final String FORECASTFRAGMENT_TAG = "forecastfragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment(), FORECASTFRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String location = Utility.getPreferredLocation(this);
        if (location != null && !location.equals(mLocation)) {
            ForecastFragment forecastFragment = (ForecastFragment) getSupportFragmentManager().findFragmentByTag(FORECASTFRAGMENT_TAG);
            if (forecastFragment != null) {
                forecastFragment.onLocationChanged();
            }
            mLocation = location;
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_show_map) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            mLocation = sharedPreferences.getString(
                    getString(R.string.pref_location_key),
                    getString(R.string.pref_location_default_value)
            );

            Uri geolocation = Uri.parse("geo:0,0").buildUpon()
                    .appendQueryParameter("q", mLocation)
                    .build();

            showMap(geolocation);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMap(Uri geolocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geolocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
