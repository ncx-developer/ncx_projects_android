package com.ncx.dms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ncx.dms.R;
import com.ncx.dms.core.CommonHelper;
import com.ncx.dms.remote.dto.McServiceDetail;

import java.util.ArrayList;

public class SparePartItemListViewAdapter extends ArrayAdapter<McServiceDetail> implements View.OnClickListener{

    ArrayList<McServiceDetail> mcServiceDetails;
    Context context;

    public SparePartItemListViewAdapter(@NonNull Context context,  ArrayList<McServiceDetail> mcServiceDetails) {

        super(context, R.layout.custom_list_item, R.id.tvPartNo, mcServiceDetails);
        this.context = context;
        this.mcServiceDetails = mcServiceDetails;
    }

    private OnClickListener onClickListener;

    public interface OnClickListener{
        void onEdit(int position);

        void onDelete(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        /*Object object= getItem(position);
        McServiceDetail mcServiceDetail=(McServiceDetail)object;
        mcServiceDetail.setUnitPrice(1000);

        add(mcServiceDetail);*/
        switch (v.getId()){
            case R.id.ivEdit:
                if (onClickListener != null)
                    onClickListener.onEdit(position);
                break;
            case R.id.ivDelete:
                if (onClickListener != null)
                    onClickListener.onDelete(position);
                break;
        }
    }

    // View lookup cache
    private static class ViewHolder {
        TextView tvPartNo;
        TextView tvDescription;
        TextView tvPrice;
        TextView tvQuantity;
        TextView tvDiscountPercentage;
        TextView tvGrossAmount;
        TextView tvNetAmount;

        ImageView ivEdit;
        ImageView ivDelete;
    }

    // private int lastPosition = -1;

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //McServiceDetail m = mcServiceDetails.get(0);
        McServiceDetail m = getItem(position);

        ViewHolder viewHolder;
        //final View result;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);//(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_list_item, parent, false);
            viewHolder.tvPartNo = convertView.findViewById(R.id.tvPartNo);
            viewHolder.tvDescription = convertView.findViewById(R.id.tvDescription);
            viewHolder.tvPrice = convertView.findViewById(R.id.tvPrice);
            viewHolder.tvQuantity = convertView.findViewById(R.id.tvQuantity);
            viewHolder.tvDiscountPercentage = convertView.findViewById(R.id.tvDiscountPercentage);
            viewHolder.tvGrossAmount = convertView.findViewById(R.id.tvGrossAmount);
            viewHolder.tvNetAmount = convertView.findViewById(R.id.tvNetAmount);
            viewHolder.ivEdit = convertView.findViewById(R.id.ivEdit);
            viewHolder.ivDelete = convertView.findViewById(R.id.ivDelete);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

       /* result = convertView;
        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;*/

        viewHolder.tvPartNo.setText(m.getPartNo());
        viewHolder.tvDescription.setText(m.getFullDescription());
        viewHolder.tvPrice.setText(CommonHelper.getCurrencyFormatString(m.getUnitPrice()));
        viewHolder.tvQuantity.setText(CommonHelper.getNumberFormatString(m.getQuantity()));
        viewHolder.tvDiscountPercentage.setText(CommonHelper.getCurrencyFormatString(m.getDiscountPercentage()));
        viewHolder.tvGrossAmount.setText(CommonHelper.getCurrencyFormatString(m.getGrossAmount()));
        viewHolder.tvNetAmount.setText(CommonHelper.getCurrencyFormatString(m.getNetAmount()));

        viewHolder.ivEdit.setOnClickListener(this);
        viewHolder.ivDelete.setOnClickListener(this);
        viewHolder.ivEdit.setTag(position);
        viewHolder.ivDelete.setTag(position);
        return convertView;
    }
}
