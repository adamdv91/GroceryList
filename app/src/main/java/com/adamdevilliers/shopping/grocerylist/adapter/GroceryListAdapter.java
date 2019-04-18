package com.adamdevilliers.shopping.grocerylist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adamdevilliers.shopping.grocerylist.R;
import com.adamdevilliers.shopping.grocerylist.model.GroceryList;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.MyGroceryAdapterViewHolder> implements RealmChangeListener {

    private Context context;

    private RealmResults<GroceryList> mGroceryList;

    private GroceryClickedListener mItemClickListener;

    public GroceryListAdapter(RealmResults<GroceryList> mGroceryList, Context context) {
        this.mGroceryList = mGroceryList;
        this.context = context;
        mGroceryList.addChangeListener(this);
    }

    public void setOnItemClickListener(GroceryClickedListener clickListener) {
        this.mItemClickListener = clickListener;
    }

    @NonNull
    @Override
    public GroceryListAdapter.MyGroceryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GroceryListAdapter.MyGroceryAdapterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grocery_view, viewGroup, false), mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryListAdapter.MyGroceryAdapterViewHolder myGroceryAdapterViewHolder, int i) {
        myGroceryAdapterViewHolder.textView.setText(mGroceryList.get(i).getName());

        if (mGroceryList.get(i).getItems() != null && mGroceryList.get(i).getItems().size() > 0 && mGroceryList.get(i).getValid()) {

            boolean areAllBought = true;

            for (int j = 0; j < mGroceryList.get(i).getItems().size(); j++) {
                if (!mGroceryList.get(i).getItems().get(j).getPaid()) {
                    areAllBought = false;
                    break;
                }
            }

            if (areAllBought) {
                myGroceryAdapterViewHolder.textView.setTextColor(context.getResources().getColor(R.color.colorFadeOut));
                myGroceryAdapterViewHolder.textView.setAlpha(0.5f);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mGroceryList.size();
    }

    @Override
    public void onChange() {
        notifyDataSetChanged();
    }

    class MyGroceryAdapterViewHolder extends RecyclerView.ViewHolder {

        GroceryClickedListener mItemClickListener;

        TextView textView;

        MyGroceryAdapterViewHolder(@NonNull View itemView, GroceryClickedListener itemClickListener) {
            super(itemView);

            textView = itemView.findViewById(R.id.grocery_list_name);

            this.mItemClickListener = itemClickListener;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onGroceryClicked(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface GroceryClickedListener {
        void onGroceryClicked(int position);
    }
}
