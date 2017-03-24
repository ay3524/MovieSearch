package ay3524.com.moviesearch.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static final String SCHEME = "https";
    public static final String BASE_URI = "www.omdbapi.com";
    public static final String T_KEY = "t";
    public static final String TYPE_KEY = "type";
    public static final String MOVIE = "movie";
    public static final String SERIES = "series";
    public static final String TITLE = "title";
    public static final String POSITION = "position";

    public static final String JSON_RESPONSE_KEY = "Response";
    public static final String JSON_RESPONSE_VALUE = "True";
    public static final String JSON_TITLE_KEY = "Title";
    public static final String JSON_GENRE_KEY = "Genre";
    public static final String JSON_RELEASED_KEY = "Released";
    public static final String JSON_PLOT_KEY = "Plot";
    public static final String JSON_IMDB_RATING_KEY = "imdbRating";
    public static final String JSON_POSTER_URL_KEY = "Poster";

    public static final String IMDB_RATING_PLACEHOLDER_STRING = "IMDB Rating : ";

    public static final int DEFAULT_POSITION_VALUE = 0;
    public static final float ZERO_RATING_VALUE = 0f;
    public static final float RATING_BAR_STEP_SIZE = 0.1f;

    /**
     * This method is used to check whether internet connection is available or not
     * It returns a boolean value true if the connection is available
     *
     * @param context - Takes the context of calling activity
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
