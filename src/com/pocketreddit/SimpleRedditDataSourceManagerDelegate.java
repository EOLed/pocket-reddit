package com.pocketreddit;

import android.util.Log;

import com.pocketreddit.library.things.Link;
import com.pocketreddit.library.things.Listing;
import com.pocketreddit.library.things.Subreddit;
import com.pocketreddit.library.things.Thing;
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
    }

    @Override
    public void onLoadLinksForFrontPageFailed(Throwable t) {
        Log.e(TAG, "Could not load links for front page.", t);
    }

    @Override
    public void onLoadCommentsForLink(Listing<? extends Thing> listingForLink) {
    }

    @Override
    public void onLoadCommentsForLinkFailed(Throwable t) {
        Log.e(TAG, "Could not load comments for link.", t);
    }
}
