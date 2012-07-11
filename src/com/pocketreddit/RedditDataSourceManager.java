package com.pocketreddit;

import com.am05.reddit.library.datasources.DataSourceException;
import com.am05.reddit.library.datasources.RedditDataSource;
import com.am05.reddit.library.things.Link;
import com.am05.reddit.library.things.Listing;
import com.am05.reddit.library.things.Subreddit;

public class RedditDataSourceManager {
    private RedditDataSource ds;
    private Delegate delegate;

    public interface Delegate {
        void onLoadDefaultSubreddits(Listing<Subreddit> defaultSubreddits);

        void onLoadDefaultSubredditsFailed(Throwable t);

        void onLoadLinksForSubreddit(Listing<Link> linksForSubreddit);

        void onLoadLinksForSubredditsFailed(Throwable t);
    }

    public RedditDataSourceManager(Delegate delegate) {
        ds = new RedditDataSource();
        this.delegate = delegate;
    }

    public void loadDefaultSubreddits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    delegate.onLoadDefaultSubreddits(ds.getDefaultSubreddits());
                } catch (DataSourceException e) {
                    delegate.onLoadDefaultSubredditsFailed(e);
                }
            }
        }).start();
    }

    public void loadLinksForSubreddit(final String subreddit) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    delegate.onLoadLinksForSubreddit(ds.getLinksForSubreddit(subreddit));
                } catch (DataSourceException e) {
                    delegate.onLoadLinksForSubredditsFailed(e);
                }
            }
        }).start();
    }
}
