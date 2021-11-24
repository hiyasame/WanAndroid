package kim.bifrost.coldrain.wanandroid.repo.remote.interceptor

import android.content.Context
import androidx.core.content.edit
import kim.bifrost.coldrain.wanandroid.App
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.StringBuilder

/**
 * kim.bifrost.coldrain.wanandroid.repo.remote.interceptor.CookieInterceptor
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/23 13:22
 **/
class ReceivedCookieInterceptor : Interceptor {

    private val data = App.context.getSharedPreferences("cookie", Context.MODE_PRIVATE)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers("set-cookie").isNotEmpty()) {
            val builder = StringBuffer()
            originalResponse.headers("set-cookie")
                .map { it.split(";")[0] }
                .forEach {
                    builder.append(it).append(";")
                }
            data.edit {
                putString("cookie", builder.toString())
            }
        }
        return originalResponse
    }
}