package ru.gorbunova.tictactoe.presentation.auth.signUp

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.common.Images
import ru.gorbunova.tictactoe.databinding.FragmentSignUpBinding
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.auth.INavigateRouter
import soft.eac.appmvptemplate.common.IPermissionAndResultProvider
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
    private val imageListener: (Tools.Image?) -> Unit = { image ->
        if (image != null) {

            picUri = image.asLocal()
            println(picUri)
            image.bytes
//            binding.ivAvatar.setImageBitmap(image.getBitmap())
            binding.ivAvatar.setImageBitmap(BitmapFactory.decodeByteArray(image.bytes, 0, image.bytes!!.size))
//            binding.ivAvatar.setImageURI(picUri)

//            Tools.loadCircleImage(
//                requireContext(), binding.ivAvatar, picUri.toString(), R.drawable.ic_avatar_placeholder
//            )

//            context?.let {
//                Glide.with(it)
//                    .load(picUri)
//                    .placeholder(R.drawable.ic_avatar_placeholder)
//                    .circleCrop()
//                    .into(binding.ivAvatar)
//            }
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

        Tools.appContext = App.appContext

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
        Tools.fromGallery(activity as IPermissionAndResultProvider, Tools.Config.Builder(Tools.appContext.packageName)
            .setOnlyBitmap(false)
            .build(), imageListener)
//        Tools.fromGallery(activity as IPermissionAndResultProvider, imageListener)
    }

    private fun openCamera() {
        Tools.fromCamera(activity as IPermissionAndResultProvider, imageListener)
    }

    private fun performCrop() {
        try {
            // Намерение для кадрирования. Не все устройства поддерживают его
            val cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(picUri, "image/*")
            cropIntent.putExtra("crop", "true")
            cropIntent.putExtra("aspectX", 1)
            cropIntent.putExtra("aspectY", 1)
            cropIntent.putExtra("outputX", 256)
            cropIntent.putExtra("outputY", 256)
            cropIntent.putExtra("return-data", true)
            startActivityForResult(cropIntent, 2)
        } catch (a: ActivityNotFoundException) {
            val errorMessage = "Извините, но ваше устройство не поддерживает кадрирование"
            toast(errorMessage)
        }
    }
}