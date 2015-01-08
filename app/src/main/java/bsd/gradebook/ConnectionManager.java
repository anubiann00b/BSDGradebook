package bsd.gradebook;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionManager {

    private static String cache;

    enum Response {
        SUCCESS, BAD_CREDS, NETWORK_FAILURE;
    }

    public static Response makeConnection(Context context, final String url) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                return downloadUrl(url);
            } catch (IOException e) {
                return Response.NETWORK_FAILURE;
            }
        } else {
            return Response.NETWORK_FAILURE;
        }
    }

    private static Response downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();

        String response = new String(ByteStreams.toByteArray(conn.getInputStream()));

        if (isSuccess(response)) {
            cache = response;
            return Response.SUCCESS;
        }
        return Response.BAD_CREDS;
    }

    private static boolean isSuccess(String response) {
        return !"{\"error\":\"username or password is incorrect\"}".equals(response);
    }
}