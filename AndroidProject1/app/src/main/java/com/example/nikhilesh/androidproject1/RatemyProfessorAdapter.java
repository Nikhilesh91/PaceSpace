package com.example.nikhilesh.androidproject1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RatemyProfessorAdapter extends BaseAdapter {

    Context mContext;
    List<Professor> mNamesList;

    RatemyProfessorAdapter(Context context, List<Professor> exampleObjectList) {
        mContext = context;
        mNamesList = exampleObjectList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_item, null);
            ViewHolder viewHolder = new ViewHolder((TextView) convertView.findViewById(R.id.text));
            convertView.setTag(viewHolder);
        }
        Professor object = (Professor) getItem(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mTextView.setText("Professor :"+ " "+object.profName);
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

    static class ViewHolder {
        TextView mTextView;
        ViewHolder(TextView textView) {
            mTextView = textView;
        }

    }
}
