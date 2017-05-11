package me.zheteng.android.videosaver.saver;

import android.support.annotation.Nullable;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简单的实现抓取美图的视频路径
 */
public class SimpleParser implements Callback {
    public static final String BASE_URL = "http://www.meipai.com/media/";
    OkHttpClient mClient;
    String mVideoId;
    ParseCallback mCallback;

    /**
     * 页面id：http://www.meipai.com/media/{id}
     * http://www.meipai.com/media/416832257
     * @param id 如上面url中的部分，一般是一串数字
     */
    public SimpleParser(String id) {
        mVideoId = id;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        mClient = builder.build();
    }

    public ParseCallback getCallback() {
        return mCallback;
    }

    public void setCallback(ParseCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (mCallback != null) {
            mCallback.onFailure(this, e);
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String string = response.body().string();
        Pattern pattern = Pattern.compile("<meta([^>]+property=\"([^\"]*)\"[^>]+content=\"([^\"]*)\"|"
                        + "[^>]+content=\"([^\"]*)\"[^>]+property=\"([^\"]*)\")[^>]*>",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        Map<String, String> hits = new HashMap<>();
        while (matcher.find()) {
            if (matcher.group(2) != null) {
                hits.put(matcher.group(2), matcher.group(3));
            } else if (matcher.group(5) != null){
                hits.put(matcher.group(5), matcher.group(4));
            }
        }

        String videoUrl = null;
        for (Map.Entry<String, String> entry : hits.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("og:video:url")) {
                videoUrl = entry.getValue();
                break;
            }
        }

        if (mCallback != null) {
            if (!isEmpty(videoUrl)) {
                mCallback.onParsed(this, videoUrl);
            } else {
                mCallback.onFailure(this, new NoVideoFoundException());
            }
        }
    }

    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    public void startParse() {
        Request request = generateRequest();
        Call call = mClient.newCall(request);
        call.enqueue(this);
    }

    private String getCompleteUrl() {
        return BASE_URL + mVideoId;
    }

    private Request generateRequest() {
        String url = getCompleteUrl();

        Request.Builder builder = new Request.Builder();
        return builder.url(url)
                .get()
                .header("User-Agent", "facebookexternalhit/1.1")
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
    }
}
