package com.zpj.shouji.market.ui.fragment.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.felix.atoast.library.AToast;
import com.zpj.fragmentation.BaseFragment;
import com.zpj.shouji.market.R;
import com.zpj.shouji.market.event.ToggleLoginModeEvent;
import com.zpj.shouji.market.manager.UserManager;
import com.zpj.shouji.market.ui.widget.input.InputView;
import com.zpj.shouji.market.ui.widget.input.PasswordInputView;
import com.zpj.shouji.market.ui.widget.SubmitView;

public class SignInFragment extends BaseFragment
        implements View.OnClickListener, UserManager.OnSignInListener {

    LinearLayout ll_go_register;
    InputView piv_account;
    PasswordInputView piv_password;
    SubmitView sv_login;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign_in;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        UserManager.getInstance().addOnSignInListener(this);
        ll_go_register = view.findViewById(R.id.ll_go_register);
        piv_account = view.findViewById(R.id.piv_login_account);
        piv_password = view.findViewById(R.id.piv_login_password);
        sv_login = view.findViewById(R.id.sv_login);

//        piv_password.setOnPwdFocusChangedListener(new PasswordInputView.OnPwdFocusChangedListener() {
//            @Override
//            public void onFocusChanged(boolean focus) {
//                mActivity.doEyeAnim(focus);
//            }
//        });

        ll_go_register.setOnClickListener(this);
        sv_login.setOnClickListener(this);

        if (getParentFragment() instanceof LoginFragment) {
            ((LoginFragment) getParentFragment()).getSoftInputHelper().moveWith(sv_login,
                    piv_account.getEditText(), piv_password.getEditText());
        }
    }

    @Override
    public void onDestroy() {
        UserManager.getInstance().removeOnSignInListener(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_go_register:
                new ToggleLoginModeEvent().post();
                break;
            case R.id.sv_login:
                String userName = piv_account.getText();
                String password = piv_password.getText();
                UserManager.getInstance().signIn(userName, password);
                AToast.normal("userName=" + userName + " password=" + password);
                break;
        }
    }

    @Override
    public void onSignInSuccess() {
        AToast.success("登录成功！");
    }

    @Override
    public void onSignInFailed(String errInfo) {
        AToast.success(errInfo);
    }
}
