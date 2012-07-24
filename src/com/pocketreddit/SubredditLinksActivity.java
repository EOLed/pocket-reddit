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

import com.pocketreddit.authentication.AuthenticatorManager;
import com.pocketreddit.library.Constants;
import com.pocketreddit.library.authentication.AuthenticationException;
import com.pocketreddit.library.authentication.LoginResult;
import com.pocketreddit.library.things.Link;
import com.pocketreddit.library.things.Listing;
import com.pocketreddit.library.things.Subreddit;

public class SubredditLinksActivity extends ListActivity {
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
        
        setupHttpLogging();

        System.setProperty(Constants.SYSTEM_PROPERTY_USER_AGENT, "Pocket Reddit for Android");


        if (getIntent().getExtras() != null) {
            subreddit = (Subreddit) getIntent().getExtras().getSerializable(
                    Extras.SUBREDDIT.toString());
        }

        Log.v(TAG, "was there a subreddit passed? " + (subreddit != null));

        ds = new RedditDataSourceManager(createDataSourceManagerDelegate());

        AuthenticatorManager authenticator = new AuthenticatorManager(createAuthenticatorManagerDelegate());
        if (!authenticator.isLoggedIn()) {
            authenticator.authenticate("testachan", "testachan");
        }
    }

    private AuthenticatorManager.Delegate createAuthenticatorManagerDelegate() {
        return new AuthenticatorManager.Delegate() {
            
            @Override
            public void onAuthenticateFailed(AuthenticationException e) {
                Log.e(TAG, "Could not authenticate.", e);
            }
            
            @Override
            public void onAuthenticate(LoginResult loginResult) {
                if (subreddit == null) {
                    ds.loadLinksForFrontPage();
                } else {
                    ds.loadLinksForSubreddit(subreddit.getDisplayName());
                }
            }
        };
    }

    private void setupHttpLogging() {
        java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(
                java.util.logging.Level.FINEST);
        java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(
                java.util.logging.Level.FINEST);

        System.setProperty("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers",
                "debug");
    }

    private RedditDataSourceManager.Delegate createDataSourceManagerDelegate() {
        return new SimpleRedditDataSourceManagerDelegate() {
            @Override
            public void onLoadLinksForSubreddit(Listing<Link> linksForSubreddit) {
                loadListing(linksForSubreddit);
            }

            @Override
            public void onLoadLinksForFrontPage(Listing<Link> linksForFrontPage) {
                loadListing(linksForFrontPage);
            }
        };
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Link link = links.get(position);
        Intent linkCommentsIntent = new Intent(this, LinkCommentsActivity.class);
        linkCommentsIntent.putExtra(com.pocketreddit.LinkCommentsActivity.Extras.OP.toString(),
                link);
        startActivity(linkCommentsIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_subreddit_links, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_subreddits:
            launchSubredditActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchSubredditActivity() {
        startActivity(new Intent(this, SubredditActivity.class));
    }

    private void loadListing(Listing<Link> links) {
        this.links = links.getChildren();
        final List<String> linkTitles = new ArrayList<String>();

        for (Link link : this.links) {
            linkTitles.add(link.getTitle());
        }

        runOnUiThread(new Runnable() {
            public void run() {
                setListAdapter(new ArrayAdapter<String>(SubredditLinksActivity.this,
                        android.R.layout.simple_list_item_1, linkTitles));
            }
        });
    }
}
