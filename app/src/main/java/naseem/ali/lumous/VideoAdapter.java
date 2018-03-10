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
import com.marcinmoskala.videoplayview.VideoPlayView;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * Created by Naseem on 09-03-2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MainActivity.Vocab> videos;

    public VideoAdapter(Context context, ArrayList<MainActivity.Vocab> video){
        this.context=context;
        this.videos=video;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textContent.setText(videos.get(position).getWord());
        holder.videoView.setVideoUrl(videos.get(position).getLink());
        holder.videoView.setLooping(false);
        holder.videoView.setAutoplay(false);
        Glide.with(context)
                .load(R.drawable.loader3)
                .asGif()
                .fitCenter()
                .into(holder.loader);
        holder.videoView.setOnVideoReadyListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                holder.loader.setVisibility(View.GONE);
                return null;
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public VideoPlayView videoView;
        public TextView textContent;
        public ImageView loader;
        public ViewHolder(View v) {
            super(v);
            videoView=(VideoPlayView) v.findViewById(R.id.video);
            textContent=(TextView)v.findViewById(R.id.videoText);
            loader=(ImageView)v.findViewById(R.id.loader);
        }
    }

}
