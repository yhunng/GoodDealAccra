package com.sleekjob.gooddealaccra;



import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Deal> deals;

    public DealAdapter(Context context, ArrayList<Deal> deals) {
        this.context = context;
        this.deals = deals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view, parent, false);
        final ViewHolder holder = new ViewHolder(view);


        return holder;
    }

    public float disc(float old, float fresh){

        float sum = old - fresh;
        float divs = sum / old;
        float  fin = divs * 100;
        return Math.round(fin);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Deal deal = deals.get(position);
        //float disc = (Float.valueOf(deal.discount)/Float.valueOf(deal.price)) * 100;
        float ori = disc(Float.valueOf(deal.price), Float.valueOf(deal.discount));
        //double ori = new BigDecimal(disc).setScale(2, RoundingMode.HALF_UP).doubleValue();
        holder.mCardTitle.setText(deal.title);
        holder.mCardLocation.setText(deal.location);
        holder.mCardDiscount.setText(String.valueOf(ori));
        holder.mCardPrice.setText(deal.discount);
        holder.oPrice.setText(deal.price);
        holder.mCardContact.setText(deal.contact);
        holder.mCardDescription.setText(deal.description);
        String url = "http://gooddealaccra.sleekjob.com/images/" + deal.image;
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.load)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.mCardImage);


        holder.mCardDescription.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false;
            @Override
            public void onClick(View v) {
                if(isClicked){
                    //This will shrink textview to 2 lines if it is expanded.
                    holder.mCardDescription.setMaxLines(2);
                    holder.more.setVisibility(View.VISIBLE);
                    holder.mCardInvisible.setVisibility(View.GONE);
                    isClicked = false;
                } else {
                    //This will expand the textview if it is of 2 lines
                    holder.mCardDescription.setMaxLines(Integer.MAX_VALUE);
                    holder.more.setVisibility(View.GONE);
                    holder.mCardInvisible.setVisibility(View.VISIBLE);
                    isClicked = true;
                }
            }
        });

        holder.oPTag.setPaintFlags(holder.oPTag.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.oPrice.setPaintFlags(holder.oPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    public void addAll(List data){
        deals.addAll(data);
        notifyDataSetChanged();
    }

    public void clearAll() {
        deals.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (deals != null) {
            return deals.size();
        }else{
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mCardTitle;
        private ImageView mCardImage;
        private TextView mCardLocation;
        private TextView mCardDescription;
        private TextView mCardContact;
        private TextView mCardPrice, oPrice, oPTag;
        private TextView mCardDiscount, more;
        private LinearLayout mCardInvisible;

        private ViewHolder(View itemView) {
            super(itemView);
            mCardTitle = (TextView) itemView.findViewById(R.id.mCardTitle);
            mCardImage = (ImageView) itemView.findViewById(R.id.mCardImage);
            mCardLocation = (TextView) itemView.findViewById(R.id.mCardLocation);
            mCardDescription = (TextView) itemView.findViewById(R.id.mCardDescription);
            mCardContact = (TextView) itemView.findViewById(R.id.mCardContact);
            mCardPrice = (TextView) itemView.findViewById(R.id.mCardPrice);
            mCardDiscount = (TextView) itemView.findViewById(R.id.mCardDiscount);
            oPrice = (TextView) itemView.findViewById(R.id.oPrice);
            oPTag = (TextView) itemView.findViewById(R.id.oPTag);
            more = (TextView) itemView.findViewById(R.id.mCardMore);
            mCardInvisible = (LinearLayout) itemView.findViewById(R.id.mCardInvisible);

        }


    }
}
