package ac.jfa.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ac.jfa.R;
import ac.jfa.modal.RelateItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RelateAdapter extends BaseAdapter{
	private List<RelateItem> info = null;
	private Map<Integer, View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	public RelateAdapter(List<RelateItem> info, Context context) {
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
			rowView = layoutInflater.inflate(R.layout.relate, null);
			ImageView content_img = (ImageView)rowView.findViewById(R.id.content_img);
			TextView contentText = (TextView)rowView.findViewById(R.id.contentText);
			RelateItem item = (RelateItem) getItem(position);
			if(item.getNumber() == 1){
				content_img.setBackgroundResource(R.drawable.bnr_mailmagazine);
			}else if(item.getNumber() == 2){
				content_img.setBackgroundResource(R.drawable.bnr_moneylog);
			}else if(item.getNumber() == 3){
				content_img.setBackgroundResource(R.drawable.bnr_management);
			}else if(item.getNumber() == 4){
				content_img.setBackgroundResource(R.drawable.bnr_accum);
			}else if(item.getNumber() == 5){
				content_img.setBackgroundResource(R.drawable.bnr_semistyle);
			}
			contentText.setText(item.getName());
			rowViews.put(position, rowView);
		}
		return rowView;
	}
}
