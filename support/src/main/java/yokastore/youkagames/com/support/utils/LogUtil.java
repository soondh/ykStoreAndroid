/*
 *
 * Copyright 2015 TedXiong xiong-wei@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package yokastore.youkagames.com.support.utils;

import android.util.Log;

//import com.youkagames.gameplatform.support.BuildConfig;

/**
 * Created by Lei Xu on 2018/1/17.
 * Log utilities that can control whether print the log.
 */
public class LogUtil {
	private static final int LOG_LEVEL = 6;

	private static final int VERBOSE = 5;
	private static final int DEBUG = 4;
	private static final int INFO = 3;
	private static final int WARN = 2;
	private static final int ERROR = 1;

	public static boolean IS_SHOW_LOG = true;

	public static void init(boolean isShowLog){
		IS_SHOW_LOG = isShowLog;
	}

	public static void v(String tag, String msg) {
		if (LOG_LEVEL > VERBOSE) {
			Log.v(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (LOG_LEVEL > DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (LOG_LEVEL > DEBUG) {
			Log.d(tag, msg, tr);
		}
	}

	public static void i(String tag, String msg) {
		if (LOG_LEVEL > INFO) {
			Log.i(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (LOG_LEVEL > WARN) {
			Log.w(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (LOG_LEVEL > ERROR) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Exception e) {
		if (LOG_LEVEL > ERROR) {
			Log.e(tag, msg, e);
		}
	}

	public static void i(String msg){
		if(LOG_LEVEL > INFO){
			i("yunli_stack" , msg);
		}
	}


}
