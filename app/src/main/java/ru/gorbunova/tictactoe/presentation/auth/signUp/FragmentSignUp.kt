package ru.gorbunova.tictactoe.presentation.auth.signUp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.btnSignUp
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.auth.INavigateRouter
import soft.eac.appmvptemplate.views.ABaseFragment
import javax.inject.Inject

class FragmentSignUp : ABaseFragment(R.layout.fragment_sign_up), ISignUpView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

//     fun getViewId() = R.layout.fragment_sign_up

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignUp.setOnClickListener {
            val login = "${etNewLogin.text}"
            val password = "${etNewPassword.text}"
            val passwordRepeat = "${etPasswordRepeat.text}"

            if (password != passwordRepeat) {
                toast(R.string.passwords_do_not_match)
                return@setOnClickListener
            }
            if (login.isEmpty() || password.isEmpty()) {
                toast(R.string.error_login_password)
                return@setOnClickListener
            }
            presenter.registration(login, password)
        }
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showSignInScreen() {
        (activity as? INavigateRouter)?.showSignIn()
    }

}