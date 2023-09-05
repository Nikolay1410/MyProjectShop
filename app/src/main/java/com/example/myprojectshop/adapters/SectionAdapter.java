package com.example.myprojectshop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprojectshop.R;
import com.example.myprojectshop.lists.Price;
import com.example.myprojectshop.lists.Section;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {
    private PriceAdapter priceAdapter;
    private List<Section> sections;
    private List<Price> prices;
    private int witchPrice;
    private String stillShop;
    private int stillColor;
    private Context context;
    private OnPriceAddClickListener onPriceAddClickListener;
    private OnSectionDeleteClickListener onSectionDeleteClickListener;
    private OnSectionChangeClickListener onSectionChangeClickListener;
    private OnChangePriceListener onChangePriceListener;
    private OnDeletePriceListener onDeletePriceListener;
    public interface OnPriceAddClickListener{
        void OnPriceAddClick(String section);
    }
    public interface OnSectionDeleteClickListener {
        void OnSectionDelete(int position, String sectionId);
    }
    public interface OnSectionChangeClickListener {
        void OnSectionChange(int position, String sectionId);
    }
    public interface OnDeletePriceListener{
        void OnDeletePrice(int position, String sectionId, String idPrice);
    }
    public interface OnChangePriceListener{
        void OnChangePrice(int position, String sectionId, String idPrice);
    }

    public void setPriceAdapter(PriceAdapter priceAdapter) {
        this.priceAdapter = priceAdapter;
    }

    public void setOnSectionDeleteClickListener(OnSectionDeleteClickListener onSectionDeleteClickListener) {
        this.onSectionDeleteClickListener = onSectionDeleteClickListener;
    }

    public void setOnSectionChangeClickListener(OnSectionChangeClickListener onSectionChangeClickListener) {
        this.onSectionChangeClickListener = onSectionChangeClickListener;
    }

    public void setOnPriceAddClickListener(OnPriceAddClickListener onPriceAddClickListener) {
        this.onPriceAddClickListener = onPriceAddClickListener;
    }

    public void setOnChangePriceListener(OnChangePriceListener onChangePriceListener) {
        this.onChangePriceListener = onChangePriceListener;
    }

    public void setOnDeletePriceListener(OnDeletePriceListener onDeletePriceListener) {
        this.onDeletePriceListener = onDeletePriceListener;
    }

    public SectionAdapter() {sections = new ArrayList<>(); prices = new ArrayList<>();
    }


    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_item, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.i("nhghghg", "lll"+sections.size());
        Log.i("nhghghg", "lll"+prices.size());
        String imageOff = "image";
        Section section = sections.get(position);
        switch (stillShop){
            case "Ariali":
                holder.textViewSectionTitle.setTextAppearance(R.style.ThemesSpinnerAriali);
                holder.buttonAddSectionOnPrice.setTextAppearance(R.style.ThemesSpinnerAriali);
                holder.buttonSectionChanges.setTextAppearance(R.style.ThemesSpinnerAriali);
                holder.buttonSectionDelete.setTextAppearance(R.style.ThemesSpinnerAriali);
                break;
            case "Bahnschrift":
                holder.textViewSectionTitle.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                holder.buttonAddSectionOnPrice.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                holder.buttonSectionChanges.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                holder.buttonSectionDelete.setTextAppearance(R.style.ThemesSpinnerBahnschrift);
                break;
            case "Comic":
                holder.textViewSectionTitle.setTextAppearance(R.style.ThemesSpinnerComic);
                holder.buttonAddSectionOnPrice.setTextAppearance(R.style.ThemesSpinnerComic);
                holder.buttonSectionChanges.setTextAppearance(R.style.ThemesSpinnerComic);
                holder.buttonSectionDelete.setTextAppearance(R.style.ThemesSpinnerComic);
                break;
                case "Deja":
                    holder.textViewSectionTitle.setTextAppearance(R.style.ThemesSpinnerDeja);
                    holder.buttonAddSectionOnPrice.setTextAppearance(R.style.ThemesSpinnerDeja);
                    holder.buttonSectionChanges.setTextAppearance(R.style.ThemesSpinnerDeja);
                    holder.buttonSectionDelete.setTextAppearance(R.style.ThemesSpinnerDeja);
                    break;
                    case "Italic":
                        holder.textViewSectionTitle.setTextAppearance(R.style.ThemesSpinnerItalic);
                        holder.buttonAddSectionOnPrice.setTextAppearance(R.style.ThemesSpinnerItalic);
                        holder.buttonSectionChanges.setTextAppearance(R.style.ThemesSpinnerItalic);
                        holder.buttonSectionDelete.setTextAppearance(R.style.ThemesSpinnerItalic);
                        break;
                        case "Segoeprb":
                            holder.textViewSectionTitle.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                            holder.buttonAddSectionOnPrice.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                            holder.buttonSectionChanges.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                            holder.buttonSectionDelete.setTextAppearance(R.style.ThemesSpinnerSegoeprb);
                            break;
                            default:
                                holder.textViewSectionTitle.setTextAppearance(R.style.ThemesSpinnerComic);
                                holder.buttonAddSectionOnPrice.setTextAppearance(R.style.ThemesSpinnerComic);
                                holder.buttonSectionChanges.setTextAppearance(R.style.ThemesSpinnerComic);
                                holder.buttonSectionDelete.setTextAppearance(R.style.ThemesSpinnerComic);
                                break;
        }
        holder.textViewSectionTitle.setTextColor(stillColor);
        holder.buttonAddSectionOnPrice.setTextColor(stillColor);
        holder.buttonSectionChanges.setTextColor(stillColor);
        holder.buttonSectionDelete.setTextColor(stillColor);

        holder.textViewSectionTitle.setText(section.getTitle().toString());
        if (imageOff.equals(section.getImage())){
            Picasso.get().load(R.drawable.podarok).into(holder.imageViewSection);
        }else {
            Picasso.get().load(section.getImage()).into(holder.imageViewSection);
        }
        List<Price> pricesOk = new ArrayList<>();
        for (Price jjj:prices){
            if (section.getId().equals(jjj.getSectionId())){
                pricesOk.add(jjj);
            }
        }
        priceAdapter = new PriceAdapter(pricesOk, witchPrice, stillShop, stillColor);
        holder.recyclerViewPrices.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewPrices.setAdapter(priceAdapter);
        priceAdapter.setOnPriceDellListener(new PriceAdapter.OnPriceDellListener() {
            @Override
            public void OnPriceDell(String id, int positionId) {
                if (onDeletePriceListener!=null){
                    onDeletePriceListener.OnDeletePrice(positionId, section.getId(), id);
                }
            }
        });
        priceAdapter.setOnPriceChangeListener(new PriceAdapter.OnPriceChangeListener() {
            @Override
            public void OnPriceChange(String id, int positionId) {
                if (onChangePriceListener!=null){
                    onChangePriceListener.OnChangePrice(positionId, section.getId(), id);
                }
            }
        });
        holder.buttonAddSectionOnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPriceAddClickListener!=null){
                    onPriceAddClickListener.OnPriceAddClick(section.getId());
                }
            }
        });
        holder.buttonSectionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSectionDeleteClickListener !=null){
                    onSectionDeleteClickListener.OnSectionDelete(position, section.getId());
                }
            }
        });
        holder.buttonSectionChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSectionChangeClickListener !=null){
                    onSectionChangeClickListener.OnSectionChange(position, section.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewSectionTitle;
        private ImageView imageViewSection;
        private RecyclerView recyclerViewPrices;
        private Button buttonAddSectionOnPrice;
        private Button buttonSectionDelete;
        private Button buttonSectionChanges;
        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSectionTitle = itemView.findViewById(R.id.textViewSectionTitle);
            imageViewSection = itemView.findViewById(R.id.imageViewSection);
            recyclerViewPrices = itemView.findViewById(R.id.recyclerViewPrices);
            buttonAddSectionOnPrice = itemView.findViewById(R.id.buttonAddSectionOnPrice);
            buttonSectionDelete = itemView.findViewById(R.id.buttonSectionDelete);
            buttonSectionChanges = itemView.findViewById(R.id.buttonSectionChanges);

        }
    }

    public void setSections(List<Section> sections, List<Price> prices, int witchPrice, String stillShop, int stillColor) {
        this.sections = sections; this.prices = prices; this.witchPrice = witchPrice; this.stillShop = stillShop; this.stillColor = stillColor;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }
}
