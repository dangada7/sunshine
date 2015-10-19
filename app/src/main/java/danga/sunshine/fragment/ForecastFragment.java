package danga.sunshine.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import danga.sunshine.activity.DetailsActivity;
import danga.sunshine.R;
import danga.sunshine.activity.SettingsActivity;
import danga.sunshine.async_task.FetchWeatherTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ArrayAdapter<String> arrayAdapter;

    //-----------------------------------------------------------------------------------
    @Override
    public void onStart() {
        super.onStart();
        updateWhether();
    }
    //-----------------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //-----------------------------------------------------------------------------------
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment_menu, menu);
    }

    //-----------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                updateWhether();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(getActivity(),SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_viewMaps:
                showMap();
                return true;
            default:

        }

        return super.onOptionsItemSelected(item);
    }//close method onOptionsItemSelected

    //-----------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new ArrayList());

        ListView listView = (ListView) rootView.findViewById(R.id.listView_forecast);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(this);

        return rootView;
    }//close method onCreateView

    //-----------------------------------------------------------------------------------
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String weatherInfo =  arrayAdapter.getItem(position);

        Intent intent = new Intent(getActivity(),DetailsActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, weatherInfo);
        startActivity(intent);
    }

    //-----------------------------------------------------------------------------------
    private String getPostcode() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String postcode = sharedPreferences.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));
        return postcode;
    }
    //-----------------------------------------------------------------------------------
    private void updateWhether() {
        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask(getActivity(),arrayAdapter);
        String postcode = getPostcode();
        Log.i("send postcode", "postcode = " + postcode);
        fetchWeatherTask.execute(postcode);
    }

    //-----------------------------------------------------------------------------------
    public void showMap() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = sharedPreferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));

        Uri uri = Uri.parse("geo:0,0?").
                buildUpon().appendQueryParameter("q",location).
                build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(),"sorry your phone dont have a app that can open maps",Toast.LENGTH_SHORT).show();

        }
    }
}
