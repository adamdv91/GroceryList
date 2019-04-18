package com.adamdevilliers.shopping.grocerylist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adamdevilliers.shopping.grocerylist.R;
import com.adamdevilliers.shopping.grocerylist.model.GroceryList;

import io.realm.RealmChangeListener;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.MyItemAdapterViewHolder> implements RealmChangeListener {

    private GroceryList mGroceryList;

    private Context context;

    private ItemClickedListener mItemClickListener;

    public ItemsListAdapter(GroceryList mGroceryList, Context context) {
        this.mGroceryList = mGroceryList;
        this.context = context;
        mGroceryList.addChangeListener(this);
    }

    public void setOnItemClickListener(ItemClickedListener clickListener) {
        this.mItemClickListener = clickListener;
    }

    @NonNull
    @Override
    public MyItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyItemAdapterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grocery_items_view, viewGroup, false), mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemAdapterViewHolder myGroceryAdapterViewHolder, int i) {
        myGroceryAdapterViewHolder.name.setText(mGroceryList.getItems().get(i).getName());
        myGroceryAdapterViewHolder.quantity.setText(String.format(context.getResources().getString(R.string.quality_placeholder), mGroceryList.getItems().get(i).getQuantity()));

        if(mGroceryList.getItems().get(i).getPaid()){
            myGroceryAdapterViewHolder.name.setTextColor(context.getResources().getColor(R.color.colorFadeOut));
            myGroceryAdapterViewHolder.name.setAlpha(0.5f);
            myGroceryAdapterViewHolder.quantity.setTextColor(context.getResources().getColor(R.color.colorFadeOut));
            myGroceryAdapterViewHolder.quantity.setAlpha(0.5f);
        }

    }

    @Override
    public int getItemCount() {
        return mGroceryList.getItems().size();
    }

    @Override
    public void onChange() {

    }

    class MyItemAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemClickedListener itemClickedListener;

        TextView name;
        TextView quantity;
        LinearLayout linear;


        MyItemAdapterViewHolder(@NonNull View itemView, ItemClickedListener mItemClickedListener) {
            super(itemView);

            name = itemView.findViewById(R.id.display_item_name);
            quantity = itemView.findViewById(R.id.display_item_quantity);

            linear = itemView.findViewById(R.id.linear_for_item);

            itemClickedListener = mItemClickedListener;

            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickedListener != null) {
                        itemClickedListener.onItemClicked(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface ItemClickedListener {
        void onItemClicked(int position);
    }
}
