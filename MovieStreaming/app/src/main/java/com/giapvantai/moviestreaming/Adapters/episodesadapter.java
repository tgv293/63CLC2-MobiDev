package com.giapvantai.moviestreaming.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.giapvantai.moviestreaming.R;
import com.giapvantai.moviestreaming.models.Episodemodel;

import java.util.List;

public class episodesadapter extends RecyclerView.Adapter<episodesadapter.Myviewholder>{

    List<Episodemodel> episodemodelList;
    Context context;
    public episodesadapter(Context context, List<Episodemodel> episodemodelList) {
        this.context=context;
        this.episodemodelList=episodemodelList;

    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.episodeslayout,parent,false);


        return new Myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+episodemodelList.get(position).getImage()).into(holder.imageView);
        holder.title.setText(episodemodelList.get(position).getName());
        holder.ratings.setText(episodemodelList.get(position).getRatings());
        holder.overview.setText(episodemodelList.get(position).getOverview());

    }

    @Override
    public int getItemCount() {
        return episodemodelList.size();
    }

    class  Myviewholder extends RecyclerView.ViewHolder{

ImageView imageView;
TextView title,ratings,overview;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.thumbnail);
            title=itemView.findViewById(R.id.titleEpisode);
            ratings=itemView.findViewById(R.id.ratingEpisode);
            overview=itemView.findViewById(R.id.overViewEpisode);
        }

    }

}
