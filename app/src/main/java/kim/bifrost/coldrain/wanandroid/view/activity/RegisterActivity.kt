package kim.bifrost.coldrain.wanandroid.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import kim.bifrost.coldrain.wanandroid.base.BaseActivity
import kim.bifrost.coldrain.wanandroid.databinding.ActivityRegisterBinding
import kim.bifrost.coldrain.wanandroid.view.viewmodel.RegisterViewModel

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = ViewModelProvider(this,
            RegisterViewModel.Factory("", "", ""))[RegisterViewModel::class.java]
        binding.registerToolbar.toolbar.apply {
            setSupportActionBar(this)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.title = "注册"
            }
        }
        binding.viewModel?.registerLiveData?.observe(this) {
            if (it && !isFinishing && !isDestroyed) {
                finish()
            }
        }
        binding.toLogin.setOnClickListener {
            LoginActivity.start(this)
            finish()
        }
        binding.buttonRegister.setOnClickListener {
            binding.viewModel?.postRegister()
        }
    }

    override fun getViewBinding(): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }
}