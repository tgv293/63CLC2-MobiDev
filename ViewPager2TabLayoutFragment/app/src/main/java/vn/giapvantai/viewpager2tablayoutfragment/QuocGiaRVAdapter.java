package vn.giapvantai.viewpager2tablayoutfragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuocGiaRVAdapter extends RecyclerView.Adapter {
    List<QuocGia> lstDataSource;

    public QuocGiaRVAdapter(List<QuocGia> lstDataSource) {
        this.lstDataSource = lstDataSource;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_quoc_gia, parent, false);
        QuocGiaItemViewHoder vh = new QuocGiaItemViewHoder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        QuocGiaItemViewHoder viewHolder = (QuocGiaItemViewHoder) holder;
        viewHolder.position = position;

        QuocGia country = lstDataSource.get(position);

        ((QuocGiaItemViewHoder) holder).quocGiaNameView.setText(country.getCountryName());
        ((QuocGiaItemViewHoder) holder).flagView.setImageResource(getMipmapResId(holder.itemView, country.getCountryFlag()));
        ((QuocGiaItemViewHoder) holder).populationView.setText(String.valueOf(country.getPopulation()));
    }

    private int getMipmapResId(View itemView, String mipmapName) {
        String packageName = itemView.getContext().getPackageName();
        return itemView.getResources().getIdentifier(mipmapName, "mipmap", packageName);
    }

    @Override
    public int getItemCount() {
        return lstDataSource.size();
    }

    // Item view hoder class
    public final class QuocGiaItemViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public int position;
        ImageView flagView;
        TextView quocGiaNameView;
        TextView populationView;

        public QuocGiaItemViewHoder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            flagView = (ImageView) itemView.findViewById(R.id.imageViewFlag);
            quocGiaNameView = (TextView) itemView.findViewById(R.id.textViewCountryName);
            populationView = (TextView) itemView.findViewById(R.id.textViewPopulation);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            QuocGia country = lstDataSource.get(clickedPosition);
            Toast.makeText(view.getContext(), "Clicked: " + country.getCountryName(), Toast.LENGTH_SHORT).show();
        }
    }
}
