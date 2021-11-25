package kim.bifrost.coldrain.wanandroid.view

import androidx.fragment.app.Fragment
import kim.bifrost.coldrain.wanandroid.utils.computeAbsent

/**
 * kim.bifrost.coldrain.wanandroid.view.FragmentRegistry
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/24 16:36
 **/
object FragmentRegistry {
    private val registry = HashMap<String, Fragment>()

    val size: Int
        get() = registry.size

    @Suppress("UNCHECKED_CAST")
    fun <T: Fragment> getInstance(targetClazz: Class<T>): T {
        return registry.computeAbsent(targetClazz.name, targetClazz.newInstance()) as T
    }
}