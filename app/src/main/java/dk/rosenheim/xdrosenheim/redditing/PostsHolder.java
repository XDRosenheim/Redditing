package dk.rosenheim.xdrosenheim.redditing;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDRosenheim on 19-11-2015.
 */
public class PostsHolder {
    String subreddit, url, after;

    PostsHolder( String sr ) {
        subreddit = sr;
        after = "";
        generateURL();
    }

    private void generateURL() {
        String URL_TEMPLATE = "https://reddit.com/r/SUBREDDIT_NAME/"
                + ".json"
                + "?after=AFTER";
        url = URL_TEMPLATE.replace("SUBREDDIT_NAME", subreddit);
        url = url.replace("AFTER", after);
    }

    List< Post > fetchPosts() {
        String       raw  = RemoteData.readContents(url);
        List< Post > list = new ArrayList<>();
        try {
            JSONObject data = new JSONObject(raw).getJSONObject("data");
            JSONArray children = data.getJSONArray("children");

            after = data.getString("after");

            for( int i = 0; i < children.length(); i++ ) {
                JSONObject cur = children.getJSONObject(i).getJSONObject("data");
                Post p = new Post();
                p.title = cur.optString("title");
                p.url = cur.getString("url");
                p.numComments = cur.optInt("num_comments");
                p.points = cur.optInt("score");
                p.author = cur.optString("author");
                p.subreddit = cur.optString("subreddit");
                p.permalink = cur.optString("permalink");
                p.domain = cur.optString("domain");
                p.sticky = cur.optBoolean("stickied");
                p.link_flair_text = cur.optString("link_flair_text");
                p.nsfw = cur.optBoolean("over_18");
                if( p.title != null ) {
                    list.add(p);
                }
            }
        } catch( Exception e ) {
            Log.e("fetchPost()", e.toString());
        }
        return list;
    }

    List< Post > fetchMorePosts() {
        generateURL();
        return fetchPosts();
    }
}
