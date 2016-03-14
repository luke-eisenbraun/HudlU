package eisenbraun.luke_eisenbraun.hudlu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by luke.eisenbraun on 3/14/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataSet;
    private OnAdapterInteractionListener mListener;

    public MyAdapter(Context context, String[] myDataSet){
        mDataSet = myDataSet;
        mListener = (OnAdapterInteractionListener) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view,parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mMyText.setText((mDataSet[position]));

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mListener.o​nItemClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mMyText;

        public MyViewHolder(View v) {
            super(v);
            mMyText = (TextView) v.findViewById(R.id.item_my_text);
        }
    }


    public interface OnAdapterInteractionListener{
        void o​nItemClicked(View view, int position);
    }
}
