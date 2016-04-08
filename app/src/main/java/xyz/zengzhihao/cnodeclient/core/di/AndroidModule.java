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

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;
import xyz.zengzhihao.cnodeclient.core.qualifier.ApplicationScope;
import xyz.zengzhihao.cnodeclient.core.qualifier.ClientVersionCode;
import xyz.zengzhihao.cnodeclient.core.qualifier.ClientVersionName;
import xyz.zengzhihao.cnodeclient.core.qualifier.ForApplication;
import xyz.zengzhihao.cnodeclient.utils.Preconditions;

/**
 * @author Kela.King
 */
@Module
public class AndroidModule {
    private final Context applicationContext;

    public AndroidModule(Application context) {
        this.applicationContext = Preconditions.checkNotNull(context, "Context can not be null.");
    }

    @ApplicationScope
    @Provides
    @ForApplication
    Context provideApplicationContext() {
        return applicationContext;
    }

    @ApplicationScope
    @Provides
    @ClientVersionCode
    int provideClientVersionCode(@ForApplication Context context) {
        return _getClientVersionCode(context);
    }

    @ApplicationScope
    @Provides
    @ClientVersionName
    String provideClientVersionName(@ForApplication Context context) {
        return _getClientVersionName(context);
    }

    @ApplicationScope
    @Provides
    AssetManager provideAssetManager(@ForApplication Context context) {
        return context.getAssets();
    }

    private static int _getClientVersionCode(Context context) {
        PackageInfo pi = _getPackageInfo(context);
        if (pi != null) {
            return pi.versionCode;
        } else {
            return 0;
        }
    }

    private static String _getClientVersionName(Context context) {
        PackageInfo pi = _getPackageInfo(context);
        if (pi != null) {
            return pi.versionName;
        } else {
            return "";
        }
    }

    private static PackageInfo _getPackageInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e, "Get package info failed.");
        }
        return pi;
    }
}