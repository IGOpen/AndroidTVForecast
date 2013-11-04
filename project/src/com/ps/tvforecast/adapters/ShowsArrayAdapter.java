package com.ps.tvforecast.adapters;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ps.tvforecast.R;
import com.ps.tvforecast.models.ShowInfo;

public class ShowsArrayAdapter extends ArrayAdapter<ShowInfo> implements StickyListHeadersAdapter {
    
	public ShowsArrayAdapter(Context context, List<ShowInfo> showResults) {
	    super(context, android.R.layout.simple_list_item_1, showResults);
	}
	
	public void update(ShowInfo showInfo) {
		ShowInfo match = getShowInfo(showInfo.getId());
		Log.d("DEBUG", "After update in ShowsArrayAdapter");
		if(match !=null) {
			match.updateShowInfo(showInfo);
			Log.d("DEBUG", "After match in update "+ match.getAsString());
			this.notifyDataSetChanged();
		}
	}
	
	public void delete(String id) {
        ShowInfo match = getShowInfo(id);
        Log.d("DEBUG", "After remove in ShowsArrayAdapter");
        if(match !=null) {
            this.remove(match);
            Log.d("DEBUG", "After match in remove "+ match.getAsString());
        }
    }
	
	public ShowInfo getShowInfo(String id) {
		Log.d("DEBUG", "ShowsArrayAdapter getCount" + this.getCount());
		for(int i=0; i < this.getCount(); i++) {
			ShowInfo curItem = getItem(i);
			Log.d("DEBUG", "ShowsArrayAdapter curItemId" + curItem.getId() + "looking for " + id);
			if(curItem.getId().equals(id)) {
				return curItem;
			}
		}
		return null;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ShowInfo showInfo = this.getItem(position);
		ItemViewHolder holder = null;
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.show_item, null, false);
            holder = new ItemViewHolder();
            holder.tvShowName = (TextView) convertView.findViewById(R.id.tvShowName);
            holder.tvEpisodeTitle = (TextView) convertView.findViewById(R.id.tvEpisodeTitle);
            holder.tvEpisodeDate = (TextView) convertView.findViewById(R.id.tvEpisodeDate);
            holder.tvEpisodeTime = (TextView) convertView.findViewById(R.id.tvEpisodeTime);
            convertView.setTag(holder);
		}
		else {
		    holder = (ItemViewHolder) convertView.getTag();
		}
		
		holder.tvShowName.setText(showInfo.getName());
		holder.tvEpisodeTitle.setText(showInfo.getNextEpisodeTitle());
		holder.tvEpisodeDate.setText(showInfo.getNextEpisodeDate());
		holder.tvEpisodeTime.setText(showInfo.getNextEpisodeTime());
		
        return convertView;
	}

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.getItem(position).getId());
    }
	
    @Override
    public long getHeaderId(int position) {
        return this.getItem(position).getName().subSequence(0, 1).charAt(0);
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        ShowInfo showInfo = this.getItem(position);
        HeaderViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.show_list_header, null, false);
            holder = new HeaderViewHolder();
            holder.tvShowListHeader = (TextView) convertView.findViewById(R.id.tvShowListHeader);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        
        holder.tvShowListHeader.setText(showInfo.getName());
        return convertView;
    }
    
    class HeaderViewHolder {
        TextView tvShowListHeader = null;
    }
    
    class ItemViewHolder {
        TextView tvShowName = null;
        TextView tvEpisodeTitle = null;
        TextView tvEpisodeDate = null;
        TextView tvEpisodeTime = null;
    }
 
}
