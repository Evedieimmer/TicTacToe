package ru.gorbunova.tictactoe.presentation.auth.signUp

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.base.ABaseFragment
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.di.DaggerAppComponent
import javax.inject.Inject

class FragmentSignUp : ABaseFragment(), ISignUpView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        btnBack.setOnClickListener {
//            activity?.let {
//                if(it is INavigateRouter)
//                    it.showSignIn()
//            }
//        }
    }

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_sign_up

//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sign_up, container, false)
//    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentSignUp().apply {
            }
    }
}