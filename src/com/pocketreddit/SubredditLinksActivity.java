package com.pocketreddit;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.am05.reddit.library.things.Link;
import com.am05.reddit.library.things.Listing;
import com.am05.reddit.library.things.Subreddit;

public class SubredditLinksActivity extends ListActivity implements
        RedditDataSourceManager.Delegate {
    private static final String TAG = SubredditLinksActivity.class.getName();

    private RedditDataSourceManager ds;
    private List<Link> links;
    private Subreddit subreddit;

    public static enum Extras {
        SUBREDDIT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subreddit = (Subreddit) getIntent().getExtras()
                .getSerializable(Extras.SUBREDDIT.toString());
        ds = new RedditDataSourceManager(this);
        ds.loadLinksForSubreddit(subreddit.getDisplayName());
    }

    @Override
    public void onLoadDefaultSubreddits(Listing<Subreddit> defaultSubreddits) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoadDefaultSubredditsFailed(Throwable t) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoadLinksForSubreddit(Listing<Link> linksForSubreddit) {
        links = linksForSubreddit.getChildren();
        runOnUiThread(new Runnable() {
            public void run() {
                setListAdapter(new ArrayAdapter<Link>(SubredditLinksActivity.this,
                        android.R.layout.simple_list_item_1, links));
            }
        });
    }

    @Override
    public void onLoadLinksForSubredditsFailed(Throwable t) {
        Log.e(TAG, "Could not get links for subreddit.", t);
    }
}
