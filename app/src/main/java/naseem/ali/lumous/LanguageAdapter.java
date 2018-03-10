package naseem.ali.lumous;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Naseem on 09-03-2018.
 */

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MainActivity.Vocab> links;

    public LanguageAdapter(Context context, ArrayList<MainActivity.Vocab> links){
        this.context=context;
        this.links=links;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(links.get(position).getLink())
                .fitCenter()
                .placeholder(R.drawable.ic_image_black_24dp)
                .crossFade()
                .into(holder.image);
        holder.textContent.setText(links.get(position).getWord());
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView image;
        public TextView textContent;
        public ViewHolder(View v) {
            super(v);
            image=(ImageView)v.findViewById(R.id.image);
            textContent=(TextView)v.findViewById(R.id.vocabText);
        }
    }

}
