package com.bku.appbooking.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        final ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_cart, null);
            holder = new ViewHolder();
            holder.imImage = (ImageView) convertView.findViewById(R.id.imImage);
            holder.txTitle = (TextView) convertView.findViewById(R.id.txTitle);
            holder.txPrice = (TextView) convertView.findViewById(R.id.txPrice);
            holder.txShortDes = (TextView) convertView.findViewById(R.id.txShortDes);
            holder.txNum = (TextView) convertView.findViewById(R.id.txNum);
            holder.btnCart = (CheckBox) convertView.findViewById(R.id.btnCart) ;
            holder.btnSub = (Button) convertView.findViewById(R.id.btnSub) ;
            holder.btnAdd = (Button) convertView.findViewById(R.id.btnAdd);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InCartProduct inCartProduct = this.inCartProductList.get(position);
        holder.txTitle.setText(inCartProduct.getProduct().getTitle());
        holder.txPrice.setText(inCartProduct.getProduct().getPrice());
        holder.txShortDes.setText(inCartProduct.getProduct().getShortDescription());
        holder.txNum.setText(String.valueOf(inCartProduct.getNum()));
        Picasso.with(context).load(inCartProduct.getProduct().getImageUrl()).into(holder.imImage);
        holder.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Check RadioButton", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf((String) holder.txNum.getText());
                if (num==1){
                    holder.btnSub.setEnabled(false);
                }
                else {
                    num -= 1;
                    holder.txNum.setText(String.valueOf(String.valueOf(num)));
                }

            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf((String) holder.txNum.getText());
                num += 1;
                holder.txNum.setText(String.valueOf(String.valueOf(num)));
                holder.btnSub.setEnabled(true);
            }
        });
        return convertView;
    }


    static class ViewHolder {
        ImageView imImage;
        TextView txTitle;
        TextView txPrice;
        TextView txShortDes;
        TextView txNum;
        CheckBox btnCart;
        Button btnSub;
        Button btnAdd;
    }

}
