package com.sleekjob.gooddealaccra;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Deal deal = deals.get(position);
        holder.mCardTitle.setText(deal.title);
        holder.mCardLocation.setText(deal.location);
        holder.mCardDiscount.setText(String.valueOf(deal.discount));
        holder.mCardPrice.setText(deal.price);
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
        private TextView mCardPrice;
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
            more = (TextView) itemView.findViewById(R.id.mCardMore);
            mCardInvisible = (LinearLayout) itemView.findViewById(R.id.mCardInvisible);

        }


    }
}
