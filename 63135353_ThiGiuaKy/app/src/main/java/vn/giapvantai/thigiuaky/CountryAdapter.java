package vn.giapvantai.thigiuaky;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CountryAdapter extends BaseAdapter {

    private ArrayList<Country> dsQuocGia; //nguon du lieu
    private Context context;
    private LayoutInflater inflater; // XML <--> Java

    public CountryAdapter(ArrayList<Country> dsQuocGia, Context context) {
        this.dsQuocGia = dsQuocGia;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    class ControlHolder { //tuong ung voi file item layout
        ImageView imageViewFlag;
        TextView textViewName;
        TextView textViewPopulation;

    }

    @Override
    public int getCount() {
        return dsQuocGia.size();
    }

    @Override
    public Object getItem(int i) {
        return dsQuocGia.get(i);
    }

    @Override
    public long getItemId(int i) { //chua dung toi
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // get Item's View for setting data
        ControlHolder itemControlsHolder; // stores refs to XML
        if (view == null) { // inflate from layout item xml
            view = inflater.inflate(R.layout.item_layout_country, null);
            // set refs for controls
            itemControlsHolder = new ControlHolder();
            itemControlsHolder.imageViewFlag = view.findViewById(R.id.imageViewFlag);
            itemControlsHolder.textViewName = view.findViewById(R.id.textViewCountryName);
            itemControlsHolder.textViewPopulation = view.findViewById(R.id.textViewPopulation);

            view.setTag(itemControlsHolder);
        } else {
            itemControlsHolder = (ControlHolder) view.getTag();
        }

        // Set Data for controls with hold in itemControlsHolder
        // Data
        final Country nation = dsQuocGia.get(i);
        itemControlsHolder.textViewName.setText(nation.getCountryName());
        itemControlsHolder.textViewPopulation.setText("Dân số: " + nation.getPopulation());

        // Image
        int resImageID = context.getResources().getIdentifier(
                nation.getCountryFlag(),
                "mipmap",
                context.getPackageName()
        );
        itemControlsHolder.imageViewFlag.setImageResource(resImageID);

        // Handle item click
        view.setOnClickListener(v -> {
            // Create Intent to open CustomItemActivity
            Intent intent = new Intent(context, CustomItemActivity.class);
            intent.putExtra("countryName", nation.getCountryName());
            intent.putExtra("countryFlag", nation.getCountryFlag());
            intent.putExtra("population", nation.getPopulation());
            context.startActivity(intent);
        });

        return view;
    }
}
