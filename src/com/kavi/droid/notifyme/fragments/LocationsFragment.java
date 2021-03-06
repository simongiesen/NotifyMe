package com.kavi.droid.notifyme.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.kavi.droid.notifyme.activities.NewLocationActivity;
import com.kavi.droid.notifyme.adapters.LocationItemAdapter;
import com.kavi.droid.notifyme.database.LocalDatabaseSQLiteOpenHelper;
import com.kavi.droid.notifyme.database.LocationData;

import java.util.List;

/**
 * Created by Kavi on 11/5/14.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */
public class LocationsFragment extends Fragment {

    private View locationFragmentView;

    private Button addNewLocationViewButton;
    private ListView locationsListView;
    private ImageView locationsImageView;

    private LocationItemAdapter locationItemAdapter;
    private Context context;
    private LocalDatabaseSQLiteOpenHelper localDatabaseSQLiteOpenHelper;

    private AlertDialog messageBalloonAlertDialog;

	public LocationsFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        this.context = getActivity();
        this.localDatabaseSQLiteOpenHelper = new LocalDatabaseSQLiteOpenHelper(context);

        locationFragmentView = inflater.inflate(com.kavi.droid.notifyme.R.layout.fragment_locations, container, false);
        setUpViews();
         
        return locationFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadLocationsToListView();
    }

    private void setUpViews() {
        addNewLocationViewButton = (Button) locationFragmentView.findViewById(com.kavi.droid.notifyme.R.id.addNewLocationViewButton);
        locationsListView = (ListView) locationFragmentView.findViewById(com.kavi.droid.notifyme.R.id.locationsListView);
        locationsImageView = (ImageView) locationFragmentView.findViewById(com.kavi.droid.notifyme.R.id.locationImageView);

        addNewLocationViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewLocationViewIntent = new Intent(getActivity(), NewLocationActivity.class);
                startActivity(addNewLocationViewIntent);
            }
        });

        locationsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLocationsToListView();
            }
        });

        locationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final LocationData selectedLocation = (LocationData) locationsListView.getItemAtPosition(position);

                messageBalloonAlertDialog = new AlertDialog.Builder(context)
                        .setTitle("Saved Location")
                        .setMessage("Name: " + selectedLocation.getLocationName() + "\n" +
                        "Description: " + selectedLocation.getLocationDescription() + "\n" +
                        "CellId: " + selectedLocation.getCellId() + "\n" +
                        "L.A.C: " + selectedLocation.getLac() + "\n" +
                        "Latitude: " + selectedLocation.getLatitude() + "\n" +
                        "Longitude: " + selectedLocation.getLongitude() + "\n")
                        .setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                localDatabaseSQLiteOpenHelper.deleteLocationFromLocationId(selectedLocation.getLocationId());
                                loadLocationsToListView();
                            }
                        })
                        .setNeutralButton("Cancel", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                messageBalloonAlertDialog.cancel();
                            }
                        }).create();
                messageBalloonAlertDialog.show();
            }
        });
    }

    private void loadLocationsToListView() {
        List<LocationData> locationDataList = localDatabaseSQLiteOpenHelper.getAllLocations();
        if (locationDataList.size() != 0) {
            locationItemAdapter = new LocationItemAdapter(locationDataList, context);
            locationsListView.setAdapter(locationItemAdapter);
        }
    }
}
