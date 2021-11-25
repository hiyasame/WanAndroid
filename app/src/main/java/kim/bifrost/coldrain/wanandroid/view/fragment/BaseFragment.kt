package kim.bifrost.coldrain.wanandroid.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * kim.bifrost.coldrain.wanandroid.view.fragment.BaseFragment
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/24 17:12
 **/
abstract class BaseFragment<T: ViewDataBinding> : Fragment() {

    protected lateinit var binding: T
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container, false)
        return binding.root
    }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?, boolean: Boolean): T

}