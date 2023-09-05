package com.example.myprojectshop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprojectshop.R;
import com.example.myprojectshop.lists.Price;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder> {
    private List<Price> prices;
    private Context context;
    private String stillShop;
    private int stillColor;
    private int witchPrice;
    private OnPriceDellListener onPriceDellListener;
    private OnPriceChangeListener onPriceChangeListener;

    public interface OnPriceDellListener{
        void OnPriceDell(String id, int positionId);
    }
    public interface OnPriceChangeListener{
        void OnPriceChange(String id, int positionId);
    }

    public void setOnPriceDellListener(OnPriceDellListener onPriceDellListener) {
        this.onPriceDellListener = onPriceDellListener;
    }

    public void setOnPriceChangeListener(OnPriceChangeListener onPriceChangeListener) {
        this.onPriceChangeListener = onPriceChangeListener;
    }

    public PriceAdapter(List<Price> prices, int witchPrice, String stillShop, int stillColor) {
        this.prices = prices; this.witchPrice = witchPrice; this.stillShop = stillShop; this.stillColor = stillColor;
    }


    @NonNull
    @Override
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prise_item, parent, false);
        return new PriceViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull PriceViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.i("vhgfvhgnfbvh", "lll"+witchPrice);
        holder.constraintLayout.setLayoutParams(new ViewGroup.LayoutParams(witchPrice, ViewGroup.LayoutParams.MATCH_PARENT));
      String imageOff = "image";
        Price price = prices.get(position);
        String sail = "" + price.getCostSail();
        String cost = String.format("%.2f", price.getCost());
        String costSail = String.format("%.2f", price.getCostSail());

        holder.textViewCostOne.setText(cost);
        holder.textViewUnit.setText(price.getPriceUnit());
        holder.textViewTitlePrice.setText(price.getName());
        holder.textViewCost.setText(cost);
        holder.textViewCostSail.setText(costSail);
        switch (stillShop){
            case "Ariali":
                holder.textViewCostOne.setTextAppearance(R.style.ThemesSpinnerAriali);
                holder.textViewUnit.setTextAppearance(R.style.ThemesSpinnerAriali);
                holder.textViewTitlePrice.setTextAppearance(R.style.ThemesSpinnerAriali);
                holder.textViewCost.setTextAppearance(R.style.ThemesSpinnerAriali);
                holder.textViewCostSail.setTextAppearance(R.style.ThemesSpinnerAriali);
                break;
            case "Bahnschrift":
                holder.textViewCostOne.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                holder.textViewUnit.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                holder.textViewTitlePrice.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                holder.textViewCost.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                holder.textViewCostSail.setTextAppearance(R.style.ThemesSpinnerBahnschrift);                break;
            case "Comic":
                holder.textViewCostOne.setTextAppearance(R.style.ThemesSpinnerComic);
                holder.textViewUnit.setTextAppearance(R.style.ThemesSpinnerComic);
                holder.textViewTitlePrice.setTextAppearance(R.style.ThemesSpinnerComic);
                holder.textViewCost.setTextAppearance(R.style.ThemesSpinnerComic);
                holder.textViewCostSail.setTextAppearance(R.style.ThemesSpinnerComic);                break;
            case "Deja":
                holder.textViewCostOne.setTextAppearance(R.style.ThemesSpinnerDeja);
                holder.textViewUnit.setTextAppearance(R.style.ThemesSpinnerDeja);
                holder.textViewTitlePrice.setTextAppearance(R.style.ThemesSpinnerDeja);
                holder.textViewCost.setTextAppearance(R.style.ThemesSpinnerDeja);
                holder.textViewCostSail.setTextAppearance(R.style.ThemesSpinnerDeja);                break;
            case "Italic":
                holder.textViewCostOne.setTextAppearance(R.style.ThemesSpinnerItalic);
                holder.textViewUnit.setTextAppearance(R.style.ThemesSpinnerItalic);
                holder.textViewTitlePrice.setTextAppearance(R.style.ThemesSpinnerItalic);
                holder.textViewCost.setTextAppearance(R.style.ThemesSpinnerItalic);
                holder.textViewCostSail.setTextAppearance(R.style.ThemesSpinnerItalic);                break;
            case "Segoeprb":
                holder.textViewCostOne.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                holder.textViewUnit.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                holder.textViewTitlePrice.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                holder.textViewCost.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                holder.textViewCostSail.setTextAppearance(R.style.ThemesSpinnerSegoeprb);                break;
            default:
                holder.textViewCostOne.setTextAppearance(R.style.ThemesSpinnerComic);
                holder.textViewUnit.setTextAppearance(R.style.ThemesSpinnerComic);
                holder.textViewTitlePrice.setTextAppearance(R.style.ThemesSpinnerComic);
                holder.textViewCost.setTextAppearance(R.style.ThemesSpinnerComic);
                holder.textViewCostSail.setTextAppearance(R.style.ThemesSpinnerComic);                break;
        }
        holder.textViewCostOne.setTextColor(-1);
        holder.textViewUnit.setTextColor(stillColor);
        holder.textViewTitlePrice.setTextColor(stillColor);
        holder.textViewCost.setTextColor(-1);
        holder.textViewCostSail.setTextColor(-1);
        if ("0.0".equals(sail)) {
            holder.cardViewPriceCost.setVisibility(View.INVISIBLE);
            holder.cardViewPriceCostOne.setVisibility(View.VISIBLE);
        } else {
            holder.cardViewPriceCost.setVisibility(View.VISIBLE);
            holder.cardViewPriceCostOne.setVisibility(View.INVISIBLE);
            holder.textViewTitlePrice.setTextColor(-913906);
            //Менять цвет cardView Background
//            holder.cardViewPriceCost.getBackground().setColorFilter(Color.parseColor("#F44336"), PorterDuff.Mode.MULTIPLY);
        }
        holder.buttonDeletePrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPriceDellListener!=null){
                    onPriceDellListener.OnPriceDell(price.getId(), position);
                }

            }
        });
        holder.buttonChangesPrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPriceChangeListener!=null){
                    onPriceChangeListener.OnPriceChange(price.getId(), position);
                }
            }
        });
        if (imageOff.equals(price.getImage())) {
            Picasso.get().load(R.drawable.podarok).into(holder.imageViewPrice);
        } else {
            Picasso.get().load(price.getImage()).into(holder.imageViewPrice);
        }
    }


    @Override
    public int getItemCount() {
        return prices.size();
    }

    class PriceViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewUnit;
        private TextView textViewTitlePrice;
        private ImageView imageViewPrice;
        private ImageView buttonDeletePrise;
        private ImageView buttonChangesPrise;
        private TextView textViewCost;
        private TextView textViewCostOne;
        private TextView textViewCostSail;
        private CardView cardViewPriceCost;
        private CardView cardViewPriceCostOne;
        private ConstraintLayout constraintLayout;
        public PriceViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitlePrice = itemView.findViewById(R.id.textViewTitlePrice);
            textViewUnit = itemView.findViewById(R.id.textViewUnit);
            textViewCost = itemView.findViewById(R.id.textViewCost);
            textViewCostOne = itemView.findViewById(R.id.textViewCostOne);
            textViewCostSail = itemView.findViewById(R.id.textViewSailCost);
            imageViewPrice = itemView.findViewById(R.id.imageViewPrise);
            buttonDeletePrise = itemView.findViewById(R.id.buttonDeletePrise);
            buttonChangesPrise = itemView.findViewById(R.id.buttonChangesPrise);
            cardViewPriceCost = itemView.findViewById(R.id.cardViewPriceCost);
            cardViewPriceCostOne = itemView.findViewById(R.id.cardViewPriceCostOne);
            constraintLayout = itemView.findViewById(R.id.constrantLayout);
        }
    }

    public List<Price> getPrices() {
        return prices;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPrices(List<Price> prices, int witchPrice, String stillShop, int stillColor) {
        this.prices = prices; this.witchPrice = witchPrice; this.stillShop = stillShop; this.stillColor = stillColor;
        notifyDataSetChanged();
    }


}
