package ac.jfa.adapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ac.jfa.R;
import ac.jfa.SettingFacebookActivity;
import ac.jfa.modal.NewsItem;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.service.textservice.SpellCheckerService.Session;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
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

public class NewsItemAdapter extends BaseAdapter {

	private List<NewsItem> info = null;
	private Map<Integer, View> rowViews = new HashMap<Integer, View>();
	private Context context = null;

	public NewsItemAdapter(List<NewsItem> info, Context context) {
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
			rowView = layoutInflater.inflate(R.layout.newsitem, null);
			TextView content_title = (TextView) rowView
					.findViewById(R.id.content_title);
			TextView content = (TextView) rowView.findViewById(R.id.content);
			ImageView imageView = (ImageView) rowView
					.findViewById(R.id.content_img);

			ImageView logoImage = (ImageView) rowView
					.findViewById(R.id.logoImage);

			TextView content_grp_name = (TextView) rowView
					.findViewById(R.id.content_grp_name);

			TextView date = (TextView) rowView.findViewById(R.id.date);
			ImageView shareImage = (ImageView) rowView
					.findViewById(R.id.contentImage);

			final NewsItem item = (NewsItem) getItem(position);

			if (item.getFeed_type().equals("2")) {
				content_grp_name.setText("ファイナンシャルマガジン");
				logoImage.setImageResource(R.drawable.fm_logo);
			} else {
				content_grp_name.setText("ファイナンシャルアカデミー");
				logoImage.setImageResource(R.drawable.fa_logo);
			}

			content_title.setText(item.getContent_title());
			if (item.getContent_detail().equals("")) {
				content.setVisibility(View.GONE);
			} else {
				content.setText(item.getContent_detail());
			}

			// content_grp_name.setText(item.get)
			date.setText(item.getContent_date());
			imageLoader(imageView, item.getContent_img());
			shareImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDialog_Layout(context);
				}
			});

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

	private void showDialog_Layout(final Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.share, null);
		TextView faceBookImageView = (TextView) view
				.findViewById(R.id.faceBook);
		faceBookImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(context, SettingFacebookActivity.class);
                context.startActivity(intent);
			}
		});
		TextView twitterImageView = (TextView) view.findViewById(R.id.twitter);
		TextView mailImageView = (TextView) view.findViewById(R.id.mail);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		final Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.setContentView(view);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.LEFT | Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		window.setWindowAnimations(R.style.mystyle); // 添加动画
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		// lp.alpha = 0.9f;
		dialog.getWindow().setLayout(720, 370);

	}
}
