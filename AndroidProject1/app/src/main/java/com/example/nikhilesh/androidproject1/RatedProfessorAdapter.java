package com.example.nikhilesh.androidproject1;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class RatedProfessorAdapter extends BaseAdapter {

    Context mContext;
    String description ="Description :  ";
    List<RatedProfessor> mNamesList;
    //rated professor adapter constructor
     RatedProfessorAdapter(Context context, List<RatedProfessor> exampleObjectList) {
        mContext = context;
        mNamesList = exampleObjectList;
    }

    @Override
    //get view method
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.ratedlist_item, null);
            ViewHolder viewHolder = new ViewHolder((TextView) convertView.findViewById(R.id.ratedDescription));
            convertView.setTag(viewHolder);
        }
        RatedProfessor object = (RatedProfessor) getItem(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        //sets the text of description here
        viewHolder.mDesc.setText(description+object.ratedDescription);
        //viewHolder.mRating.setText("Subject :" + " " + object.subject);
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

        ViewHolder(TextView mDesc) {
            this.mDesc = mDesc;
        }
    }
}
