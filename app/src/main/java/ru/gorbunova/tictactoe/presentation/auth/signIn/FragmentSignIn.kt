package ru.gorbunova.tictactoe.presentation.auth.signIn

import android.os.Bundle
import android.view.View
import android.widget.Toast
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.databinding.FragmentSignInBinding
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.auth.INavigateRouter
import soft.eac.appmvptemplate.views.ABaseFragment
import javax.inject.Inject

class FragmentSignIn : ABaseFragment(FragmentSignInBinding::class.java), ISignInView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SignInPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    private val binding: FragmentSignInBinding get() = getViewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            (activity as? INavigateRouter)?.showSignUp()
//            activity?.let{
//                if(it is INavigateRouter)
//                    it.showSignUp()
//            }
        }

        binding.btnSignIn.setOnClickListener {
            val login = "${binding.etLogin.text}"
            val password = "${binding.etPassword.text}"

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