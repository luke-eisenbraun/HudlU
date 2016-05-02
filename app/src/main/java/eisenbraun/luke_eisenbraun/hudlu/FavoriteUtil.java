package eisenbraun.luke_eisenbraun.hudlu;

import android.content.Context;
import android.util.Log;

import eisenbraun.luke_eisenbraun.hudlu.models.Favorite;
import eisenbraun.luke_eisenbraun.hudlu.models.MashableNewsItem;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by luke.eisenbraun on 3/31/2016.
 */
public class FavoriteUtil {
    public static void addFavorite(Context context, MashableNewsItem newsItem){
        Realm realm = Realm.getInstance(context);
        Favorite favorite = new Favorite(
                newsItem.title,
                newsItem.author,
                newsItem.feature_image,
                newsItem.link);
        realm.beginTransaction();
        realm.copyToRealm(favorite);
        realm.commitTransaction();
        realm.close();
    }

    public static void removeFavorite(Context context, MashableNewsItem newsItem){
        Realm realm = Realm.getInstance(context);
        Favorite favorite = realm.where(Favorite.class).equalTo("title", newsItem.title).findFirst();
        if (favorite != null) {
            Log.d("HudlU", "found: " + favorite.getTitle() + ", removing.");
            realm.beginTransaction();
            favorite.removeFromRealm();
            realm.commitTransaction();
        }
        realm.close();
    }

    public static boolean isFavorite(Context context, MashableNewsItem newsItem){
        Realm realm = Realm.getInstance(context);
        Favorite favorite = realm.where(Favorite.class).equalTo("title", newsItem.title).findFirst();
        if (favorite != null) {
            Log.d("HudlU", "found: " + favorite.getTitle());
            realm.close();
            return true;
        }
        realm.close();
        return false;
    }

    public static RealmResults<Favorite> getAllFavorites(Context context){
        Realm realm = Realm.getInstance(context);
        RealmResults<Favorite> favorites = realm.where(Favorite.class).findAll();
        Log.d("HudlU", "found: " + favorites.size());
        return favorites;
    }
}