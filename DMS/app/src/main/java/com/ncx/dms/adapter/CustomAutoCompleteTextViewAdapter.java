package com.ncx.dms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ncx.dms.R;
import com.ncx.dms.remote.dto.McServiceDetail;
import com.ncx.dms.remote.dto.McSparePart;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CustomAutoCompleteTextViewAdapter extends ArrayAdapter<McSparePart>{
    Context context;
    List<McSparePart> dataSet;

    public CustomAutoCompleteTextViewAdapter(Context context, List<McSparePart> dataSet){

        super(context, 0, dataSet);
        this.context = context;
        this.dataSet = new ArrayList<>(dataSet);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return partNoFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        McSparePart mcSparePart = getItem(position);

        ViewHolder viewHolder;
        //final View result;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);//(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.customer_list_item_code_description, parent, false);
            viewHolder.tvCode = convertView.findViewById(R.id.tvCode);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tvCode.setText(mcSparePart.getPartNo());
        return convertView;
    }

    public McSparePart getItemByCode(String partNo){
        partNo =  partNo.toLowerCase();

        McSparePart mcSparePart = null;
        for(McSparePart item : dataSet){
            if (item.getPartNo().equalsIgnoreCase(partNo)){
                mcSparePart = item;
                break;
            }
        }
        return  mcSparePart;
    }

    private Filter partNoFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<McSparePart> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(dataSet);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (McSparePart item : dataSet) {
                    if (item.getPartNo().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((McSparePart) resultValue).getPartNo();
        }
    };

    private static class ViewHolder{
        TextView tvCode;
    }
}
