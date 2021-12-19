package kim.bifrost.coldrain.wanandroid.repo.remote.interceptor

import android.content.Context
import kim.bifrost.coldrain.wanandroid.App
import okhttp3.Interceptor
import okhttp3.Response

/**
 * kim.bifrost.coldrain.wanandroid.repo.remote.interceptor.AddCookiesInterceptor
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/23 13:48
 **/
class AddCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        App.cookieData.getString("cookie", "").apply {
            builder.addHeader("cookie", this!!)
        }
        return chain.proceed(builder.build())
    }
}