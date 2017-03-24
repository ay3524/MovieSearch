package ay3524.com.moviesearch.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import ay3524.com.moviesearch.R;
import ay3524.com.moviesearch.app.AppController;
import ay3524.com.moviesearch.utils.Utils;

public class DetailActivity extends AppCompatActivity {

    TextView title, genre, plot, releaseDate, rating, emptytext1, emptytext2;
    RatingBar ratingBar;
    ProgressDialog pDialog;
    ImageView posterImage;
    RelativeLayout errorView, movieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpActionBar();

        posterImage = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        genre = (TextView) findViewById(R.id.genre);
        plot = (TextView) findViewById(R.id.plot);
        releaseDate = (TextView) findViewById(R.id.release);
        rating = (TextView) findViewById(R.id.rating);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        errorView = (RelativeLayout) findViewById(R.id.empty_view);
        movieView = (RelativeLayout) findViewById(R.id.movie_view);
        emptytext1 = (TextView) findViewById(R.id.empty_title_text);
        emptytext2 = (TextView) findViewById(R.id.empty_subtitle_text);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        if (getIntent().getExtras() != null) {
            String title = getIntent().getStringExtra(Utils.TITLE);
            int position = getIntent().getIntExtra(Utils.POSITION, Utils.DEFAULT_POSITION_VALUE);
            String url = buildMovieUrl(title, position);
            checkConnectionAndDisplayTheMovie(url);

        }
    }

    /**
     * This method is used to put the back button on toolbar for navigation
     */
    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * This method is used to build the url to be used on the basis of title and position
     *
     * @param title    - movie or serial title
     * @param position - position of spinner to know where to search
     */
    private String buildMovieUrl(String title, int position) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Utils.SCHEME)
                .authority(Utils.BASE_URI)
                .appendQueryParameter(Utils.T_KEY, title);

        switch (position) {
            case 1:
                builder.appendQueryParameter(Utils.TYPE_KEY, Utils.MOVIE);
                break;
            case 2:
                builder.appendQueryParameter(Utils.TYPE_KEY, Utils.SERIES);
                break;
        }

        return builder.build().toString();
    }

    /**
     * This method is used to check intenet connection and show the movie result
     *
     * @param url    - movie url based on given title
     */
    private void checkConnectionAndDisplayTheMovie(String url) {
        if (Utils.isConnected(getApplicationContext())) {
            displayTheMovieResult(url);
        } else {
            setUpErrorView(R.string.error_view_title,R.string.error_view_subtitle);
        }
    }

    /**
     * This method is used for the network request using volley library and
     * JSON parsing to get Title, Genre, Release date, Plot (short version) and rating of the movie or serial.
     *
     * @param url - omdb url for the specified title and type
     */
    private void displayTheMovieResult(String url) {
        // Tag used to cancel the request
        String tag_string_req = getString(R.string.tag_movie_request);

        pDialog.setMessage(getString(R.string.loding_progress));
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String responseString = jsonObject.getString(Utils.JSON_RESPONSE_KEY);
                    if(responseString.equals(Utils.JSON_RESPONSE_VALUE)){
                        movieView.setVisibility(View.VISIBLE);
                        setMovieTextAndImage(jsonObject.getString(Utils.JSON_TITLE_KEY),
                                jsonObject.getString(Utils.JSON_GENRE_KEY),
                                jsonObject.getString(Utils.JSON_RELEASED_KEY),
                                jsonObject.getString(Utils.JSON_PLOT_KEY),
                                jsonObject.getString(Utils.JSON_IMDB_RATING_KEY),
                                jsonObject.getString(Utils.JSON_POSTER_URL_KEY));
                    }else{
                        setUpErrorView(R.string.error_movie_not_found,R.string.not_present);
                    }
                } catch (JSONException e) {
                    Log.e("JSONError",e.getMessage());
                    e.printStackTrace();
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                setUpErrorView(R.string.problem_server,R.string.worry_text);
                hideDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    /**
     * This method is used to set values of TextView and ImageView for the current title entered by user
     * @param titleString   - Movie or Serial Title
     * @param genreString   - Movie or Serial Genre
     * @param releaseDateString   - Movie or Serial Release Date
     * @param plotString   - Movie or Serial Full Plot
     * @param ratingString   - Movie or Serial IMDB Rating
     * @param posterURL   - Movie or Serial poster url link
     */
    private void setMovieTextAndImage(String titleString, String genreString,
                                      String releaseDateString, String plotString,
                                      String ratingString, String posterURL) {

        title.setText(titleString);
        genre.setText(genreString);
        releaseDate.setText(releaseDateString);
        plot.setText(plotString);
        rating.setText(Utils.IMDB_RATING_PLACEHOLDER_STRING.concat(ratingString));

        try {
            Float f = Float.parseFloat(ratingString);
            ratingBar.setRating(f);
        } catch (NumberFormatException e) {
            ratingBar.setRating(Utils.ZERO_RATING_VALUE);
        }
        ratingBar.setStepSize(Utils.RATING_BAR_STEP_SIZE);

        Glide.with(DetailActivity.this)
                .load(posterURL)
                .crossFade()
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.sorry_no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(posterImage);

    }

    /**
     * This method is used to show loading dialog
     */
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * This method is used to hide loading dialog
     */
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    /**
     * This method is used to show various errors encountered when making movie requests
     * For Ex.Internet Connection Error,Movie Not Found and Server Problem
     * @param error_view_title    - Error heading text
     * @param error_view_subtitle - Error sub-heading text
     */
    private void setUpErrorView(int error_view_title, int error_view_subtitle) {
        errorView.setVisibility(View.VISIBLE);
        emptytext1.setText(error_view_title);
        emptytext2.setText(error_view_subtitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            navigateUpTo(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
