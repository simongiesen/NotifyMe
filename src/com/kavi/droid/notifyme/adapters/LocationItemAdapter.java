package com.kavi.droid.notifyme.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kavi.droid.notifyme.database.LocationData;
import com.kavi.droid.notifyme.views.LocationListItem;

import java.util.List;

/**
 * Created by Kavi on 11/10/14.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */
public class LocationItemAdapter extends BaseAdapter {

    private List<LocationData> locationDataList;
    private Context context;

    public LocationItemAdapter(List<LocationData> locationDataList, Context context) {
        this.locationDataList = locationDataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return locationDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return (locationDataList == null)? null: locationDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LocationListItem locationListItem;
        if (convertView == null){
            locationListItem = (LocationListItem) View.inflate(context, com.kavi.droid.notifyme.R.layout.location_list_item, null);
        } else {
            locationListItem = (LocationListItem) convertView;
        }

        locationListItem.setLocationData(locationDataList.get(position));
        return locationListItem;
    }
}
