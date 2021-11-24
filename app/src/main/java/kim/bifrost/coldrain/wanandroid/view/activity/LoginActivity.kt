package kim.bifrost.coldrain.wanandroid.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import kim.bifrost.coldrain.wanandroid.databinding.ActivityLoginBinding
import kim.bifrost.coldrain.wanandroid.viewmodel.LoginViewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loginToolbar.toolbar.apply {
            setSupportActionBar(this)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.title = "登录"
            }
        }
        // 为什么使用viewModel？
        // 因为根据MVVM架构的结构
        // View层不能持有model层的引用
        // 把数据交给ViewModel,然后在ViewModel中编写业务相关逻辑(ViewModel中可以持有Model层引用)
        // Model层持有服务端资源/本地资源
        binding.viewModel = ViewModelProvider(this, LoginViewModel.Factory("", ""))[LoginViewModel::class.java]
        binding.buttonLogin.setOnClickListener {
            binding.viewModel?.postLogin()
        }
        // 观察登录变化
        // 在登录时销毁界面出栈并更新nav_header
        binding.viewModel?.loginLiveData?.observe(this) {
            if (it && !isFinishing && !isDestroyed) {
                finish()
            }
        }
        binding.toRegister.setOnClickListener {
            RegisterActivity.start(this)
            finish()
        }
    }

    override fun getViewBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}