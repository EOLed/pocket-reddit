package com.pocketreddit;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.am05.reddit.library.things.Link;
import com.am05.reddit.library.things.Listing;
import com.am05.reddit.library.things.Subreddit;

public class SubredditActivity extends ListActivity implements RedditDataSourceManager.Delegate {

    private static final String TAG = SubredditActivity.class.getName();
    private List<Subreddit> subreddits;
    private RedditDataSourceManager dsManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dsManager = new RedditDataSourceManager(this);
        dsManager.loadDefaultSubreddits();

        // setContentView(R.layout.activity_subreddit);

        // getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Subreddit subreddit = subreddits.get(position);
        Log.v(TAG, "time to bring you to: " + subreddit.getUrl());
        dsManager.loadLinksForSubreddit(subreddit.getDisplayName());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_subreddit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            // NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadDefaultSubreddits(Listing<Subreddit> defaultSubreddits) {
        final List<Subreddit> subreddits = defaultSubreddits.getChildren();
        this.subreddits = subreddits;
        runOnUiThread(new Runnable() {
            public void run() {
                setListAdapter(new ArrayAdapter<Subreddit>(SubredditActivity.this,
                        android.R.layout.simple_list_item_1, subreddits));
            }
        });
    }

    @Override
    public void onLoadDefaultSubredditsFailed(Throwable t) {
        Log.e(TAG, "Couldn't get default subreddits: " + t.getMessage(), t);
    }

    @Override
    public void onLoadLinksForSubreddit(Listing<Link> linksForSubreddit) {
        Log.v(TAG, "Links for subreddit: " + linksForSubreddit.getChildren());
    }

    @Override
    public void onLoadLinksForSubredditsFailed(Throwable t) {
        // TODO Auto-generated method stub

    }

}
