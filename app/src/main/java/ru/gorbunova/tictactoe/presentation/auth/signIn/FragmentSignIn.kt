package ru.gorbunova.tictactoe.presentation.auth.signIn

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_sign_in.*
import ru.gorbunova.tictactoe.base.ABaseFragment
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.di.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.auth.INavigateRouter
import ru.gorbunova.tictactoe.presentation.main.GameActivity
import javax.inject.Inject

class FragmentSignIn : ABaseFragment(), ISignInView {

@Inject
@InjectPresenter
lateinit var presenter: SignInPresenter

@ProvidePresenter
fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_sign_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignUp.setOnClickListener {
            activity?.let{
                if(it is INavigateRouter)
                    it.showSignUp()
            }
        }

        btnSignIn.setOnClickListener {
//            val intent = Intent(this, GameActivity::class.java)
//            startActivity(intent)

        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentSignIn().apply {

            }
    }
}