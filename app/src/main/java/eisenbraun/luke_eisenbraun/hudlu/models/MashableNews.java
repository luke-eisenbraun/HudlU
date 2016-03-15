package eisenbraun.luke_eisenbraun.hudlu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by luke.eisenbraun on 3/14/2016.
 */
public class MashableNews {
    @SerializedName("new")
    public List<MashableNewsItem> newsItems;
}
