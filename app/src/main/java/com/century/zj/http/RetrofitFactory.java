package com.century.zj.http;




import com.century.zj.http.config.HttpConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author yemao
 * @date 2017/4/9
 * @description 写自己的代码, 让别人说去吧!
 */

public class RetrofitFactory {

    private static RetrofitFactory mRetrofitFactory;
    private static  APIFunction mAPIFunction;
    private RetrofitFactory(){
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
//                .addInterceptor(InterceptorUtil.tokenInterceptor())//添加拦截器
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
//                .addNetworkInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return null;
//                    }
//                })
                .build();
        Retrofit mRetrofit=new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URl)
                .addConverterFactory(ScalarsConverterFactory.create())//去掉上传字符串的引号
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        mAPIFunction=mRetrofit.create(APIFunction.class);

    }

    public static RetrofitFactory getInstence(){
        if (mRetrofitFactory==null){
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitFactory();
            }

        }
        return mRetrofitFactory;
    }
    public  APIFunction API(){
        return mAPIFunction;
    }
}
