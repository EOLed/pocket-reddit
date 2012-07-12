package com.pocketreddit;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.am05.reddit.library.things.Listing;
import com.am05.reddit.library.things.Subreddit;

public class SubredditActivity extends ListActivity {

    private static final String TAG = SubredditActivity.class.getName();
    private List<Subreddit> subreddits;
    private RedditDataSourceManager dsManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dsManager = createDataSourceManager();
        Log.v(TAG, "loading default subreddits");
        dsManager.loadDefaultSubreddits();

        // setContentView(R.layout.activity_subreddit);

        // getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private RedditDataSourceManager createDataSourceManager() {
        return new RedditDataSourceManager(new SimpleRedditDataSourceManagerDelegate() {
            @Override
            public void onLoadDefaultSubreddits(Listing<Subreddit> defaultSubreddits) {
                final List<Subreddit> subreddits = defaultSubreddits.getChildren();
                SubredditActivity.this.subreddits = subreddits;
                final List<String> subredditNames = new ArrayList<String>();

                for (Subreddit subreddit : subreddits) {
                    subredditNames.add(subreddit.getDisplayName());
                    Log.v(TAG, "adding: " + subreddit.getDisplayName());
                }

                runOnUiThread(new Runnable() {
                    public void run() {
                        setListAdapter(new ArrayAdapter<String>(SubredditActivity.this,
                                android.R.layout.simple_list_item_1, subredditNames));
                    }
                });
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Subreddit subreddit = subreddits.get(position);
        Log.v(TAG, "time to bring you to: " + subreddit.getUrl());
        Intent subredditLinks = new Intent(this, SubredditLinksActivity.class);
        subredditLinks.putExtra(SubredditLinksActivity.Extras.SUBREDDIT.toString(), subreddit);
        startActivity(subredditLinks);
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
}
