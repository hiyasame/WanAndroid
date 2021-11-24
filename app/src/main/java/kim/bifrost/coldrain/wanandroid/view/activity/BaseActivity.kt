package kim.bifrost.coldrain.wanandroid.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

/**
 * kim.bifrost.coldrain.wanandroid.view.activity.BaseActivity
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/21 1:55
 **/
abstract class BaseActivity<T : ViewDataBinding>() : AppCompatActivity() {

    protected val binding: T by lazy {
        getViewBinding()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    abstract fun getViewBinding(): T

}