package kim.bifrost.coldrain.wanandroid.repo.remote

import com.google.gson.GsonBuilder
import kim.bifrost.coldrain.wanandroid.constant.Constant
import kim.bifrost.coldrain.wanandroid.repo.remote.interceptor.AddCookiesInterceptor
import kim.bifrost.coldrain.wanandroid.repo.remote.interceptor.ReceivedCookieInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.lang.Long
import java.lang.NumberFormatException


/**
 * kim.bifrost.coldrain.wanandroid.repo.remote.RetrofitHelper
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/21 22:51
 **/
object RetrofitHelper {

    private var retrofit: Retrofit? = null

    val service: ApiService by lazy { getRetrofit().create(ApiService::class.java) }

    private val gson = GsonBuilder()
        .registerTypeAdapter(Int::class.java, IntTypeAdapter())
        .create()

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHttpClient())
                .build()
        }
        return retrofit!!
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ReceivedCookieInterceptor())
            .addInterceptor(AddCookiesInterceptor())
            .build()
    }

    // 添加一个TypeAdapter处理异常状况
    // 异常就0呗, 9999+太寄吧阴间了
    // 注册和登录还给我返回不一样的值，吐了
    private class IntTypeAdapter : TypeAdapter<Int>() {
        override fun read(reader: JsonReader): Int? {
            if (reader.peek() === JsonToken.NULL) {
                reader.nextNull()
                return null
            }
            val stringValue: String = reader.nextString()
            return try {
                Integer.valueOf(stringValue)
            } catch (e: NumberFormatException) {
                null
            }
        }

        override fun write(writer: JsonWriter, value: Int?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }

}