package com.aisier.ui

import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.architecture.util.startActivity
import com.aisier.architecture.util.toast
import com.aisier.bean.WxArticleBean
import com.aisier.databinding.ActivityMainBinding
import com.aisier.util.TimerShareLiveData
import com.aisier.vm.MainViewModel
import com.aisier.vm.ShareViewModel

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val mBinding by viewBinding(ActivityMainBinding::bind)
    private val mViewModel by viewModels<MainViewModel>()

    override fun init() {
        initData()
        initObserver()
        initGlobalObserver()
    }

    private fun initGlobalObserver() {
        getAppViewModelProvider().get(ShareViewModel::class.java).msgLiveData.observe(this) {
            toast("我是第二个页面的消息")
        }

        TimerShareLiveData.get().observe(this) {
            Log.i("wutao--> ", "MainActivity: $it")
        }
    }

    private fun initObserver() {
        mViewModel.wxArticleLiveData.observeState(this) {
            onSuccess { data: List<WxArticleBean>? ->
                Log.i("wutao--> ", "$data: ")
                showNetErrorPic(false)
                mBinding.tvContent.text = data?.toString()
            }

            onFailed {

            }

            onException {
                showNetErrorPic(true)
            }

            onShowLoading {
                showLoading()
            }

            onEmpty {

            }

            onComplete {
                dismissLoading()
                Log.i("wutao--> ", "onComplete: ")
            }
        }

        mViewModel.mediatorLiveDataLiveData.observe(this) {
            showNetErrorPic(false)
            if (it.testData == null) {
                mBinding.tvContent.text = it.httpData.toString()
            } else {
                mBinding.tvContent.text = it.testData.toString()
            }
        }
    }

    private fun showNetErrorPic(isShowError: Boolean) {
        mBinding.tvContent.isGone = isShowError
        mBinding.ivContent.isVisible = isShowError
    }

    private fun initData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_contain, MainFragment()).commit()
        mBinding.btnNet.setOnClickListener { mViewModel.requestNet() }
        mBinding.btnNetError1.setOnClickListener { mViewModel.requestNetError() }
        mBinding.btnMultipleDataSourcesDb.setOnClickListener {
            mBinding.tvContent.text = ""
            mViewModel.requestFromDb()
        }
        mBinding.btnMultipleDataSourcesNet.setOnClickListener {
            mBinding.tvContent.text = ""
            mViewModel.requestFromNet()
        }
        mBinding.goSecondActivity.setOnClickListener { startActivity<SecondActivity>() }
    }
}
