package ac.jfa.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ac.jfa.R;
import ac.jfa.modal.NoticeItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoticeItemAdapter extends BaseAdapter{

	private List<NoticeItem> info = null;
	private Map<Integer, View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
	
	public NoticeItemAdapter(List<NoticeItem> info, Context context) {
		// TODO Auto-generated constructor stub
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
			NoticeItem item = (NoticeItem)getItem(position);
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			rowView = layoutInflater.inflate(R.layout.noticeitem, null);
			TextView date = (TextView)rowView.findViewById(R.id.date);
			TextView content = (TextView)rowView.findViewById(R.id.content);
			date.setText(item.getSend_date());
			content.setText(item.getTitle());
		}
		return rowView;
	}

}
