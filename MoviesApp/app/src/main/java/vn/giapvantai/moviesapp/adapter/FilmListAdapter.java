package vn.giapvantai.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import vn.giapvantai.moviesapp.R;
import vn.giapvantai.moviesapp.activities.DetailsActivity;
import vn.giapvantai.moviesapp.domain.ListFilm;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {

    private final ListFilm items;
    private Context context;

    public FilmListAdapter(ListFilm items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_film, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = items.getData().get(position).getTitle();
        String poster = items.getData().get(position).getPoster();
        holder.titleTxt.setText(title);

        RequestOptions requestOptions = new RequestOptions();
        MultiTransformation<Bitmap> multi = new MultiTransformation<>(new CenterCrop(), new RoundedCorners(30));
        requestOptions = requestOptions.transform(multi);

        Glide.with(context)
                .load(poster)
                .apply(requestOptions)
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("id", items.getData().get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTxt;
        private final ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
