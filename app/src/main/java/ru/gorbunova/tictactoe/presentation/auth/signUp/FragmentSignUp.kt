package ru.gorbunova.tictactoe.presentation.auth.signUp

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.databinding.FragmentSignUpBinding
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.auth.INavigateRouter
import soft.eac.appmvptemplate.common.IPermissionAndResultProvider
import soft.eac.appmvptemplate.common.Photo
import soft.eac.appmvptemplate.common.Tools
import soft.eac.appmvptemplate.views.ABaseFragment
import javax.inject.Inject


class FragmentSignUp : ABaseFragment(FragmentSignUpBinding::class.java), ISignUpView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    private val binding: FragmentSignUpBinding get() = getViewBinding()
    private var picUri: Uri? = null
    private val imageListener: (Photo.Image?) -> Unit = { image ->
        if (image != null) {

            picUri = image.asLocal()

//            binding.ivAvatar.setImageBitmap(image.getBitmap())
//            binding.ivAvatar.setImageBitmap(BitmapFactory.decodeByteArray(image.bytes, 0, image.bytes!!.size))
//            binding.ivAvatar.setImageURI(picUri)

            Tools.loadCircleImage(
                requireContext(),
                binding.ivAvatar,
                picUri.toString(),
                R.drawable.ic_avatar_placeholder
            )

//            Images.load("$picUri") {
//                it?.also {
//                    binding.ivAvatar.setImageBitmap(it)
//                }
//            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            val login = "${binding.etNewLogin.text}"
            val password = "${binding.etNewPassword.text}"
            val passwordRepeat = "${binding.etPasswordRepeat.text}"

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

        Photo.appContext = App.appContext

        binding.btnLoadAvatar.setOnClickListener {
            activity?.let { it ->
                AlertDialog.Builder(it)
                    .setTitle("Загрузить фото")
                    .setMessage("Выберите:")
                    .setPositiveButton("Открыть галерею") { dialog, _ ->
                        dialog.dismiss()

                        openGallery()
                    }
                    .setNegativeButton("Открыть камеру") { dialog, _ ->
                        dialog.dismiss()

                        openCamera()
                    }
                    .create()
                    .show()

            }
        }

    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showSignInScreen() {
        (activity as? INavigateRouter)?.showSignIn()
    }

    private fun openGallery() {
        Photo.fromGallery(activity as IPermissionAndResultProvider, imageListener)
    }

    private fun openCamera() {
        Photo.fromCamera(activity as IPermissionAndResultProvider, imageListener)
    }
}