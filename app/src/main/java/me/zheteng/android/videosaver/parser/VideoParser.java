package me.zheteng.android.videosaver.parser;

import android.support.annotation.NonNull;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析器框架代码
 */
public class VideoParser implements Callback {
    private OkHttpClient mClient;
    private ParseCallback mCallback;
    private UrlProvider mUrlProvider;
    private Extractor mExtractor;
    private List<RequestInterceptor> mRequestInterceptors;

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
        mRequestInterceptors = new ArrayList<>();
    }

    public void addRequestInterceptor(RequestInterceptor requestInterceptor) {
        mRequestInterceptors.add(requestInterceptor);
    }

    public void removeRequestInterceptor(RequestInterceptor requestInterceptor) {
        mRequestInterceptors.remove(requestInterceptor);
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
        List<Video> list = mExtractor.extract(string);

        if (mCallback != null) {
            if (list.size() > 0) {
                mCallback.onParsed(this, list);
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
        builder = builder.url(url)
                .get()
                .header("User-Agent", "facebookexternalhit/1.1")
                .cacheControl(CacheControl.FORCE_NETWORK);
        for (RequestInterceptor interceptor : mRequestInterceptors) {
            builder = interceptor.intercept(builder);
        }

        return builder.build();
    }
}
