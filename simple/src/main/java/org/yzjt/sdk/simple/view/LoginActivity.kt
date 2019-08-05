package org.yzjt.sdk.simple.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import org.yzjt.sdk.BR
import org.yzjt.sdk.mvvm.base.BaseBindActivity
import org.yzjt.sdk.simple.R
import org.yzjt.sdk.simple.databinding.ActivityLoginBinding
import org.yzjt.sdk.simple.repository.MainRepository
import org.yzjt.sdk.simple.viewmodel.LoginViewModel

/**
 * Created by LT on 2019/8/2.
 */
class LoginActivity : BaseBindActivity<MainRepository, ActivityLoginBinding, LoginViewModel>() {

    private var progressDialog: ProgressDialog? = null

    override fun initStatusBar() {

    }

    override fun getViewModel(): LoginViewModel? = createViewModel(this, LoginViewModel::class.java)

    override fun initView() {
        mDataBinding.viewModel = mViewModel
        mDataBinding.btnLogin.setOnClickListener(this)
    }

    override fun getContentViewId(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login
    }

    override fun getVariableId(): Int {
        return BR.viewModel
    }

    override fun onSingleClick(view: View) {
        if(view == mDataBinding.btnLogin){
            // login
            mViewModel?.login(mDataBinding.etUsername.text.toString(),mDataBinding.etPassword.text.toString())
        }
    }

    override fun initViewObservable() {
        mViewModel?.let {
                viewModel ->
            viewModel.getLoginLiveData().observe(this, Observer {
                Log.e(TAG,"loginEntity:$it")
                if(!TextUtils.isEmpty(it.token)){
                    // 成功
                    Toast.makeText(mContext,"login success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(mContext,MainActivity::class.java))
                } else {
                    // 失败
                    Toast.makeText(mContext,"login failure", Toast.LENGTH_SHORT).show()
                }
            })
            viewModel.getLoadLiveData().observe(this, Observer {
                Log.e(TAG,"loadLiveData.value:$it")
                if(it){
                    if(progressDialog == null){
                        progressDialog = ProgressDialog(mContext)
                    }
                    if(!progressDialog!!.isShowing){
                        progressDialog?.setMessage("loading")
                        progressDialog?.show()
                    }
                } else {
                    if(progressDialog != null && progressDialog!!.isShowing){
                        progressDialog?.dismiss()
                    }
                }
            })
        }
    }
}