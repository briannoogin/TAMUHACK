/* Copyright 2016 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.esri.arcgisruntime.sample.displaydevicelocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;
import com.esri.arcgisruntime.sample.spinner.ItemData;
import com.esri.arcgisruntime.sample.spinner.SpinnerAdapter;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

  private MapView mMapView;
  private LocationDisplay mLocationDisplay;
  private Spinner mSpinner;

  private int requestCode = 2;
  String[] reqPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission
      .ACCESS_COARSE_LOCATION};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);

    // Get the Spinner from layout
    mSpinner = (Spinner) findViewById(R.id.spinner);
    Portal portal = new Portal("http://www.arcgis.com");
    PortalItem mapPortalItem = new PortalItem(portal, "3e47bb1b9ccb411784b2d1a3668bae3a");

    // Get the MapView from layout and set a map with the BasemapType Imagery
    mMapView = (MapView) findViewById(R.id.mapView);
    ArcGISMap mMap = new ArcGISMap(mapPortalItem);
    mMapView.setMap(mMap);

    // get the MapView's LocationDisplay
    mLocationDisplay = mMapView.getLocationDisplay();


    // Listen to changes in the status of the location data source.
    mLocationDisplay.addDataSourceStatusChangedListener(new LocationDisplay.DataSourceStatusChangedListener() {
      @Override
      public void onStatusChanged(LocationDisplay.DataSourceStatusChangedEvent dataSourceStatusChangedEvent) {

        // If LocationDisplay started OK, then continue.
        if (dataSourceStatusChangedEvent.isStarted())
          return;

        // No error is reported, then continue.
        if (dataSourceStatusChangedEvent.getError() == null)
          return;

        // If an error is found, handle the failure to start.
        // Check permissions to see if failure may be due to lack of permissions.
        boolean permissionCheck1 = ContextCompat.checkSelfPermission(MapActivity.this, reqPermissions[0]) ==
            PackageManager.PERMISSION_GRANTED;
        boolean permissionCheck2 = ContextCompat.checkSelfPermission(MapActivity.this, reqPermissions[1]) ==
            PackageManager.PERMISSION_GRANTED;

        if (!(permissionCheck1 && permissionCheck2)) {
          // If permissions are not already granted, request permission from the user.
          ActivityCompat.requestPermissions(MapActivity.this, reqPermissions, requestCode);
        } else {
          // Report other unknown failure types to the user - for example, location services may not
          // be enabled on the device.
          String message = String.format("Error in DataSourceStatusChangedListener: %s", dataSourceStatusChangedEvent
              .getSource().getLocationDataSource().getError().getMessage());
          Toast.makeText(MapActivity.this, message, Toast.LENGTH_LONG).show();

          // Update UI to reflect that the location display did not actually start
          mSpinner.setSelection(0, true);
        }
      }
    });


    // Populate the list for the Location display options for the spinner's Adapter
    ArrayList<ItemData> list = new ArrayList<>();
    list.add(new ItemData("GPS Off", R.drawable.locationdisplaydisabled));
    list.add(new ItemData("GPS On", R.drawable.locationdisplayon));
    list.add(new ItemData("Re-Center", R.drawable.locationdisplayrecenter));
    list.add(new ItemData("Navigation", R.drawable.locationdisplaynavigation));
    list.add(new ItemData("Compass", R.drawable.locationdisplayheading));

    SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_layout, R.id.txt, list);
    mSpinner.setAdapter(adapter);
    mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
          case 0:
            // Stop Location Display
            if (mLocationDisplay.isStarted())
              mLocationDisplay.stop();
              break;
          case 1:
            // Start Location Display
            if (!mLocationDisplay.isStarted())
              mLocationDisplay.startAsync();
              break;
          case 2:
            // Re-Center MapView on Location
            // AutoPanMode - Default: In this mode, the MapView attempts to keep the location symbol on-screen by
            // re-centering the location symbol when the symbol moves outside a "wander extent". The location symbol
            // may move freely within the wander extent, but as soon as the symbol exits the wander extent, the MapView
            // re-centers the map on the symbol.
            mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
            if (!mLocationDisplay.isStarted())
              mLocationDisplay.startAsync();
              break;
          case 3:
            // Start Navigation Mode
            // This mode is best suited for in-vehicle navigation.
            mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
            if (!mLocationDisplay.isStarted())
              mLocationDisplay.startAsync();
            break;
          case 4:
            // Start Compass Mode
            // This mode is better suited for waypoint navigation when the user is walking.
            mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.COMPASS_NAVIGATION);
            if (!mLocationDisplay.isStarted())
              mLocationDisplay.startAsync();
              break;
        }

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) { }

    });
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    // If request is cancelled, the result arrays are empty.
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      // Location permission was granted. This would have been triggered in response to failing to start the
      // LocationDisplay, so try starting this again.
      mLocationDisplay.startAsync();
    } else {
      // If permission was denied, show toast to inform user what was chosen. If LocationDisplay is started again,
      // request permission UX will be shown again, option should be shown to allow never showing the UX again.
      // Alternative would be to disable functionality so request is not shown again.
      Toast.makeText(MapActivity.this, getResources().getString(R.string.location_permission_denied), Toast
          .LENGTH_SHORT).show();

      // Update UI to reflect that the location display did not actually start
      mSpinner.setSelection(0, true);
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    mMapView.pause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mMapView.resume();
  }

}