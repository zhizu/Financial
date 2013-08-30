package ac.jfa.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.AnimActivity;
import ac.jfa.CategoryActivity;
import ac.jfa.KouzaActivity;
import ac.jfa.MainActivity;
import ac.jfa.R;
import ac.jfa.download.AsyncHttpClient;
import ac.jfa.download.JsonHttpResponseHandler;
import ac.jfa.modal.School;
import ac.jfa.util.JsonUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class SchoolAdapter extends BaseAdapter {
	protected static final android.content.DialogInterface.OnClickListener OnClickListener = null;
	private List<School> info = null;
	private Map<Integer, View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	private String dl_sid;
	private String sid;
	private ProgressBar progress = null;

	private Location location,location2 = null;
	private LocationManager locationManager;
	public SchoolAdapter(List<School> info, Context context,String dl_sid,String sid,ProgressBar progress) {
		this.info = info;
		this.context = context;
		this.dl_sid = dl_sid;
		this.sid = sid;
		this.progress = progress;
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
			final School item = (School) getItem(position);
			Button name_btn = (Button)rowView.findViewById(R.id.name_btn);
			Button kougi_btn = (Button)rowView.findViewById(R.id.kougi_btn);
			Button map_btn = (Button)rowView.findViewById(R.id.map_btn);
			final Button mousi_btn = (Button)rowView.findViewById(R.id.mousi_btn);
			TextView content_title = (TextView)rowView.findViewById(R.id.content_title);
			TextView content_title1 = (TextView)rowView.findViewById(R.id.content_title1);
			TextView content_title2 = (TextView)rowView.findViewById(R.id.content_title2);
			TextView content_title3 = (TextView)rowView.findViewById(R.id.content_title3);
			TextView content_title0 = (TextView)rowView.findViewById(R.id.content_title0);
			TextView kaijyo = (TextView)rowView.findViewById(R.id.kaijyo);
			View line = (View)rowView.findViewById(R.id.line);
			if(item.getChu_no().equals("")){
				if(Integer.parseInt(item.getSho_zaiko())>0){
					mousi_btn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							progress.setVisibility(View.VISIBLE);
							AsyncHttpClient client = new AsyncHttpClient();
							client.get(context,
									"https://www.f-academy.jp/sp/app/api/mypage.cgi?sid="+sid+"&sai_no="
											+ item.getSho_no(), new JsonHttpResponseHandler() {

										@Override
										public void onSuccess(JSONObject jsonObject) {
											// TODO Auto-generated method stub
											progress.setVisibility(View.GONE);
											Toast.makeText(context, "お申込みが完了しました", Toast.LENGTH_SHORT).show();
											Intent intent = ((Activity) context).getIntent();
											intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
										    ((Activity) context).finish();
										    context.startActivity(intent);
									
											super.onSuccess(jsonObject);
										}

									});
						}
					});
				}else{
					mousi_btn.setText("受付終了");
				}
			}else{
				 mousi_btn.setText("キャンセル");
				 mousi_btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
					    builder.setTitle("提示");
					    builder.setMessage("本当にキャンセルしますか？");
					    builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								progress.setVisibility(View.VISIBLE);
								AsyncHttpClient client = new AsyncHttpClient();
								client.get(context,
										"https://www.f-academy.jp/sp/app/api/mypage.cgi?sid="+sid+"&can_no="
												+ item.getChu_no(), new JsonHttpResponseHandler() {

											@Override
											public void onSuccess(JSONObject jsonObject) {
												// TODO Auto-generated method stub
												progress.setVisibility(View.GONE);
												Intent intent = ((Activity) context).getIntent();
												intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
											    ((Activity) context).finish();
											    context.startActivity(intent);
												super.onSuccess(jsonObject);
											}

										});
							}
						});
					    builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
						builder.create().show();

					}
				});
			}
			
			
			
			
			if(item.isFlag()){
				content_title1.setText(item.getTitle());
				content_title0.setText(item.getKigen());
				name_btn.setVisibility(View.GONE);
				kougi_btn.setVisibility(View.GONE);
				map_btn.setVisibility(View.GONE);
				mousi_btn.setVisibility(View.GONE);
				content_title.setVisibility(View.GONE);
				content_title2.setVisibility(View.GONE);
				content_title3.setVisibility(View.GONE);
				button.setVisibility(View.GONE);
				kaijyo.setVisibility(View.GONE);
				line.setVisibility(View.GONE);
			}else{

				if(item.getKaijo_address().equals("")){
					button.setVisibility(View.GONE);
				}else{
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
				}
				
				
				name_btn.setText(item.getTitle());
				
				kougi_btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.putExtra("dl_sid", dl_sid);
						intent.putExtra("movie_category", "school");
						intent.setClass(context, AnimActivity.class);
						context.startActivity(intent);
						((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out);
					}
				});
				if(item.getKaijo_map_url().equals("")){
					map_btn.setVisibility(View.GONE);
				}else{
					map_btn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(context, KouzaActivity.class);
							intent.putExtra("url", item.getKaijo_map_url());
							intent.putExtra("from", "schooladapter");
							context.startActivity(intent);
							((Activity) context).overridePendingTransition(R.anim.in_from_right,
									R.anim.out);
						}
					});
				}
				
				
				
				content_title.setText(item.getKigen());
				
				content_title1.setText(item.getSho_name());
				
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
				content_title2.setText(sb.toString());
				
				content_title3.setText(item.getKaijo());
			}
			
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
