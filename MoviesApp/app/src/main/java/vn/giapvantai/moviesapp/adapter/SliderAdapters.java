package vn.giapvantai.moviesapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import vn.giapvantai.moviesapp.R;
import vn.giapvantai.moviesapp.domain.SliderItems;

public class SliderAdapters extends RecyclerView.Adapter<SliderAdapters.SliderViewHolder> {

    private List<SliderItems> sliderItems;
    private ViewPager2 viewPager2;
    private Context context;

    // Constructor
    public SliderAdapters(List<SliderItems> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderAdapters.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.slide_items_container, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapters.SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
        if(position == sliderItems.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    // ViewHolder class
    public class SliderViewHolder extends  RecyclerView.ViewHolder {
        private ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }

        void setImage(SliderItems sliderItems) {
            RequestOptions requestOptions = new RequestOptions();
            MultiTransformation<Bitmap> multi = new MultiTransformation<>(new CenterCrop(), new RoundedCorners(60));
            requestOptions = requestOptions.transform(multi);

            Glide.with(context)
                    .load(sliderItems.getImage())
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int oldSize = sliderItems.size();
            List<SliderItems> duplicateItems = new ArrayList<>(sliderItems);
            sliderItems.addAll(duplicateItems);
            notifyItemRangeInserted(oldSize, duplicateItems.size());
        }
    };

}
