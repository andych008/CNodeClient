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
import android.content.SharedPreferences;

import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;
import xyz.zengzhihao.cnodeclient.core.EventBus;
import xyz.zengzhihao.cnodeclient.core.qualifier.ApplicationScope;
import xyz.zengzhihao.cnodeclient.core.qualifier.ForApplication;

/**
 * @author Kela.King
 */
@Module
public class DataModule {
    @Provides
    @ApplicationScope
    SharedPreferences provideSharedPreferences(@ForApplication Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    @Provides
    @ApplicationScope
    Bus provideEventBus() {
        return EventBus.newInstance();
    }
}