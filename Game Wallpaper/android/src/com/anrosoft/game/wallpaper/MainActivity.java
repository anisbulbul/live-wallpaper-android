package com.anrosoft.game.wallpaper;

import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    private static final String YOUTUBE_CHANNEL = "UCqZzw1k0zMyGMBIHTjslMyw";
    private static final String YOUTUBE_ID = "2AERMXfPJJc";
    private static final String WEBSITE_APPS = "https://apps.anrosoft.com";
    private static final String PLAYSTORE_DEVELOPER = "https://play.google.com/store/apps/developer?id=AnroSoft";
    private static final String DEVELOPER_MAIL = "anrosoft.office@gmail.com";
    private static final String APP_NAME = "Game Wallpaper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getSupportActionBar().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.actionbar_bg_style));
    }

    public void showOtherApps(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(Uri
                        .parse(PLAYSTORE_DEVELOPER));
        startActivity(intent);
    }


    public void seeWebSite(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(Uri
                        .parse(WEBSITE_APPS));
        startActivity(intent);
    }

    public void set3DLw(View v) {
        setting3DWallPaper();
    }

    public void setting3DWallPaper() {
        Intent intent = new Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(this, Android3DWallpaper.class));
        startActivity(intent);
    }

    public void showTutorial() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("vnd.youtube:" + YOUTUBE_ID));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/channel/" + YOUTUBE_CHANNEL));
            startActivity(intent);
        }
    }

    public void shareOption() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, APP_NAME + " Visit: http://play.google.com/store/apps/details?id="
                + getPackageName());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void rateOption() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + getPackageName())));
        } catch (ActivityNotFoundException e) {

        }
    }

    public void contactOption() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("mailto:" + DEVELOPER_MAIL)));
        } catch (ActivityNotFoundException e) {

        }
    }

    public void proVersionOption() {
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(Uri
                        .parse(PLAYSTORE_DEVELOPER));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            shareOption();
            return true;
        } else if (id == R.id.setting) {
            setting3DWallPaper();
            return true;
        } else if (id == R.id.action_settings) {
            setting3DWallPaper();
            return true;
        } else if (id == R.id.action_rate) {
            rateOption();
            return true;
        } else if (id == R.id.action_share) {
            shareOption();
            return true;
        } else if (id == R.id.action_contact) {
            contactOption();
            return true;
        } else if (id == R.id.action_pro_version) {
            proVersionOption();
            return true;
        } else if (id == R.id.action_exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
