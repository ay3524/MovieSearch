package ay3524.com.moviesearch.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ay3524.com.moviesearch.R;
import ay3524.com.moviesearch.utils.Utils;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText editText;
    Button button;
    String title;
    Spinner sp;
    int positionOfSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.edittext);

        button = (Button) findViewById(R.id.button);
        sp = (Spinner) findViewById(R.id.spinner);

        setUpSpinner();

        sp.setOnItemSelectedListener(this);
        button.setOnClickListener(this);
    }

    /**
     * This method is used set up the spinner with three values All,Movie,Serial
     */
    private void setUpSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.spinner_item_names));
        sp.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        title = editText.getText().toString().trim();
        if (title.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.title_empty_toast, Toast.LENGTH_SHORT).show();
        } else {
            hideKeyBoard();
            Intent i = new Intent(getApplicationContext(), DetailActivity.class);
            i.putExtra(Utils.TITLE, title);
            i.putExtra(Utils.POSITION, positionOfSpinner);
            startActivity(i);
        }
    }

    /**
     * This method is used to hide to hide the soft input keyboard
     */
    private void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        positionOfSpinner = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_settings){
            startActivity(new Intent(getApplicationContext(),About.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
