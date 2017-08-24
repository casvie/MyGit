package com.longrise.sharelib.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longrise.sharelib.R;
import com.longrise.sharelib.model.TypeBean;

import java.util.List;

public class ShareAdapter extends BaseAdapter {

    private List<TypeBean> dataLists;

    private Context context;

    public ShareAdapter(Context context) {
        this.context = context;
    }

    public void setDataLists(List<TypeBean> dataLists) {
        this.dataLists = dataLists;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (dataLists != null) {
            return dataLists.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.umeng_adapter_share_item, null);
            viewHolder.msgTV = (TextView) convertView.findViewById(R.id.umeng_item_tv_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TypeBean data = dataLists.get(position);
        viewHolder.msgTV.setText(data.getInfo());
        // 显示图标
        Drawable drawable = context.getResources().getDrawable(data.getRedid());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        viewHolder.msgTV.setCompoundDrawables(null, drawable, null, null);
        return convertView;
    }

    private class ViewHolder {
        private TextView msgTV;
    }
}
