package com.anrosoft.compass.wallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * Settings activity
 *
 * @author sylsau - sylvain.saurel@gmail.com - http://www.ssaurel.com
 */
public class SettingsActivity extends PreferenceActivity {

    /**
     * Key for display hand sec.
     */
    public static final String CHOOSE_EARTH = "earthChooseList";
    public static final String CHOOSE_COMPASS = "wheelChooseList";
    public static final String CONTACT_DEVELOPER = "contactDeveloper";
    public static final String SHARE_OPTION = "shareOption";
    public static final String RATE_OPTION = "rateOption";

    private ListPreference earthChooseListPreference = null;
    private ListPreference wheelChooseListPreference = null;
    private Preference contactDeveloperPreference = null;
    private Preference shareOptionPreference = null;
    private Preference rateOptionPreference = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);

        earthChooseListPreference = (ListPreference) findPreference(CHOOSE_EARTH);
        earthChooseListPreference.setOnPreferenceChangeListener(prefChangeListener);

        wheelChooseListPreference = (ListPreference) findPreference(CHOOSE_COMPASS);
        wheelChooseListPreference.setOnPreferenceChangeListener(prefChangeListener);

        contactDeveloperPreference = findPreference(CONTACT_DEVELOPER);
        contactDeveloperPreference.setOnPreferenceChangeListener(prefChangeListener);

//        shareOptionPreference = findPreference(SHARE_OPTION);
//        shareOptionPreference.setOnPreferenceChangeListener(prefChangeListener);

        Preference myPref = (Preference) findPreference(SHARE_OPTION);
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showShare();
                return false;
            }
        });

        rateOptionPreference = findPreference(RATE_OPTION);
        rateOptionPreference.setOnPreferenceChangeListener(prefChangeListener);
    }

    public void showShare() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent
                .putExtra(
                        Intent.EXTRA_TEXT,
                        " 3D Compass Wallpaper "
                                + " Visit: https://play.google.com/store/apps/details?id=com.anrosoft.brainsudoku");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }


    private OnPreferenceChangeListener prefChangeListener = new OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (CHOOSE_EARTH.equals(preference.getKey())) {
                int index = Integer.parseInt(newValue.toString());
                if (index >= 0 && index <= 5) {
                    WallpaperAssetsTore.earthIndex = index;
                }
                return true;
            } else if (CHOOSE_COMPASS.equals(preference.getKey())) {
                int index = Integer.parseInt(newValue.toString());
                if (index >= 0 && index <= 5) {
                    WallpaperAssetsTore.wheelIndex = index;
                }
                return true;
            } else if (CONTACT_DEVELOPER.equals(preference.getKey())) {
                boolean value = (Boolean) newValue;
                Toast.makeText(getApplicationContext(), value + " : contact", Toast.LENGTH_LONG).show();
                return true;
            } else if (SHARE_OPTION.equals(preference.getKey())) {
                boolean value = (Boolean) newValue;
                Toast.makeText(getApplicationContext(), value + " : share", Toast.LENGTH_LONG).show();
                return true;
            } else if (RATE_OPTION.equals(preference.getKey())) {
                boolean value = (Boolean) newValue;
                Toast.makeText(getApplicationContext(), value + " : rate", Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
        }
    };
}
