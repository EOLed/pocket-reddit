package com.pocketreddit;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.pocketreddit.library.things.Comment;
import com.pocketreddit.library.things.Link;
import com.pocketreddit.library.things.Listing;
import com.pocketreddit.library.things.Thing;

public class LinkCommentsActivity extends ListActivity {
    private RedditDataSourceManager dsManager;
    private Link op;
    private Listing<? extends Thing> listing;

    public static enum Extras {
        OP
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        op = (Link) getIntent().getExtras().getSerializable(Extras.OP.toString());

        dsManager = createDataSourceManager();
        dsManager.loadCommentsForLink(op);

        // setContentView(R.layout.activity_link_comments);
        // getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private RedditDataSourceManager createDataSourceManager() {
        return new RedditDataSourceManager(new SimpleRedditDataSourceManagerDelegate() {
            @Override
            public void onLoadCommentsForLink(Listing<? extends Thing> listingForLink) {
                listing = listingForLink;
                final List<String> commentsList = new ArrayList<String>();
                for (Thing comment : listing.getChildren()) {
                    try {
                        if (comment instanceof Comment)
                            commentsList.add(((Comment) comment).getBody());
                    } catch (NullPointerException e) {
                        return;
                    }
                }

                runOnUiThread(new Runnable() {
                    public void run() {
                        setListAdapter(new ArrayAdapter<String>(LinkCommentsActivity.this,
                                android.R.layout.simple_list_item_1, commentsList));
                    };
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_link_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
