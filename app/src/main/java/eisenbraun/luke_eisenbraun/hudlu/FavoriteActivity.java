package eisenbraun.luke_eisenbraun.hudlu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import eisenbraun.luke_eisenbraun.hudlu.models.Favorite;
import eisenbraun.luke_eisenbraun.hudlu.models.MashableNewsItem;
import io.realm.RealmResults;

/**
 * Created by luke.eisenbraun on 3/31/2016.
 */
public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyFavoriteAdapter adapter;
    private final List<MashableNewsItem> myDataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new MyFavoriteAdapter(myDataset, this);
        mRecyclerView.setAdapter(adapter);

        loadFavorites();
    }

    public void loadFavorites(){
        RealmResults<Favorite> favorites = FavoriteUtil.getAllFavorites(this.getApplicationContext());
        for (int i=0; i<favorites.size(); ++i){
            MashableNewsItem item = new MashableNewsItem();
            Favorite favorite = favorites.get(i);
            item.title = favorite.getTitle();
            item.author = favorite.getAuthor();
            item.feature_image = favorite.getImage();
            item.link = favorite.getLink();
            myDataset.add(item);
        }
    }
}