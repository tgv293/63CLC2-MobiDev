package vn.giapvantai.moviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import vn.giapvantai.moviesapp.R;

public class ActorsListAdapter extends RecyclerView.Adapter<ActorsListAdapter.ViewHolder> {

    private List<String> images;
    private Context context;

    public ActorsListAdapter(List<String> images) {
        this.images = images;
        this.context = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize context here to avoid potential memory leaks
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.viewholder_actor, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl = images.get(position);
        holder.bindData(imageUrl);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ShapeableImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.actorImage);
        }

        public void bindData(String imageUrl) {
            Glide.with(context)
                    .load(imageUrl)
                    .into(pic);
        }
    }
}
