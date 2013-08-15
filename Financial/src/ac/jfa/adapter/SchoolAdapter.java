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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
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

					Criteria criteria = new Criteria();
					LocationManager locationManager = (LocationManager) context
							.getSystemService(Context.LOCATION_SERVICE);
					String provider = locationManager.getBestProvider(criteria,
							false);
					Location location = locationManager
							.getLastKnownLocation(provider);
					double lat = location.getLatitude();
					double lng = location.getLongitude();
					LatLng coordinate = new LatLng(lat, lng);

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
}
