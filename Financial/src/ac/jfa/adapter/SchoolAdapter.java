package ac.jfa.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ac.jfa.R;
import ac.jfa.modal.School;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

public class SchoolAdapter extends BaseAdapter {
	private List<School> info = null;
	private Map<Integer, View> rowViews = new HashMap<Integer, View>();
	private Context context = null;

	private Location location,location2 = null;
	private LocationManager locationManager;
	public SchoolAdapter(List<School> info, Context context) {
		this.info = info;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return info.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return info.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = rowViews.get(position);
		if (rowView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			rowView = layoutInflater.inflate(R.layout.school, null);
			Button button = (Button) rowView.findViewById(R.id.keiroButton);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// Criteria criteria = new Criteria();
					// LocationManager locationManager = (LocationManager)
					// context
					// .getSystemService(Context.LOCATION_SERVICE);
					// String provider =
					// locationManager.getBestProvider(criteria,
					// false);
					// Location location = locationManager
					// .getLastKnownLocation(provider);
					// double lat = location.getLatitude();
					// double lng = location.getLongitude();
					LatLng coordinate = new LatLng(getLocation().getLatitude(),
							getLocation().getLongitude());

					String uc = "東京・御茶ノ水・ソラシティ カンファレンスセンター";
					Intent intent = new Intent();
					intent.setClassName("com.google.android.apps.maps",
							"com.google.android.maps.MapsActivity");
					if (isIntentAvailable(intent)) {
						Intent i = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d&saddr="
										+ coordinate.latitude
										+ ","
										+ coordinate.longitude
										+ "&daddr="
										+ uc
										+ "&hl=jp"));
						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								& Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
						i.setClassName("com.google.android.apps.maps",
								"com.google.android.maps.MapsActivity");
						context.startActivity(i);
					} else {
						Intent i = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://map.google.com/maps?f=d&source=s_d&saddr="
										+ coordinate.latitude
										+ ","
										+ coordinate.longitude
										+ "&daddr="
										+ uc
										+ "&hl=jp"));
						context.startActivity(i);
					}

					// Intent intent = new Intent();
					// intent.setClass(context, MapActivity.class);
					// context.startActivity(intent);
				}
			});
			rowViews.put(position, rowView);
		}
		return rowView;
	}

	private boolean isIntentAvailable(Intent intent) {
		List<ResolveInfo> activities = context.getPackageManager()
				.queryIntentActivities(intent,
						PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
		return activities.size() != 0;
	}

	public Location getLocation() {
		
		try {
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);

			// getting GPS status
			boolean isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			boolean isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
				System.out.println("not");
			} else {
				// this.canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 5000, 500,
							new LocationListener() {

								@Override
								public void onStatusChanged(String provider,
										int status, Bundle extras) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onProviderEnabled(String provider) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onProviderDisabled(String provider) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onLocationChanged(Location location) {
									// TODO Auto-generated method stub

								}
							});
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							location2 = location;
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					System.out.println("yangxuGPScan");
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER, 5000, 500,
								new LocationListener() {

									@Override
									public void onStatusChanged(
											String provider, int status,
											Bundle extras) {
										// TODO Auto-generated method stub
									}

									@Override
									public void onProviderEnabled(
											String provider) {
										// TODO Auto-generated method stub
									}

									@Override
									public void onProviderDisabled(
											String provider) {
										// TODO Auto-generated method stub
									}

									@Override
									public void onLocationChanged(
											Location location) {
										// TODO Auto-generated method stub
									}
								});
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								location2 = location;
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location2;
	}
}
