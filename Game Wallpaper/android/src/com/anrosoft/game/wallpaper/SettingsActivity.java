package com.anrosoft.game.wallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

/**
 * Settings activity
 *
 * @author sylsau - sylvain.saurel@gmail.com - http://www.ssaurel.com
 */
public class SettingsActivity extends PreferenceActivity {

    public final String APP_NAME = "Game Wallpaper";

    public static final String CHOOSE_GLOW_SPEED = "glowSpeedChooseList";
    public static final String SOUND_OPTION = "glowSoundChooseCheckbox";
    public static final String CONTACT_DEVELOPER = "contactDeveloper";
    public static final String SHARE_OPTION = "shareOption";
    public static final String RATE_OPTION = "rateOption";

    private ListPreference glowSpeedChooseListPreference = null;
    private CheckBoxPreference glowSoundChoosePreference = null;
    private Preference contactDeveloperPreference = null;
    private Preference rateOptionPreference = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);

        glowSpeedChooseListPreference = (ListPreference) findPreference(CHOOSE_GLOW_SPEED);
        glowSpeedChooseListPreference.setOnPreferenceChangeListener(prefChangeListener);

        glowSoundChoosePreference = (CheckBoxPreference) findPreference(SOUND_OPTION);
        glowSoundChoosePreference.setOnPreferenceChangeListener(prefChangeListener);

        contactDeveloperPreference = findPreference(CONTACT_DEVELOPER);
        contactDeveloperPreference.setOnPreferenceChangeListener(prefChangeListener);

        rateOptionPreference = findPreference(RATE_OPTION);
        rateOptionPreference.setOnPreferenceChangeListener(prefChangeListener);

        Preference myPref = (Preference) findPreference(SHARE_OPTION);
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showShare();
                return false;
            }
        });
    }

    public void showShare() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent
                .putExtra(
                        Intent.EXTRA_TEXT,
                        APP_NAME + " Visit: https://play.google.com/store/apps/details?id=" + getPackageName());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }


    private OnPreferenceChangeListener prefChangeListener = new OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (CHOOSE_GLOW_SPEED.equals(preference.getKey())) {
                int index = Integer.parseInt(newValue.toString());
                if (index >= 0 && index <= 3) {
                    if (index == 0) {
                        WallPaperAssetStore.BALLS_SPEED_RATIO = 0.5f;
                    } else if (index == 1) {
                        WallPaperAssetStore.BALLS_SPEED_RATIO = 1.0f;
                    } else if (index == 2) {
                        WallPaperAssetStore.BALLS_SPEED_RATIO = 1.5f;
                    } else if (index == 3) {
                        WallPaperAssetStore.BALLS_SPEED_RATIO = 2.0f;
                    }
                }
                return true;
            } else if (SOUND_OPTION.equals(preference.getKey())) {
                boolean value = (Boolean) newValue;
                if (value) {
                    WallPaperAssetStore.isSoundEnable = true;
                } else {
                    WallPaperAssetStore.isSoundEnable = false;
                }
                return true;
            }
            return false;
        }
    };
}
