package kim.bifrost.coldrain.wanandroid.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import kim.bifrost.coldrain.wanandroid.App
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.base.BaseActivity
import kim.bifrost.coldrain.wanandroid.base.BaseVMActivity
import kim.bifrost.coldrain.wanandroid.databinding.ActivityMainBinding
import kim.bifrost.coldrain.wanandroid.repo.data.UserData
import kim.bifrost.coldrain.wanandroid.utils.then
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.view.adapter.HomeVPAdapter
import kim.bifrost.coldrain.wanandroid.view.adapter.MainViewPagerAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>(isCancelStatusBar = false) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.mainToolbar.toolbar)
        // Toolbar
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        // 侧滑栏
        // 设置HeaderLayout中元素点击事件
        binding.navView.getHeaderView(0)
            .findViewById<TextView>(R.id.userName)
            .setOnClickListener {
                (it as TextView).text.equals("去登录").then {
                    // 启动登录Activity
                    LoginActivity.start(this)
                }
            }
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                // 登出
                R.id.logout -> {
                    Log.d("Test", "Test")
                    viewModel.logout()
                    reloadLoginStatus()
                    toast("注销成功")
                }
                // 收藏
                R.id.myCollections -> {
                    // 登录则进入界面
                    if (UserData.isLogged) {
                        startActivity(Intent(this, CollectActivity::class.java))
                    } else {
                        // 未登录则进入登录界面
                        LoginActivity.start(this)
                    }
                }
            }
            true
        }
        // BottomNav
        binding.bottomNav.apply {
            labelVisibilityMode = BottomNavigationView.LABEL_VISIBILITY_LABELED
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.bottom_nav_home -> binding.viewPager.currentItem = 0
                    R.id.bottom_nav_marketplace -> binding.viewPager.currentItem = 1
                    R.id.bottom_nav_wechat -> binding.viewPager.currentItem = 2
                    R.id.bottom_nav_system -> binding.viewPager.currentItem = 3
                    R.id.bottom_nav_project -> binding.viewPager.currentItem = 4
                }
                true
            }
        }
        // ViewPager2 相关逻辑
        binding.viewPager.adapter = MainViewPagerAdapter(this)
        binding.viewPager.registerOnPageChangeCallback (object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNav.menu.getItem(position).isChecked = true
            }
        })
        binding.viewPager.isUserInputEnabled = false
        // FAB 相关逻辑
        binding.floatingActionBtn.setOnClickListener {

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        // 回到该界面时更新登录状态信息
        reloadLoginStatus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.query -> {
                // TODO 搜索功能
            }
            // 点击home键显示滑动菜单
            android.R.id.home -> binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    // 刷新UI登录状态
    private fun reloadLoginStatus() {
        App.coroutineScope.launch(Dispatchers.Main) {
            binding.navView.getHeaderView(0)
                .also {
                    it.findViewById<TextView>(R.id.userInfo).text = if (UserData.isLogged && UserData.userInfoData != null)
                        "等级: ${UserData.userInfoData!!.coinInfo!!.level} 排名: ${UserData.userInfoData!!.coinInfo!!.rank}"
                    else "等级: -- 排名: --"
                    it.findViewById<TextView>(R.id.userName).text = if (UserData.isLogged && UserData.userInfoData != null)
                        UserData.userInfoData!!.userInfo.nickname
                    else "去登录"
                }
        }
    }

    // 将当前ViewPager2显示的fragment滑动至最顶端
    private fun scrollToTop() {

    }
}