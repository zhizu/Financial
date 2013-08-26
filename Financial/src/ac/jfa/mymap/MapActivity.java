package ac.jfa.mymap;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ac.jfa.R;
import ac.jfa.download.AsyncHttpClient;
import ac.jfa.download.JsonHttpResponseHandler;
import ac.jfa.util.JsonUtils;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends FragmentActivity{

	private  GoogleMap map = null;
//	private LatLng coordinate = null;
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.map);
	        
	        Criteria criteria = new Criteria();
	        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        String provider = locationManager.getBestProvider(criteria, false);
//	        Location location = locationManager.getLastKnownLocation(provider);
//	        double lat =  location.getLatitude();
//	        double lng = location.getLongitude();
//	        coordinate = new LatLng(lat, lng);
	       map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map))
	                .getMap();
	        
	        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	        map.addMarker(new MarkerOptions().position(new LatLng( 35.69987450,139.76377490)).title("Marker"));
	        map.setMyLocationEnabled(true);
	        map.setTrafficEnabled(false);
	        
//	        CameraUpdate center=
//	                CameraUpdateFactory.newLatLng(coordinate);
//	            CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
//
//	            map.moveCamera(center);
//	            map.animateCamera(zoom);
	        
			AsyncHttpClient client = new AsyncHttpClient();
			 client.get(MapActivity.this, "http://maps.googleapis.com/maps/api/directions/json?origin=35.6982201,139.7468066&destination=35.69987450,139.76377490&sensor=false&mode=walking", new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(JSONObject jsonObject) {
					// TODO Auto-generated method stub
					try {
						JSONArray array = jsonObject.getJSONArray("routes");
						JSONObject route = array.getJSONObject(0);
						
						JSONArray array2 = route.getJSONArray("legs");
						JSONObject array3 = array2.getJSONObject(0);
						JSONArray array4 = array3.getJSONArray("steps");
						System.out.println("yang--->" + array4.toString());
						JsonUtils jsonUtils = new JsonUtils();
						List<LatLng> list = new ArrayList<LatLng>();
						list = jsonUtils.parsePoilyFromJson(array4);
//						JSONObject polyline = route.getJSONObject("overview_polyline");
//						String str = polyline.getString("points");
//						 List<LatLng> points = decodePoly(str); 
//						 for(int i=0;i<150;i++){
//							 System.out.println("lat-->" + points.get(i).latitude + "lng-->" + points.get(i).longitude + "/n");
//						 }
//						 System.out.println("gaosiwei--->" + str);
//						System.out.println("yangxu3--->" + polyline.toString());
						 map.addPolyline(new PolylineOptions().addAll(list).width((float)10.0).color(Color.BLUE));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						System.out.println("yangxu--->" + "error");
						e.printStackTrace();
					}
					
					super.onSuccess(jsonObject);
				}
				 
			 });
	        
	    }

	  private List<LatLng> decodePoly(String encoded) {  
		  
		  List<LatLng> poly = new ArrayList<LatLng>();
		  int index = 0, len = encoded.length();
		  int lat = 0, lng = 0;

		  while (index < len) {
		      int b, shift = 0, result = 0;
		      do {
		          b = encoded.charAt(index++) - 63;
		          result |= (b & 0x1f) << shift;
		          shift += 5;
		      } while (b >= 0x20);
		      int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		      lat += dlat;

		      shift = 0;
		      result = 0;
		      do {
		          b = encoded.charAt(index++) - 63;
		          result |= (b & 0x1f) << shift;
		          shift += 5;
		      } while (b >= 0x20);
		      int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
		      lng += dlng;

		     LatLng p = new LatLng((int) (((double) lat / 1E5) * 1E6),
		           (int) (((double) lng / 1E5) * 1E6));
		      poly.add(p);
		  }

		  return poly;
	    }  
}
