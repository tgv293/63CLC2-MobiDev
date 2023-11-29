package vn.giapvantai.moviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.giapvantai.moviesapp.R;

import java.util.List;

public class CategoryEachFilmListAdapter extends RecyclerView.Adapter<CategoryEachFilmListAdapter.ViewHolder> {

    private final List<String> items;
    private Context context;

    public CategoryEachFilmListAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position);
        holder.titleTxt.setText(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
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
