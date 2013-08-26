package ac.jfa.adapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ac.jfa.KouzaActivity;
import ac.jfa.R;
import ac.jfa.VedioPlayActivity;
import ac.jfa.modal.Video;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;


public class VideoAdapter extends BaseAdapter{

	private List<Video>info = null;
	private Map<Integer,View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	private Video item = null;
	public VideoAdapter(List<Video> info,Context context){
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
		if(rowView == null){
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			rowView = layoutInflater.inflate(R.layout.vedioitem, null);
			item = (Video) getItem(position);
	        TextView title = (TextView)rowView.findViewById(R.id.content_title);
	        title.setText(item.getMovie_title());
			ImageView imageView = (ImageView)rowView.findViewById(R.id.videoView);
			
			TextView name = (TextView)rowView.findViewById(R.id.name);
			TextView date = (TextView)rowView.findViewById(R.id.date);
			name.setText(item.getSpeaker());
			date.setText(item.getMovie_date());
			Button button = (Button)rowView.findViewById(R.id.download);
			button.setText(item.getText_link_title());
			button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(context, KouzaActivity.class);
					intent.putExtra("from", "video");
					intent.putExtra("url", item.getText_link_url());
					context.startActivity(intent);
				}
			});
			imageLoader(imageView, item.getMovie_image());
			
			rowViews.put(position, rowView);
		}
		return rowView;
	}
	
	public void imageLoader(ImageView image, String url) {

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(0).showImageForEmptyUri(0).showImageOnFail(0)
				.resetViewBeforeLoading(false) // default
				.delayBeforeLoading(1000).cacheInMemory(true) // default
				.cacheOnDisc(true) // default
				// .preProcessor(...)
				// .postProcessor(...)
				// .extraForDownloader(...)
				.imageScaleType(ImageScaleType.EXACTLY) // default
				.bitmapConfig(Bitmap.Config.ARGB_8888) // default
				// .decodingOptions(...)
				.displayer(new SimpleBitmapDisplayer()) // default
				.handler(new Handler()) // default
				.build();

		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
				// null)
				.threadPoolSize(3)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 1)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
				// default
				.discCache(new UnlimitedDiscCache(cacheDir))
				// default
				.discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				// .imageDownloader(new BaseImageDownloader(context)) // default
				// .imageDecoder(new BaseImageDecoder()) // default
				.defaultDisplayImageOptions(options) // default
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);

		ImageLoader.getInstance().displayImage(url, image);
	}
}
