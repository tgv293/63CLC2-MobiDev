package vn.giapvantai.thigiuaky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BaiHatAdapter extends ArrayAdapter<BaiHat> {
    public BaiHatAdapter(Context context, ArrayList<BaiHat> dsBaiHat) {
        super(context, 0, dsBaiHat);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BaiHat baiHat = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(baiHat.getTenBai());

        return convertView;
    }
}

