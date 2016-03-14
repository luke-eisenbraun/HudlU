package eisenbraun.luke_eisenbraun.hudlu;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by luke.eisenbraun on 3/14/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;
    public MyAdapter(String[] myDataset){
        mDataset = myDataset;
    }
    private OnAdapterInteractionListener mListener;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view,parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mMyText.setText((mDataset[position]));

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mListener.o​nItemClicked(v, position);
                Snackbar.make(view, myDataset[position], Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mMyText;
        mListener = (OnAdapterInteractionListener) context;

        public MyViewHolder(View v) {
            super(v);
            mMyText = (TextView) v.findViewById(R.id.item_my_text);
        }
    }

    public interface OnAdapterInteractionListener{
        void o​nItemClicked(​View view, int position);
    }
}
