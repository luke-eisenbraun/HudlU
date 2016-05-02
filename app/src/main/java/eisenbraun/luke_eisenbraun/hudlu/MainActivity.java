package eisenbraun.luke_eisenbraun.hudlu;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import eisenbraun.luke_eisenbraun.hudlu.models.MashableNews;
import eisenbraun.luke_eisenbraun.hudlu.models.MashableNewsItem;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnAdapterInteractionListener{
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private final List<MashableNewsItem> myDataSet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(this, myDataSet);
        mRecyclerView.setAdapter(mAdapter);

        checkFirstRun();
        fetchLatestNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("HudlU", "Favorite menu item clicked.");
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(View view, int position){
        Snackbar.make(view, myDataSet.get(position).author, Snackbar.LENGTH_SHORT).show();
    }

    public void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("Prefs", Context.MODE_PRIVATE).getBoolean("isFirstRun", true);

        if(isFirstRun) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("This is your first run!");
            AlertDialog dialog = builder.create();
            dialog.show();

            getSharedPreferences("Prefs", Context.MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
        }
    }

    public void fetchLatestNews(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) { // Connected
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.GET,
                    "http://mashable.com/stories.json?hot_per_page=0&new_per_page=10&rising_per_page=0",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Asynchronous 'success' call back that runs on the main thread
                            MashableNews mashableNews = new Gson().fromJson(response, MashableNews.class);
                            myDataSet.addAll(mashableNews.newsItems);
                            mAdapter.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Asynchronous 'error' call back that runs on the main thread
                            Toast.makeText(getApplicationContext(), "Error occurred while fetching news", Toast.LENGTH_SHORT).show();
                        }
                    });

            requestQueue.add(request);

            Toast.makeText(getApplicationContext(), "Fetching latest news", Toast.LENGTH_SHORT).show();


        } else { // Disconnected
            Toast.makeText(getApplicationContext(), "No network connectivity", Toast.LENGTH_SHORT).show();
        }
    }
}
