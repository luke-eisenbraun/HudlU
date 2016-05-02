package eisenbraun.luke_eisenbraun.hudlu;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * Created by luke.eisenbraun on 3/14/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<MashableNewsItem> mDataSet;
    private OnAdapterInteractionListener mListener;
    private RequestQueue mRequestQueue;
    private Context context;

    public MyAdapter(Context context, List<MashableNewsItem> myDataSet){
        mDataSet = myDataSet;
        mListener = (OnAdapterInteractionListener) context;
        mRequestQueue = Volley.newRequestQueue(context);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class4,parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MashableNewsItem mItem = mDataSet.get(position);
        holder.mMyText.setText(mItem.title);
        holder.mAuthorText.setText(mItem.author);

        ImageRequest mImageRequest = new ImageRequest(mItem.feature_image,
            new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    holder.mImage.setImageBitmap(response);
                    holder.mImage.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }, 0, 0, null,
            new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error){
                    holder.mImage.setImageResource(R.drawable.icon_hudllogo);
                }
            }
        );

        mRequestQueue.add(mImageRequest);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mListener.o​nItemClicked(v, position);
            }
        });

        // Set color of favorite
        boolean isFavorite = FavoriteUtil(context, mItem);
        if(isFavorite) {
            holder.mButton.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            holder.mButton.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else {
            holder.mButton.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            holder.mButton.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavorite = FavoriteUtil.isFavorite(context, mItem);
                if(isFavorite) {
                    FavoriteUtil.removeFavorite(context, mItem);
                    holder.mButton.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                    holder.mButton.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    Log.d("HudlU", "Removing favorite: " + mItem.title);
                } else {
                    FavoriteUtil.addFavorite(context, mItem);
                    holder.mButton.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                    holder.mButton.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    Log.d("HudlU", "Adding favorite: " + mItem.title);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mMyText;
        TextView mAuthorText;
        Button mButton;

        public MyViewHolder(View v) {
            super(v);
            mMyText = (TextView) v.findViewById(R.id.item_title);
            mAuthorText = (TextView) v.findViewById(R.id.item_author);
            mImage = (ImageView) v.findViewById(R.id.item_image);
            mButton = (Button) v.findViewById(R.id.button_favorite);
        }
    }


    public interface OnAdapterInteractionListener{
        void o​nItemClicked(View view, int position);
    }
}
