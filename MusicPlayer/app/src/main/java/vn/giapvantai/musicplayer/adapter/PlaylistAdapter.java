package vn.giapvantai.musicplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.giapvantai.musicplayer.MPPreferences;
import vn.giapvantai.musicplayer.R;
import vn.giapvantai.musicplayer.model.PlayList;

import com.bumptech.glide.Glide;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.MyViewHolder> {
    private final List<PlayList> playLists;
    private final PlayListListener playListListener;

    public PlayListAdapter(PlayListListener playListListener, List<PlayList> playLists) {
        this.playLists = playLists;
        this.playListListener = playListListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo mới ViewHolder từ layout item_playlist
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Thiết lập dữ liệu cho ViewHolder tại vị trí position
        holder.name.setText(playLists.get(position).title);

        // Kiểm tra trạng thái và hiển thị ảnh album nếu trạng thái là true
        if (holder.state) {
            Glide.with(holder.art.getContext())
                    .load(playLists.get(position).musics.get(0).albumArt)
                    .placeholder(R.drawable.ic_album_art)
                    .into(holder.art);
        }
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng phần tử trong danh sách
        return playLists.size();
    }

    // Giao diện lắng nghe sự kiện click trên danh sách phát
    public interface PlayListListener {
        void click(PlayList playList);
    }

    // ViewHolder cho mỗi item trong danh sách phát
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView art;
        private final boolean state;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Lấy trạng thái từ SharedPreferences
            state = MPPreferences.getAlbumRequest(itemView.getContext());
            name = itemView.findViewById(R.id.title);
            art = itemView.findViewById(R.id.art);

            // Xử lý sự kiện click trên layout ảnh album
            itemView.findViewById(R.id.art_layout).setOnClickListener(v ->
                    playListListener.click(playLists.get(getAdapterPosition())));
        }
    }
}
