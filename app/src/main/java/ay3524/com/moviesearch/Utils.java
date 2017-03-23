package ay3524.com.moviesearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ashish on 06-03-2017.
 */

public class Utils {

    public static final String SCHEME = "https";
    public static final String BASE_URI = "www.omdbapi.com";
    public static final String T_KEY = "t";
    public static final String TYPE_KEY = "type";
    public static final String PLOT_KEY = "plot";
    public static final String FULL = "full";
    public static final String MOVIE = "movie";
    public static final String SERIES = "series";

    public static final String TITLE = "title";
    public static final String POSITION = "position";

    public static final int DEFAULT_POSITION_VALUE = 0;
    public static final float ZERO_RATING_VALUE = 0f;
    public static final float RATING_BAR_STEP_SIZE = 0.1f;


    static boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
