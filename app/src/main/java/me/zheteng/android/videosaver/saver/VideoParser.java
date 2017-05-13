package me.zheteng.android.videosaver.saver;

import android.support.annotation.NonNull;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 解析器框架代码
 */
public class VideoParser implements Callback {
    private OkHttpClient mClient;
    private ParseCallback mCallback;
    private UrlProvider mUrlProvider;
    private Extractor mExtractor;

    /**
     * 创建一个解析
     * @param urlProvider url provider
     * @param extractor 抽取算法
     * @return VideoParser
     */
    public static VideoParser create(@NonNull UrlProvider urlProvider, @NonNull Extractor extractor) {
        VideoParser instance = new VideoParser();
        instance.mUrlProvider = urlProvider;
        instance.mExtractor = extractor;

        return instance;
    }


    private VideoParser() {
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
        String videoUrl = mExtractor.extract(string);

        if (mCallback != null) {
            if (!ParserUtils.isEmpty(videoUrl)) {
                mCallback.onParsed(this, videoUrl);
            } else {
                mCallback.onFailure(this, new NoVideoFoundException());
            }
        }
    }

    /**
     * 开始请求，异步操作，请使用{@link ParseCallback} 来处理结果
     */
    public void startParse() {
        Request request = generateRequest();
        Call call = mClient.newCall(request);
        call.enqueue(this);
    }

    /**
     * @return 生成请求
     */
    private Request generateRequest() {
        String url = mUrlProvider.getTargetUrl();

        Request.Builder builder = new Request.Builder();
        return builder.url(url)
                .get()
                .header("User-Agent", "facebookexternalhit/1.1")
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
    }
}
