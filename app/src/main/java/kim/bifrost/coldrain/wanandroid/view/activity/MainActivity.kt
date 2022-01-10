package kim.bifrost.coldrain.wanandroid.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.base.BaseFragment
import kim.bifrost.coldrain.wanandroid.base.BaseVMActivity
import kim.bifrost.coldrain.wanandroid.databinding.ActivityMainBinding
import kim.bifrost.coldrain.wanandroid.repo.data.UserData
import kim.bifrost.coldrain.wanandroid.utils.then
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.view.adapter.MainViewPagerAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>(isCancelStatusBar = false) {

    private val sp by lazy { getPreferences(MODE_PRIVATE) }

    private val requestDataLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data?.data ?: return@registerForActivityResult
            sp.edit {
                putString("icon", data.toString())
            }
            val img = binding.navView.getHeaderView(0)
                .findViewById<ImageView>(R.id.userIcon)
            // 加载图片
            Glide.with(img)
                .load(data)
                .into(img)
        }
    }

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
            .apply {
                findViewById<TextView>(R.id.userName)
                    .setOnClickListener {
                        (it as TextView).text.equals("去登录").then {
                            // 启动登录Activity
                            LoginActivity.start(this@MainActivity)
                        }
                    }
                findViewById<ImageView>(R.id.userIcon)
                    .apply {
                        setOnClickListener {
                            val b = UserData.isLogged
                            if (UserData.isLogged) {
                                // 打开文件选择器
                                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                                intent.addCategory(Intent.CATEGORY_OPENABLE)
                                // 指定只显示图片
                                intent.type = "image/*"
                                requestDataLauncher.launch(intent)
                            } else {
                                LoginActivity.start(this@MainActivity)
                            }
                        }
                        if (sp.getString("icon", null) != null) {
                            val uri = sp.getString("icon", null)!!
                            Glide.with(this)
                                .load(Uri.parse(uri))
                                .into(this)
                        } else {
                            resetUserIcon()
                        }
                    }
            }
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                // 登出
                R.id.logout -> {
                    Log.d("Test", "Test")
                    viewModel.logout()
                    reloadLoginStatus()
                    resetUserIcon()
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
                // 积分
                R.id.myPoints -> {
                    // 登录则进入界面
                    if (UserData.isLogged) {
                        startActivity(Intent(this, PointActivity::class.java))
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
                if (binding.viewPager.scrollState != ViewPager2.SCROLL_STATE_IDLE) return@setOnItemSelectedListener false
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
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNav.menu.getItem(position).isChecked = true
            }
        })
        binding.viewPager.isUserInputEnabled = false
        // FAB 相关逻辑
        binding.floatingActionBtn.setOnClickListener {
            scrollToTop()
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
        lifecycleScope.launch(Dispatchers.Main) {
            binding.navView.getHeaderView(0)
                .also {
                    it.findViewById<TextView>(R.id.userInfo).text =
                        if (UserData.isLogged && UserData.userInfoData != null)
                            "等级: ${UserData.userInfoData!!.coinInfo!!.level} 排名: ${UserData.userInfoData!!.coinInfo!!.rank}"
                        else "等级: -- 排名: --"
                    it.findViewById<TextView>(R.id.userName).text =
                        if (UserData.isLogged && UserData.userInfoData != null)
                            UserData.userInfoData!!.userInfo.nickname
                        else "去登录"
                }
        }
    }

    private fun resetUserIcon() {
        sp.edit {
            clear()
        }
        val img = binding.navView.getHeaderView(0)
            .findViewById<ImageView>(R.id.userIcon)
        Glide.with(img)
            .load("https://i0.hdslb.com/bfs/face/member/noface.jpg@240w_240h_1c_1s.webp")
            .into(img)
    }

    // 将当前ViewPager2显示的fragment滑动至最顶端
    @Suppress("UNCHECKED_CAST")
    private fun scrollToTop() {
        binding.viewPager.apply {
            (supportFragmentManager.findFragmentByTag("f$currentItem") as BaseFragment<*>).scrollToTop()
        }
    }
}