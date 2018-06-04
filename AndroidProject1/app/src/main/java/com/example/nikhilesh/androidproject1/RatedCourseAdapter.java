package com.example.nikhilesh.androidproject1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//code for listview
public class RatedCourseAdapter extends BaseAdapter {
    Context mContext;
    List<RatedCourse> mNamesList;
  //constructor for ratedCourse
    RatedCourseAdapter(Context context, List<RatedCourse> exampleObjectList) {
        mContext = context;
        mNamesList = exampleObjectList;
    }
  //get view method for listview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.rated_course_listitem, null);// inflates the xml here
            //viewholder sets the data
            ViewHolder viewHolder = new ViewHolder((TextView) convertView.findViewById(R.id.courseratedDescription),
                    (TextView) convertView.findViewById(R.id.professorName));
            convertView.setTag(viewHolder);
        }
        RatedCourse object = (RatedCourse) getItem(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mDesc.setText("Description :" + " " + object.ratedDescription); // puts the description into viewholder
        //viewHolder.mRating.setText("Ratings :" + " " + object.ratings);
        //viewHolder.profName.setText("Professor :" + " " + object.profName);
        return convertView;
    }

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

    //viewholder class
    static class ViewHolder {
        TextView mDesc;
        TextView profName;

        ViewHolder(TextView mDesc,TextView profName) {
            this.mDesc = mDesc;
            this.profName=profName;
        }
    }

}