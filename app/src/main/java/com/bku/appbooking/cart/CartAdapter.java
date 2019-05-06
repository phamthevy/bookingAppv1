package com.bku.appbooking.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bku.appbooking.R;
import com.bku.appbooking.common.InCartProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter  extends BaseAdapter {
    private List<InCartProduct> inCartProductList;
    private Context context;
    private LayoutInflater layoutInflater;

    public CartAdapter(Context context, List<InCartProduct>inCartProductList){
        this.context =  context;
        this.inCartProductList = inCartProductList;
        layoutInflater =  LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return inCartProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return inCartProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_cart, null);
            holder = new ViewHolder();
            holder.imgView = (ImageView) convertView.findViewById(R.id.image);
            holder.titleView = (TextView) convertView.findViewById(R.id.title);
            holder.priceView = (TextView) convertView.findViewById(R.id.price);
            holder.desView = (TextView) convertView.findViewById(R.id.shortDes);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InCartProduct inCartProduct = this.inCartProductList.get(position);
        ImageView imageViewCoverArt = (ImageView)convertView.findViewById(R.id.image);
        holder.titleView.setText(inCartProduct.getProduct().getTitle());
        holder.priceView.setText(inCartProduct.getProduct().getPrice());

        Picasso.with(context).load(inCartProduct.getProduct().getImageUrl()).into(holder.imgView);

        return convertView;
    }
    static class ViewHolder {
        ImageView imgView;
        TextView titleView;
        TextView priceView;
        TextView desView;
    }

}
