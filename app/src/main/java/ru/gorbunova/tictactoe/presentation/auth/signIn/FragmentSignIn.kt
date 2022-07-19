package ru.gorbunova.tictactoe.presentation.auth.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.base.ABaseFragment
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.di.DaggerAppComponent
import javax.inject.Inject

class FragmentSignIn : ABaseFragment(), ISignInView {

@Inject
@InjectPresenter
lateinit var presenter: SignInPresenter

@ProvidePresenter
fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
        //toast("Message")
    }

    override fun getViewId() = R.layout.fragment_sign_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sign_in, container, false)
//    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentSignIn().apply {

            }
    }
}