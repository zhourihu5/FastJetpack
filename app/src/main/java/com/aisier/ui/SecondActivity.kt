package com.aisier.ui

import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.databinding.ActivitySecondBinding
import com.aisier.util.TimerShareLiveData

class SecondActivity : BaseActivity(R.layout.activity_second) {

    private val mBinding by viewBinding(ActivitySecondBinding::bind)

    override fun init() {
        mBinding.btGetUserInfo.setOnClickListener {
            TimerShareLiveData.get().cancelTimer()
        }

        TimerShareLiveData.get().observe(this) {
            Log.i("wutao--> ", "SecondActivity: $it")
        }
    }
}