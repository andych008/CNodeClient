/*
 * Copyright 2016 Kela.King
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.zengzhihao.cnodeclient.core.di;

import android.content.Context;
import android.net.Uri;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;
import xyz.zengzhihao.cnodeclient.BuildConfig;
import xyz.zengzhihao.cnodeclient.core.qualifier.ApplicationScope;
import xyz.zengzhihao.cnodeclient.core.qualifier.ForApplication;

/**
 * @author Kela.King
 */
@Module
public class NetworkModule {
    private static final long HTTP_DISK_CACHE_SIZE = 20 * 1024 * 1024;
    private static final String HTTP_DISK_CACHE_NAME = "http-cache";

    private static OkHttpClient _createOkHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(20000L, TimeUnit.MILLISECONDS)
                .writeTimeout(15000L, TimeUnit.MILLISECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .cache(new Cache(new File(context.getExternalCacheDir(),
                            HTTP_DISK_CACHE_NAME),
                            HTTP_DISK_CACHE_SIZE));
        } else {
            builder.cache(
                    new Cache(new File(context.getCacheDir(),
                            HTTP_DISK_CACHE_NAME),
                            HTTP_DISK_CACHE_SIZE));
        }
        return builder.build();
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(@ForApplication Context context) {
        return _createOkHttpClient(context);
    }

    @Provides
    @ApplicationScope
    Picasso providePicasso(@ForApplication Context context, OkHttpClient okHttpClient) {
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        builder.interceptors().clear();
        builder.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS));

        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(builder.build()))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri,
                                                  Exception exception) {
                        Timber.e(exception, "### Failed to load image: %s", uri);
                    }
                })
                .build();
        if (BuildConfig.DEBUG) {
            picasso.setIndicatorsEnabled(true);
        }
        return picasso;
    }
}