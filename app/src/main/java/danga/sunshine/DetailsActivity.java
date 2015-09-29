package danga.sunshine;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private final String FORECAST_SHARED_HASHTAG = "#sunshine";
    private final String LOG_TAG = "DetailsActivity" ;

    private String mForecastStr;

    //--------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = this.getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView) this.findViewById(R.id.details_text)).setText(mForecastStr);
        }
    }
    //--------------------------------------------------------------------------
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);

        // Fetch and store ShareActionProvider
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if(mShareActionProvider != null){
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }else{
            Log.i(LOG_TAG, "mShareActionProvider == null");
        }

        // Return true to display menu
        return true;
    }
    //--------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //--------------------------------------------------------------------------
    private Intent createShareForecastIntent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,mForecastStr + FORECAST_SHARED_HASHTAG);

        return shareIntent;
    }
}
