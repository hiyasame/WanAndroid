package kim.bifrost.coldrain.wanandroid.repo.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.reflect.TypeToken
import kim.bifrost.coldrain.wanandroid.App
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.UserInfoData

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.UserData
 * WanAndroid
 *
 *
 * @author 寒雨
 * @since 2021/11/23 8:55
 **/
object UserData {

    var isLogged = false
    var userInfoData: UserInfoData? = null
        set(value) {
            localData.edit {
                putString("userInfoData", App.gson.toJson(value))
            }
            field = value
        }

    // 本地缓存数据
    private val localData = App.context.getSharedPreferences("userdata", Context.MODE_PRIVATE)

    init {
        isLogged = App.context.getSharedPreferences("cookie", Context.MODE_PRIVATE).getString("cookie", null) != null
        userInfoData = App.gson.fromJson(localData.getString("userInfoData", ""), UserInfoData::class.java)
    }

    // 释放
    fun releaseLocalData() {
        localData.edit {
            clear()
        }
        App.context.getSharedPreferences("cookie", Context.MODE_PRIVATE).edit {
            clear()
        }
    }

}