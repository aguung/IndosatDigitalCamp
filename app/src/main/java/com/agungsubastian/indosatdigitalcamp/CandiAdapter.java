package com.agungsubastian.indosatdigitalcamp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CandiAdapter extends RecyclerView.Adapter<CandiAdapter.ListViewHolder> implements Filterable {

    private ArrayList<Candi> listCandi;
    private ArrayList<Candi> listCandiFiltered;
    private int type;
    private CandiAdapterListener listener;

    public CandiAdapter(ArrayList<Candi> listCandi, int type,CandiAdapterListener listener) {
        this.listCandi = listCandi;
        this.listCandiFiltered = listCandi;
        this.type = type;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(type == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_candi_card, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_candi, parent, false);
        }
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Candi candi = listCandiFiltered.get(position);
        Glide.with(holder.itemView.getContext())
                .load(candi.getPhoto())
                .apply(new RequestOptions().override(100, 100).error(R.drawable.icon).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.imgPhoto);
        holder.tvNama.setText(candi.getNama());
        holder.tvAsal.setText(candi.getAsal());
        holder.tvDeskripsi.setText(candi.getDeskripsi());

    }

    @Override
    public int getItemCount() {
        return listCandiFiltered.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvNama, tvAsal, tvDeskripsi;
        ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvNama = itemView.findViewById(R.id.tv_item_name);
            tvAsal = itemView.findViewById(R.id.tv_item_from);
            tvDeskripsi = itemView.findViewById(R.id.tv_item_deskripsi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onCandiSelected(listCandiFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    listCandiFiltered = listCandi;
                } else {
                    ArrayList<Candi> filteredList = new ArrayList<>();
                    for (Candi row : listCandi) {
                        if (row.getNama().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    listCandiFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listCandiFiltered;
                Log.v("result",""+filterResults.values);
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listCandiFiltered = (ArrayList<Candi>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface CandiAdapterListener {
        void onCandiSelected(Candi data);
    }
}
