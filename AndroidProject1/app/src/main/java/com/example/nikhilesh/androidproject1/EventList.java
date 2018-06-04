package com.example.nikhilesh.androidproject1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

// this is the adapter class that ectends from the base class
// it is a custom listview used in the EventActivity
public class EventList extends BaseAdapter {

    // member declaration

    Context mContext;
    List<EventInfo> mNamesList;
    String title= "Title : ";
    String description ="Description : ";
    // parameterized constructor

    public EventList(Context context, List<EventInfo> exampleObjectList) {
        mContext = context;
        mNamesList = exampleObjectList;
    }

    // implement the methods

    @Override
    public int getCount() {
        return mNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.event_row_object, null);
            EventViewHolder viewHolder = new EventViewHolder((TextView) convertView.findViewById(R.id.EventtxtTitle),
                    (TextView) convertView.findViewById(R.id.EventtxtDescription));
            convertView.setTag(viewHolder);
        }

        EventInfo object = (EventInfo) getItem(position);
        EventList.EventViewHolder viewHolder = (EventViewHolder) convertView.getTag();


        viewHolder.mTextViewTitle.setText(title + object.getEventtitle());
        viewHolder.mTextDescription.setText(description + object.getEventdescription());
        return convertView;
    }

    // view holder class to save the event info values

    static class EventViewHolder {
        TextView mTextViewTitle;
        TextView mTextDescription;


        EventViewHolder(TextView title, TextView description) {
            mTextViewTitle = title;
            mTextDescription = description;

        }

    }
}
