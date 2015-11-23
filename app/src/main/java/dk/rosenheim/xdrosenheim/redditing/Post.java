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
    int points, numComments, id;

    /**
     * @return Amount of comments + "comment(s)"
     */
    public String getNumberOfComments() {
        return numComments == 1 ? numComments + " comment" : numComments + " comments";
    }

    /**
     * @return Some people master title creations, some doesn't.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return Return karma. Hope you have been nice.
     */
    public String getScore() {
        return Integer.toString(points);
    }

    /**
     * A sticky situation.
     *
     * @return Sticky/pinned status for the post.
     */
    public Boolean getSticky() {
        return sticky; // (°͜ʖ°)
    }

    /**
     * @return Flair text, if any.
     */
    public String getFlareText() {
        return Objects.equals(link_flair_text, "null") || Objects.equals(link_flair_text, "") ? null
                : "[ " + link_flair_text + " ]";
    }

    /**
     * @return The url for the post.
     */
    public String getLink() {
        return url;
    }

    public String getPermaLink() {
        return permalink;
    }
    /**
     * @return Who posted.
     */
    public String getOP() {
        return author;
    }

    /**
     * @return NSFW status for the post.
     */
    public boolean getNsfw() {
        return nsfw;
    }
}
