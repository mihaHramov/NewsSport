package aaa.bbb.ccc.sportnews.di.module;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.SecureRandom;

import javax.inject.Named;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import aaa.bbb.ccc.sportnews.api.NyNewsApi;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    SecureRandom provideSecureRandom() {
        return new java.security.SecureRandom();
    }

    @Provides
    OkHttpClient provideOkhttpClient() {
        return new OkHttpClient();
    }


    @Provides
    @Named("UnsafeOkHttpClient")
    OkHttpClient provideUnsafeOkHttpClient(OkHttpClient okHttpClient, SecureRandom secureRandom, HttpLoggingInterceptor interceptor, TrustManager[] trustAllCerts) {


        try {
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, secureRandom);
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            X509TrustManager trustManager = (X509TrustManager) trustAllCerts[0];
            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .addInterceptor(interceptor)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

    @Provides
    TrustManager[] getUnsafeOkHttpClient() {
        return new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};
    }


    @Provides
    Gson getGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    Converter.Factory provideGsonConvertFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    CallAdapter.Factory provideRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    NyNewsApi getNewsApi(Converter.Factory gsonConvertFactory, CallAdapter.Factory factory, @Named("UnsafeOkHttpClient") OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(gsonConvertFactory)
                .addCallAdapterFactory(factory)
                .baseUrl(NyNewsApi.BASE_URL)
                .client(client)
                .build();//базовый url
        return retrofit.create(NyNewsApi.class);
    }
}
