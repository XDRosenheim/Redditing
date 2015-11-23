package dk.rosenheim.xdrosenheim.redditing;

import java.util.Objects;

/**
 * Created by XDRosenheim on 19-11-2015.
 */
public class Post {
    String subreddit, title, author,
            permalink, url, domain,
            link_flair_text;
    Boolean sticky, nsfw;
    int points, numComments;

    String getNumberOfComments() {
        return numComments == 1 ? numComments + " comment" : numComments + " comments";
    }
    String getTitle() {
        return title;
    }
    String getScore() {
        return Integer.toString(points);
    }
    Boolean getSticky() {
        return sticky; // (°͜ʖ°)
    }
    String getFlareText() {
        return Objects.equals(link_flair_text, "null") || Objects.equals(link_flair_text, "") ? null : "[ " + link_flair_text + " ]";
    }
    String getLink() {
        return url;
    }
    String getOP() {
        return author;
    }
    boolean getNsfw() {
        return nsfw;
    }
}
