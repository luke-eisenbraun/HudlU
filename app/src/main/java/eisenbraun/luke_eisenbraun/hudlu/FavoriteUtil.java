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
        Favorite fav = new Favorite(
                newsItem.title,
                newsItem.author,
                newsItem.feature_image,
                newsItem.link);
        realm.beginTransaction();
        realm.copyToRealm(fav);
        realm.commitTransaction();
        realm.close();
    }

    public static void removeFavorite(Context context, MashableNewsItem newsItem){
        Realm realm = Realm.getInstance(context);
        Favorite fav = realm.where(Favorite.class).equalTo("title", newsItem.title).findFirst();
        if (fav != null) {
            Log.d("HudlU", "found: " + fav.getTitle() + ", removing.");
            realm.beginTransaction();
            fav.removeFromRealm();
            realm.commitTransaction();
        }
        realm.close();
    }

    public static boolean isFavorite(Context context, MashableNewsItem newsItem){
        Realm realm = Realm.getInstance(context);
        Favorite fav = realm.where(Favorite.class).equalTo("title", newsItem.title).findFirst();
        if (fav != null) {
            Log.d("HudlU", "found: " + fav.getTitle());
            realm.close();
            return true;
        }
        realm.close();
        return false;
    }

    public static RealmResults<Favorite> getAllFavorites(Context context){
        Realm realm = Realm.getInstance(context);
        RealmResults<Favorite> favs = realm.where(Favorite.class).findAll();
        Log.d("HudlU", "found: " + favs.size());
        return favs;
    }
}