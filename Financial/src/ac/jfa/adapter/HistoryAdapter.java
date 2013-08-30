package ac.jfa.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.maps.model.LatLng;

import ac.jfa.KouzaActivity;
import ac.jfa.R;
import ac.jfa.modal.HistoryItem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryAdapter extends BaseAdapter {

	private List<HistoryItem> info = null;
	private Map<Integer, View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	private Location location, location2 = null;
	private LocationManager locationManager;

	public HistoryAdapter(List<HistoryItem> info, Context context) {
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
			rowView = layoutInflater.inflate(R.layout.historyitem, null);
			final HistoryItem item = (HistoryItem) getItem(position);
			Button tani = (Button) rowView.findViewById(R.id.tani);
			TextView title = (TextView) rowView.findViewById(R.id.title);
			TextView time = (TextView) rowView.findViewById(R.id.time);
			TextView locate = (TextView) rowView.findViewById(R.id.locate);
			Button map = (Button) rowView.findViewById(R.id.map);
			Button keiro = (Button) rowView.findViewById(R.id.keiro);
			ImageView imageView = (ImageView) rowView
					.findViewById(R.id.imageView);
			tani.setText(item.getTani() + "単位");
			title.setText(item.getSho_name());
			String text = item.getNittei();
			StringBuilder sb = new StringBuilder(text);
			sb.delete(0, 1);
			sb.delete(sb.length()-1, sb.length());
			char[] array = sb.toString().toCharArray();
			for(int i=0; i < array.length; i++){
				if(array[i] == ','){
					sb.replace(i, i+1, "\n");
				}
			}
			time.setText("日時：" + sb.toString());
			if (item.getKaijo().equals("")) {
				locate.setVisibility(View.GONE);
			} else {
				locate.setText("会場：" + item.getKaijo());
			}
			if (item.getKaijo_address().equals("")) {
				keiro.setVisibility(View.GONE);
			} else {
				keiro.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						LatLng coordinate = new LatLng(getLocation()
								.getLatitude(), getLocation().getLongitude());

						String uc = item.getKaijo_address();
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
											+ uc + "&hl=jp"));
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
											+ uc + "&hl=jp"));
							context.startActivity(i);
						}
					}
				});
			}
			if (item.getKaijo_map_url().equals("")) {
				map.setVisibility(View.GONE);
			} else {
                map.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(context, KouzaActivity.class);
						intent.putExtra("url", item.getKaijo_map_url());
						intent.putExtra("from", "historyadapter");
						context.startActivity(intent);
						((Activity) context).overridePendingTransition(R.anim.in_from_right,
								R.anim.out);
					}
				});
			}
			if (item.getStatus().equals("1")) {
				imageView.setImageResource(R.drawable.branch_2);
			}
			rowViews.put(position, rowView);
		}
		return rowView;
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

	private boolean isIntentAvailable(Intent intent) {
		List<ResolveInfo> activities = context.getPackageManager()
				.queryIntentActivities(intent,
						PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
		return activities.size() != 0;
	}
}
