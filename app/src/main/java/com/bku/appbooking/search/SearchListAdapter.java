package com.bku.appbooking.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bku.appbooking.R;
import com.bku.appbooking.common.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Product> products;
    private SearchDataReceiver searchDataReceiver;

    // 1
    public SearchListAdapter(Context context, SearchDataReceiver searchDataReceiver) {
        this.searchDataReceiver = searchDataReceiver;
        searchDataReceiver.setLinkedAdapter(this);
        this.context = context;
        this.products = searchDataReceiver.getProducts();
    }

    // 2
    @Override
    public int getCount() {
        return products.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return products.get(position).getId();
    }

    // 4
    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = products.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.layout_product, null);

            ImageView imageViewCoverArt = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
            TextView titleTv = (TextView)convertView.findViewById(R.id.tv_title);
            TextView priceTv = (TextView)convertView.findViewById(R.id.tv_price);
            TextView bonusTv = (TextView)convertView.findViewById(R.id.tv_bonus);

            SearchListAdapter.ViewHolder viewHolder = new SearchListAdapter.ViewHolder(titleTv, priceTv, bonusTv, imageViewCoverArt);
            convertView.setTag(viewHolder);
        }
        final SearchListAdapter.ViewHolder viewHolder = (SearchListAdapter.ViewHolder)convertView.getTag();
//    viewHolder.imageViewCoverArt.setImageResource(book.getImageResource());
        viewHolder.titleTv.setText(product.getTitle());
        viewHolder.priceTv.setText(product.getPrice());
        viewHolder.bonusTv.setText(String.valueOf(position));

        Picasso.with(context).load(product.getImageUrl()).into(viewHolder.imageViewCoverArt);

        return convertView;
    }

    public void updateNextPage(){
        searchDataReceiver.updateNextPage();
    }

    private class ViewHolder {
        private TextView titleTv;
        private TextView priceTv;
        private TextView bonusTv;
        private ImageView imageViewCoverArt;

        public ViewHolder(TextView titleTv, TextView priceTv, TextView bonusTv, ImageView imageViewCoverArt) {
            this.titleTv = titleTv;
            this.priceTv = priceTv;
            this.bonusTv = bonusTv;
            this.imageViewCoverArt = imageViewCoverArt;
        }
    }
}
