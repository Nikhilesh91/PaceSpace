package com.example.nikhilesh.androidproject1;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ViewCourseAdapter extends BaseAdapter {

    Context mContext;
    List<ViewCourse> mNamesList;
    String CourseName ="Course Name : ";

    ViewCourseAdapter(Context context, List<ViewCourse> namesList) {
        mContext = context;
        mNamesList = namesList;
    }


    //get view method
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.viewcourse_item, null); // inflates the viewcourseitem xml
            ViewHolder viewHolder = new ViewHolder((TextView) convertView.findViewById(R.id.nameCourse));
            convertView.setTag(viewHolder);
        }
        ViewCourse object = (ViewCourse) getItem(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        String name = CourseName+object.courseName;
        viewHolder.mCourseName.setText(name); // puts the data into listview
        // viewHolder.mRating.setText((CharSequence) object.ratings);
        return convertView;
    }


    //gets the count
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

    //viewholder class for courseName
    static class ViewHolder {
        TextView mCourseName;

        ViewHolder(TextView mCourseName) {
            this.mCourseName = mCourseName;
        }
    }
}

