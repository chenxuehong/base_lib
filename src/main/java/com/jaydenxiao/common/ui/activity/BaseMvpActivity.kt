package com.jaydenxiao.common.ui.mvp

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import com.jaydenxiao.common.ITopPresenter
import com.jaydenxiao.common.IView
import com.jaydenxiao.common.R
import com.jaydenxiao.common.ui.activity.BaseActivity
import com.jaydenxiao.common.ui.view.linearlayout.LoadingPager

/**
 * Created by 13198 on 2018/9/19.
 * @desc BaseMvpActivity
 */

abstract class BaseMvpActivity<P : ITopPresenter> : BaseActivity(), IView<P> {

    var mLoadingPager: LoadingPager? = null
    override fun getContentView(): Int = R.layout.activtiy_base

    abstract fun getChildView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        inited()
        super.onCreate(savedInstanceState)
    }

    override fun getCtx(): Context? {
        return this
    }

    override fun initView() {
        val container = this.findViewById<FrameLayout>(R.id.activity_base_fl_container)
        container.removeAllViews()
        container.addView(layoutInflater.inflate(getChildView(), null))
        mLoadingPager = this.findViewById<LoadingPager>(R.id.activity_base_loadingPager)
        mLoadingPager?.setOnReloadListener {
            onRetry()
        }
    }

    /**
     * 重试
     */
    abstract fun onRetry()

    private fun setLoadingState(loadStatus: LoadingPager.LoadStatus) {
        mLoadingPager?.setLoadingTip(loadStatus)
    }

    override fun showLoading(msg: String) {

        setLoadingState(LoadingPager.LoadStatus.loading)
        mLoadingPager?.setTips(msg)
    }

    override fun finish(resultCode: Int) {
        finish()
    }

    override fun showLoading(srtResId: Int) {
        showLoading(resources.getString(srtResId))
    }

    override fun dismissLoading() {

        setLoadingState(LoadingPager.LoadStatus.finish)
    }

    /**
     * 网络访问错误提醒
     */
    override fun showNetErrorTip() {
        setLoadingState(LoadingPager.LoadStatus.error)
    }

    /**
     * 网络访问错误提醒
     */
    override fun showEmpty() {
        setLoadingState(LoadingPager.LoadStatus.empty)
    }

    /**
     * 网络访问错误提醒
     */
    override fun showServerErrorTip() {
        setLoadingState(LoadingPager.LoadStatus.sereverError)
    }

}
