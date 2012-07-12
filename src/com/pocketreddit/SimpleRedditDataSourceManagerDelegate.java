package com.pocketreddit;

import android.util.Log;

import com.am05.reddit.library.things.Link;
import com.am05.reddit.library.things.Listing;
import com.am05.reddit.library.things.Subreddit;
import com.pocketreddit.RedditDataSourceManager.Delegate;

public class SimpleRedditDataSourceManagerDelegate implements Delegate {
    private static final String TAG = SimpleRedditDataSourceManagerDelegate.class.getName();

    @Override
    public void onLoadDefaultSubreddits(Listing<Subreddit> defaultSubreddits) {

    }

    @Override
    public void onLoadDefaultSubredditsFailed(Throwable t) {
        Log.e(TAG, "Could not load default subreddits.", t);
    }

    @Override
    public void onLoadLinksForSubreddit(Listing<Link> linksForSubreddit) {

    }

    @Override
    public void onLoadLinksForSubredditsFailed(Throwable t) {
        Log.e(TAG, "Could not load links for subreddit.", t);
    }

    @Override
    public void onLoadLinksForFrontPage(Listing<Link> linksForFrontPage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoadLinksForFrontPageFailed(Throwable t) {
        Log.e(TAG, "Could not load links for front page.", t);
    }
}
