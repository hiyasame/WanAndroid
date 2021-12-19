package kim.bifrost.coldrain.wanandroid.utils

import android.content.Context
import android.widget.Toast
import kim.bifrost.coldrain.wanandroid.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * kim.bifrost.coldrain.wanandroid.utils.Candy
 * WanAndroid
 * Kt语法糖工具
 *
 * @author 寒雨
 * @since 2021/11/21 15:40
 **/
inline fun Boolean.then(func: () -> Unit): Boolean {
    if (this) func()
    return this
}

inline fun Boolean.elseThen(func: () -> Unit): Boolean {
    if (!this) func()
    return this
}

// 断言多个条件，可自定义断言成功执行的动作
inline fun assertConditions(vararg cond: Boolean, action: () -> Unit) {
    if (cond.all { it }) action()
}

fun toast(content: String, context: Context = App.context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, content, duration).show()
}

// 并发安全的toast方法
suspend fun toastConcurrent(
    content: String,
    context: Context = App.context,
    duration: Int = Toast.LENGTH_SHORT,
) {
    withContext(Dispatchers.Main) {
        Toast.makeText(context, content, duration).show()
    }
}

// 是否与其中任何一个参数相同
fun Any?.equals(vararg any: Any?): Boolean = any.any { it == this }

fun <K, V> HashMap<K, V>.computeAbsent(key: K, def: V): V {
    if (containsKey(key)) {
        return get(key)!!
    }
    put(key, def)
    return def
}