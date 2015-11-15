package llvc.sonicxm.tracklist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import llvc.sonicxm.R;

public class CustomListViewAdapter extends ArrayAdapter<TrackItem> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<TrackItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView trackTitle;
        TextView artist;
        ImageView downloadImageView;
        ImageView plusImageView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        TrackItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.track_cell, null);

            holder = new ViewHolder();
            holder.trackTitle = (TextView) convertView.findViewById(R.id.trackTitle);
            holder.artist = (TextView) convertView.findViewById(R.id.artist);
            holder.downloadImageView = (ImageView) convertView.findViewById(R.id.downloadButton);
            holder.plusImageView = (ImageView) convertView.findViewById(R.id.plusButton);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.trackTitle.setText(rowItem.trackTitle);
        holder.artist.setText(rowItem.artist);
        if (rowItem.canDownload) {
            holder.downloadImageView.setVisibility(View.VISIBLE);
        } else {
            holder.downloadImageView.setVisibility(View.INVISIBLE);
        }

        if (position%2 == 0) {
            convertView.setBackgroundResource(R.color.light_gray);
        } else {
            convertView.setBackgroundResource(R.color.thick_gray);
        }

        convertView.setMinimumHeight(120);

        return convertView;
    }
}