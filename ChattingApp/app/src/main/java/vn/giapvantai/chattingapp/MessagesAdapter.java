package vn.giapvantai.chattingapp;

import static vn.giapvantai.chattingapp.ChatWindowActivity.reciverImgStatic;
import static vn.giapvantai.chattingapp.ChatWindowActivity.senderImg;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_SEND = 1;
    private static final int ITEM_RECEIVE = 2;

    private final Context context;
    private final ArrayList<msgModelclass> messagesAdapterArrayList;

    public MessagesAdapter(Context context, ArrayList<msgModelclass> messagesAdapterArrayList) {
        this.context = context;
        this.messagesAdapterArrayList = messagesAdapterArrayList;
    }

    private static boolean isVideoFile(String url) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        return mimeType != null && mimeType.startsWith("video");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_SEND) {
            view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
            return new SenderViewHolder(view, context);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.receiver_layout, parent, false);
            return new ReceiverViewHolder(view, context);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        msgModelclass message = messagesAdapterArrayList.get(position);
        holder.itemView.setOnLongClickListener(view -> {
            showDeleteDialog(message);
            return false;
        });

        if (holder.getItemViewType() == ITEM_SEND) {
            SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
            senderViewHolder.bind(message);
        } else {
            ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) holder;
            receiverViewHolder.bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messagesAdapterArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        msgModelclass message = messagesAdapterArrayList.get(position);
        if (Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().equals(message.getSenderid())) {
            return ITEM_SEND;
        } else {
            return ITEM_RECEIVE;
        }
    }

    private void showDeleteDialog(msgModelclass message) {
        new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this message?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    // Xóa tin nhắn từ cơ sở dữ liệu
                    DatabaseReference msgRef = FirebaseDatabase.getInstance().getReference("messages").child(message.getMessage());
                    msgRef.removeValue();

                    // Xóa tin nhắn từ ArrayList và cập nhật RecyclerView
                    messagesAdapterArrayList.remove(message);
                    notifyDataSetChanged();
                })
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    static class SenderViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgTxt;
        ImageView msgImage;
        Context context;

        public SenderViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profilerggg);
            msgTxt = itemView.findViewById(R.id.msgsendertyp);
            msgImage = itemView.findViewById(R.id.imagesend);
            this.context = context;
        }

        public void bind(msgModelclass message) {
            if (message.getFileUrl() != null) {
                Picasso.get().load(message.getFileUrl()).into(msgImage);
                msgTxt.setVisibility(View.GONE);
            } else {
                msgTxt.setText(message.getMessage());
                msgImage.setVisibility(View.GONE);
            }
            Picasso.get().load(senderImg).into(circleImageView);
        }
    }

    static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgTxt;
        ImageView msgImage;
        Context context;

        public ReceiverViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.pro);
            msgTxt = itemView.findViewById(R.id.receivertextset);
            msgImage = itemView.findViewById(R.id.imagereceiver);
            this.context = context;
        }

        public void bind(msgModelclass message) {
            if (message.getFileUrl() != null) {
                Picasso.get().load(message.getFileUrl()).into(msgImage);
                msgTxt.setVisibility(View.GONE);
            } else {
                msgTxt.setText(message.getMessage());
                msgImage.setVisibility(View.GONE);
            }
            Picasso.get().load(reciverImgStatic).into(circleImageView);
        }
    }
}
