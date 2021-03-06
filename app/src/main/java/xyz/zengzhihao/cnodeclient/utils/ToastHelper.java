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

import android.content.Context;
import android.widget.Toast;

/**
 * @author Kela.King
 */
public class ToastHelper {
    private Context _context;

    public ToastHelper(Context context) {
        _context = context;
    }

    public void show(String message) {
        Toast.makeText(_context, message, Toast.LENGTH_SHORT).show();
    }
}