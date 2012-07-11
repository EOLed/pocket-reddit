package com.pocketreddit;

import com.am05.reddit.library.things.Link;
import com.am05.reddit.library.things.Listing;
import com.am05.reddit.library.things.Subreddit;
import com.pocketreddit.RedditDataSourceManager.Delegate;

public class SimpleRedditDataSourceManagerDelegate implements Delegate {

    @Override
    public void onLoadDefaultSubreddits(Listing<Subreddit> defaultSubreddits) {

    }

    @Override
    public void onLoadDefaultSubredditsFailed(Throwable t) {

    }

    @Override
    public void onLoadLinksForSubreddit(Listing<Link> linksForSubreddit) {

    }

    @Override
    public void onLoadLinksForSubredditsFailed(Throwable t) {

    }

}
