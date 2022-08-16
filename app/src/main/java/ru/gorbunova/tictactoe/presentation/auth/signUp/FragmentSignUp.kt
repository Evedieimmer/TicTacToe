package ru.gorbunova.tictactoe.presentation.auth.signUp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.btnSignUp
import ru.gorbunova.tictactoe.base.ABaseFragment
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.auth.INavigateRouter
import javax.inject.Inject

class FragmentSignUp : ABaseFragment(), ISignUpView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_sign_up

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignUp.setOnClickListener {
            val login = "${etNewLogin.text}"
            val password = "${etNewPassword.text}"

            if (login.isEmpty() || password.isEmpty()) {
                toast(R.string.error_login_password)
                return@setOnClickListener
            }
            presenter.registration(login, password)
        }
    }

    override fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showSignInScreen() {
        (activity as? INavigateRouter)?.showSignIn()
    }

}