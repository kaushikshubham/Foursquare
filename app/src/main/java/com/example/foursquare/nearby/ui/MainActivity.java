package com.example.foursquare.nearby.ui;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foursquare.nearby.R;
import com.example.foursquare.nearby.presenter.IVenueListDataPresenter;
import com.example.foursquare.nearby.presenter.VenueListDataPresenter;
import com.example.foursquare.nearby.ui.adapterr.VenueListAdapter;
import com.example.foursquare.nearby.ui.listener.RecyclerTouchListener;
import com.example.foursquare.nearby.ui.loader.ListLoader;
import com.example.foursquare.nearby.utility.Logger;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity implements IView, LoaderManager.LoaderCallbacks<IVenueListDataPresenter>, RecyclerTouchListener.ClickListener, LocationHandler.IFetchLocation {

    private static final String TAG = MainActivity.class.getSimpleName();
    private IVenueListDataPresenter presenter;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private LocationHandler locationHandler;
    private VenueListAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        progressBar = findViewById(R.id.progress_bar);
        rv = findViewById(R.id.list);

        locationHandler = new LocationHandler(this, this);

        adapter = new VenueListAdapter(presenter);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) ? 2 : 4));

        rv.addOnItemTouchListener(new RecyclerTouchListener(this, this));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sort();
            }
        });
         /* 1001 is a unique loader id through which system manages
         a single instance of loader for an activity */
        getLoaderManager().initLoader(1001, null, this);
    }

    @Override
    public void fetchDataBasedonLocation(String locstring) {
        presenter.attachView(this);
        presenter.fetchData(locstring);
    }

    @Override
    public void dataDownLoaded() {
        Logger.v(TAG, "data downloaded : " + presenter.getCount());
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
        if (fab.getVisibility() != View.VISIBLE) {
            fab.setVisibility(View.VISIBLE);
        }
        adapter.setVenueListDataPresenter(presenter);
    }

    @Override
    public void displayError(String s) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        Logger.e(TAG, "data fetch error");
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationHandler.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationHandler.onPause();
    }

    /* On Request permission method to check the permisison is granted or not for Marshmallow+ Devices  */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationHandler.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        locationHandler.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.detachView();
        locationHandler.onDestroy();
        progressBar = null;
        locationHandler = null;
        presenter = null;
    }

    @Override
    public void onClick(View view, int position) {
        presenter.onClick(position);
    }

    /*
    Loader callback
     */
    @Override
    public Loader<IVenueListDataPresenter> onCreateLoader(int id, Bundle args) {
        return new ListLoader(this, new VenueListDataPresenter());
    }

    @Override
    public void onLoadFinished(Loader<IVenueListDataPresenter> loader, IVenueListDataPresenter data) {
        presenter = data;
        fetchDataBasedonLocation(null);
    }

    @Override
    public void onLoaderReset(Loader<IVenueListDataPresenter> loader) {
        if (presenter != null)
            presenter.onDestroy();
        presenter = null;
    }
}
