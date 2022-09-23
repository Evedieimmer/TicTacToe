package ru.gorbunova.tictactoe.presentation.auth.signUp

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.btnSignUp
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.databinding.FragmentSignUpBinding
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

    private val binding: FragmentSignUpBinding get() = getViewBinding()
    private val GALLERY_REQUEST = 1
    private val REQUEST_TAKE_PHOTO = 1

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

        ivAvatar.setOnClickListener{
            activity?.let {
                AlertDialog.Builder(it)
                    .setTitle("Загрузить фото")
                    .setMessage("Выберите:")
                    .setPositiveButton("Открыть галерею") { dialog, _ ->
                        dialog.dismiss()

                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(intent,GALLERY_REQUEST)
                    }
                    .setNegativeButton("Открыть камеру") { dialog, _ ->
                        dialog.dismiss()

                        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        try {
                            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                        } catch (e: ActivityNotFoundException) {
                            e.printStackTrace()
                        }
                    }

            }

        }

//        Glide.with(this)
//            .load(avatar)
//            .centerCrop()
//            .into(binding.ivAvatar)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            ivAvatar.setImageURI((data.data))
        }
        else {
            toast("Something went wrong")
        }

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
                ivAvatar.setImageBitmap(data.extras?.get("data") as Bitmap)
        } else {
            toast("Something went wrong")
        }
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showSignInScreen() {
        (activity as? INavigateRouter)?.showSignIn()
    }

}