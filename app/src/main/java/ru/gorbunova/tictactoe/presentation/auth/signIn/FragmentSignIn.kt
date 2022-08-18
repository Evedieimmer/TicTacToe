package ru.gorbunova.tictactoe.presentation.auth.signIn

import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_sign_in.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.auth.INavigateRouter
import soft.eac.appmvptemplate.views.ABaseFragment
import javax.inject.Inject

class FragmentSignIn : ABaseFragment(R.layout.fragment_sign_in), ISignInView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SignInPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

//     fun getViewId() = R.layout.fragment_sign_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignUp.setOnClickListener {
            (activity as? INavigateRouter)?.showSignUp()
//            activity?.let{
//                if(it is INavigateRouter)
//                    it.showSignUp()
//            }
        }

        btnSignIn.setOnClickListener {
            val login = "${etLogin.text}"
            val password = "${etPassword.text}"

            if (login.isEmpty() || password.isEmpty()) {
                toast(R.string.error_login_password)
                return@setOnClickListener
            }
            presenter.auth(login, password)
        }
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun goToMenu() {
        (activity as? INavigateRouter)?.goToMenuScreen()
    }

}