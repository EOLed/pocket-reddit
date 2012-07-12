package com.pocketreddit;

import com.am05.reddit.library.datasources.DataSourceException;
import com.am05.reddit.library.datasources.RedditDataSource;
import com.am05.reddit.library.things.Link;
import com.am05.reddit.library.things.Listing;
import com.am05.reddit.library.things.Subreddit;

public class RedditDataSourceManager {
    private static final RedditDataSource ds;
    private Delegate delegate;

    static {
        ds = new RedditDataSource();
    }

    public interface Delegate {
        void onLoadDefaultSubreddits(Listing<Subreddit> defaultSubreddits);

        void onLoadDefaultSubredditsFailed(Throwable t);

        void onLoadLinksForSubreddit(Listing<Link> linksForSubreddit);

        void onLoadLinksForSubredditsFailed(Throwable t);

        void onLoadLinksForFrontPage(Listing<Link> linksForFrontPage);

        void onLoadLinksForFrontPageFailed(Throwable t);
    }

    public RedditDataSourceManager(Delegate delegate) {
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

    public void loadLinksForFrontPage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    delegate.onLoadLinksForFrontPage(ds.getLinksForFrontPage());
                } catch (DataSourceException e) {
                    delegate.onLoadLinksForFrontPageFailed(e);
                }
            }
        }).start();
    }
}
