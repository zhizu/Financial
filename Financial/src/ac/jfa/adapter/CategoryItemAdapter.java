package ac.jfa.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ac.jfa.R;
import ac.jfa.modal.CategoryItem;
import ac.jfa.modal.NewsItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CategoryItemAdapter extends BaseAdapter{

	private List<CategoryItem> info = null;
	private Map<Integer, View> rowViews = new HashMap<Integer, View>();
	private Context context = null;
    private CategoryItem item;
	
	public CategoryItemAdapter(List<CategoryItem> info, Context context) {
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
			rowView = layoutInflater.inflate(R.layout.categoryitem, null);
			TextView categoryItem = (TextView)rowView.findViewById(R.id.Category);
            RelativeLayout CategoryLayout = (RelativeLayout)rowView.findViewById(R.id.CategoryLayout);
			item = (CategoryItem) getItem(position);
			if(position == 0){
				CategoryLayout.setBackgroundResource(R.drawable.ic_preference_first_normal);
			}else if(position == info.size()-1){
				CategoryLayout.setBackgroundResource(R.drawable.ic_preference_last_normal);
			}
			categoryItem.setText(item.getMovie_category_name());
			rowViews.put(position, rowView);
		}
		return rowView;
	}

}
