package dk.rosenheim.xdrosenheim.redditing;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDRosenheim on 19-11-2015.
 */
public class PostsFragment extends Fragment {
    ListView             postsList;
    ArrayAdapter< Post > adapter;
    Handler              handler;
    String               subreddit;
    List< Post >         posts;
    PostsHolder          postsHolder;

    public PostsFragment() {
        handler = new Handler();
        posts = new ArrayList<>();
    }

    public static Fragment newInstance( String subreddit ) {
        PostsFragment pf = new PostsFragment();
        pf.subreddit = subreddit;
        pf.postsHolder = new PostsHolder(pf.subreddit);
        return pf;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState ) {
        View v = inflater.inflate(R.layout.posts, container, false);
        postsList = ( ListView ) v.findViewById(R.id.posts_list);
        postsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {
                //TODO Open link on tap
                Uri    uriUrl        = Uri.parse(posts.get(position).getLink());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
        postsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick( AdapterView< ? > parent, View view, int position,
                    long id ) {
                Uri    uriUrl        = Uri.parse(posts.get(position).getPermaLink());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
                return true;
            }
        });

        return v;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceStats ) {
        super.onActivityCreated(savedInstanceStats);
        initialize();
    }

    private void initialize() {
        if( posts.size() == 0 ) {
            new Thread() {
                public void run() {
                    posts.addAll(postsHolder.fetchPosts());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            createAdapter();
                        }
                    });
                }
            }.start();
        } else {
            createAdapter();
        }
    }

    private void createAdapter() {
        if( getActivity() == null ) {
            return;
        }
        adapter = new ArrayAdapter< Post >(getActivity(), R.layout.post_item, posts) {
            @Override
            public View getView( int position, View convertView, ViewGroup parent ) {
                if( convertView == null ) {
                    convertView = getActivity().getLayoutInflater()
                            .inflate(R.layout.post_item, null);
                }

                TextView postTitle, postDetails, postScore, postFlareText, postNsfw;
                postTitle = ( TextView ) convertView.findViewById(R.id.post_title);
                postDetails = ( TextView ) convertView.findViewById(R.id.post_comments);
                postScore = ( TextView ) convertView.findViewById(R.id.post_score);
                postFlareText = ( TextView ) convertView.findViewById(R.id.flareText);
                postNsfw = ( TextView ) convertView.findViewById(R.id.nsfw);

                postFlareText.setText(posts.get(position).getFlareText());
                if( posts.get(position).getFlareText() != null ) {
                    postFlareText.setTextSize(13);
                } else {
                    postFlareText.setTextSize(0);
                }
                if( !posts.get(position).getNsfw() ) {
                    postNsfw.setTextSize(0);
                } else {
                    postNsfw.setTextSize(13);
                }
                postTitle.setText(posts.get(position).getTitle());
                if( !posts.get(position).getSticky() ) {
                    postTitle.setTextColor(Color.WHITE);
                } else {
                    postTitle.setTextColor(Color.RED);
                }
                postDetails.setText(posts.get(position).getNumberOfComments());
                postScore.setText(posts.get(position).getScore());

                return convertView;
            }
        };
        postsList.setAdapter(adapter);
    }
}
