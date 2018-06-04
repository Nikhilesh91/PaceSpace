package com.example.nikhilesh.androidproject1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

// this is the custom adapter class that extends base adapter

public class JobOpeningList extends BaseAdapter{

    // variable declaration

    Context mContext;
    List<JobOpeningInfo> mNamesList;

    // parameterized constructor

    public JobOpeningList(Context context, List<JobOpeningInfo> exampleObjectList) {
        mContext = context;
        mNamesList = exampleObjectList;
    }

    // implement the methods in baseadapter

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
            convertView = View.inflate(mContext, R.layout.job_openings_row_object, null);
            ViewHolder viewHolder = new ViewHolder((TextView) convertView.findViewById(R.id.jobOpeningtxtTitle),
                    (TextView) convertView.findViewById(R.id.jobOpeningtxtQUalifications),
                    (TextView) convertView.findViewById(R.id.jobOpeningtxtContact));
            convertView.setTag(viewHolder);
        }

        JobOpeningInfo object = (JobOpeningInfo) getItem(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.mTextViewTitle.setText(object.getTitle());
        viewHolder.mTextViewQualification.setText(object.getQualification());
        viewHolder.mTextViewContact.setText(object.getContact());

        return convertView;
    }

    // view holder class for list

    static class ViewHolder {
        TextView mTextViewTitle;
        TextView mTextViewQualification;
        TextView mTextViewContact;

        ViewHolder(TextView title, TextView qualification , TextView contact) {
            mTextViewTitle = title;
            mTextViewQualification = qualification;
            mTextViewContact = contact;
        }

    }
}
