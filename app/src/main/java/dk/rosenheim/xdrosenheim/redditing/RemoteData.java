package dk.rosenheim.xdrosenheim.redditing;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by XDRosenheim on 19-11-2015.
 */
public class RemoteData {
    public static HttpsURLConnection getConnection(String url) {
        System.out.println("URL: " + url);
        HttpsURLConnection hcon = null;
        try {
            hcon = (HttpsURLConnection) new URL(url).openConnection();
            hcon.setReadTimeout(15000);
            hcon.setRequestProperty("User-agent", "Redditing v0.1");
        } catch (MalformedURLException e) {
            Log.e("getConnection()", "Invalid URL: " + e.toString());
        } catch (IOException e) {
            Log.e("getConnection()", "Could not connect: " + e.toString());
        }
        return hcon;
    }

    public static String readContents(String url) {
        HttpsURLConnection hcon = getConnection(url);
        if (hcon == null) return null;
        try {
            StringBuilder sb = new StringBuilder(8192);
            String tmp;
            BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    hcon.getInputStream()
                )
            );
            while((tmp=br.readLine())!=null)
                sb.append(tmp).append("\n");
            br.close();
            return sb.toString();
        } catch (IOException e) {
            Log.d("READ FAILED: ", e.toString());
            return null;
        }
    }
}
