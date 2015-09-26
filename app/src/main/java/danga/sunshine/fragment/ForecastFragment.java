package danga.sunshine.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import danga.sunshine.DetailsActivity;
import danga.sunshine.async_task.FetchWeatherTask;
import danga.sunshine.R;
import danga.sunshine.SettingsActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ArrayAdapter<String> arrayAdapter;

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
                FetchWeatherTask fetchWeatherTask = new FetchWeatherTask(arrayAdapter);
                fetchWeatherTask.execute("94043");
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(getActivity(),SettingsActivity.class);
                startActivity(intent);
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
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textView,
                new ArrayList<String>());

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
        intent.putExtra(Intent.EXTRA_TEXT,weatherInfo);
        startActivity(intent);
    }
}
