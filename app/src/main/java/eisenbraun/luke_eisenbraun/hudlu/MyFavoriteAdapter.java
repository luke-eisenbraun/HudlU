package eisenbraun.luke_eisenbraun.hudlu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import eisenbraun.luke_eisenbraun.hudlu.models.MashableNewsItem;

/**
 * Created by luke.eisenbraun on 4/26/2016.
 */
public class MyFavoriteAdapter extends RecyclerView.Adapter<MyFavoriteAdapter.MyFavoriteViewHolder> {
    private List<MashableNewsItem> mDataset;
    //private MyFavoriteAdapter.OnFavoriteAdapterInteractionListener mListener;
    private RequestQueue mRequestQueue;
    private Context context;

    public  MyFavoriteAdapter(List<MashableNewsItem> myDataset, Context context){
        this.mDataset = myDataset;
        //mListener = (OnFavoriteAdapterInteractionListener)context;
        mRequestQueue = Volley.newRequestQueue(context);
        this.context = context;
    }

    @Override
    public MyFavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view_favorite, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyFavoriteViewHolder vh = new MyFavoriteViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyFavoriteViewHolder holder, final int position) {
        final MashableNewsItem currItem = mDataset.get(position);
        ImageRequest request = new ImageRequest(currItem.feature_image,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        holder.imgView.setImageBitmap(bitmap);
                        Log.d("HudlU", holder.imgView.toString());
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        //imgView.setImageResource(R.drawable.image_load_error);
                        Log.e("HudlU", error.getMessage());
                    }
                });
        mRequestQueue.add(request);
        holder.textTitleView.setText(currItem.title);
        holder.textAuthorView.setText(currItem.author);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mListener.o​nItemClicked(v, position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currItem.link));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /*
    public interface OnFavoriteAdapterInteractionListener {
        void o​nItemClicked(View view, int p​osition);
    }
    */

    public class MyFavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView textTitleView;
        TextView textAuthorView;
        public MyFavoriteViewHolder(View v) {
            super(v);
            imgView = (ImageView)v.findViewById(R.id.item_image);
            textTitleView = (TextView)v.findViewById(R.id.item_title);
            textAuthorView = (TextView)v.findViewById(R.id.item_author);
        }
    }
}