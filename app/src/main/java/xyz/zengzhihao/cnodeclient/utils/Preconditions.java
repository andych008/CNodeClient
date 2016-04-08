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

package xyz.zengzhihao.cnodeclient.utils;

/**
 * @author Kela.King
 */
public class Preconditions {
    public static <T> T checkNotNull(T t) {
        if (t == null)
            throw new NullPointerException();
        return t;
    }

    public static <T> T checkNotNull(T t, Object value) {
        if (t == null)
            throw new NullPointerException(String.valueOf(value));
        return t;
    }
}