package ru.gorbunova.tictactoe.presentation.main.menu

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.databinding.FragmentMenuBinding
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import soft.eac.appmvptemplate.common.IPermissionAndResultProvider
import soft.eac.appmvptemplate.common.Photo
import soft.eac.appmvptemplate.common.Tools
import soft.eac.appmvptemplate.views.ABaseFragment
import javax.inject.Inject


class FragmentMenu : ABaseFragment(FragmentMenuBinding::class.java), IMenuView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MenuPresenter

//    private val binding: FragmentMenuBinding get() = getViewBinding()

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    private val binding: FragmentMenuBinding get() = getViewBinding()
    private var picUri: Uri? = null
    private val imageListener: (Photo.Image?) -> Unit = { image ->
        if (image != null) {

            picUri = image.asLocal()
            println(picUri)

            Tools.loadCircleImage(
                requireContext(),
                binding.ivAvatar,
                picUri.toString(),
                R.drawable.ic_avatar_placeholder
            )

            image.path?.let { presenter.uploadAvatar(it) }

//            val avatarUrl = presenter.getAvatarUrl()
//            println(avatarUrl)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Photo.appContext = App.appContext
        val name = presenter.getUserName()
        binding.userName.text = "$name"

        val avatarUrl = presenter.getAvatarUrl()
        if(avatarUrl != "")
            println(avatarUrl)
            Tools.loadCircleImage(
                requireContext(),
                binding.ivAvatar,
                "http://10.102.100.91:8080$avatarUrl"
            )

        binding.ivAvatar.setOnClickListener {
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

        binding.btnOnline.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showNetworkGame()
            }
        }

        binding.btnOnlineBot.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showNetworkGameForBot()
            }
        }

        binding.btnWithFriend.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showLocalGame()
            }
        }

        binding.btnWithBot.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showBotGame()
            }
        }

        binding.btnRating.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showRecords()
            }
        }

        binding.btnQuitTheGame.setOnClickListener {
            activity?.let{
                presenter.logOut()
            }
        }

        binding.btnConnectToGame.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showGameWithHost()
            }
        }

        binding.btnCreateLocalServerGame.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showHostGame()
            }
        }
    }

    override fun goToAuthScreen() {
        (activity as? INavigateRouterMain)?.goToAuthScreen()
    }

    private fun openGallery() {
        Photo.fromGallery(activity as IPermissionAndResultProvider, imageListener)
    }

    private fun openCamera() {
        Photo.fromCamera(activity as IPermissionAndResultProvider, imageListener)
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}