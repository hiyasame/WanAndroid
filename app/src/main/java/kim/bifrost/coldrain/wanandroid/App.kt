package kim.bifrost.coldrain.wanandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * kim.bifrost.coldrain.wanandroid.App
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/21 15:48
 **/
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        val cookieData: SharedPreferences by lazy { context.getSharedPreferences("cookie", Context.MODE_PRIVATE) }

        val searchHistoryData: SharedPreferences by lazy { context.getSharedPreferences("search_history", Context.MODE_PRIVATE) }

        val gson: Gson = GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create()
    }

}
