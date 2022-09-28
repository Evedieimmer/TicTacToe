package ru.gorbunova.tictactoe.presentation.main.menu


import android.net.Uri
import android.os.FileUtils
import moxy.InjectViewState
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import soft.eac.appmvptemplate.common.Photo
import soft.eac.appmvptemplate.common.rx.SubRX
import soft.eac.appmvptemplate.tools.ABasePresenter
import java.io.File
import javax.inject.Inject

@InjectViewState
class MenuPresenter : ABasePresenter<IMenuView> {
    @Inject
    constructor()

    @Inject
    lateinit var userRepository: UserRepository

    fun logOut(){
        userRepository.logOut(SubRX{ _, e ->
            if (e != null) {
                e.printStackTrace()
                return@SubRX
            }
            viewState.goToAuthScreen()
        })
    }

    fun getUserName(): String {
        val user = userRepository.getUser() ?: throw IllegalStateException("User not find")
        return user.login
    }

    fun uploadAvatar(imagePath: String) {

        val file = File(imagePath)
        val body = MultipartBody.Part.createFormData("image", file.name, file.asRequestBody())

            userRepository.uploadAvatar(SubRX {_, e ->
                if(e != null) {
                    e.printStackTrace()
                    viewState.showError(e.localizedMessage ?: "error")
                    return@SubRX
                }
            }, body)
    }

    fun getAvatarUrl(): String {
        return userRepository.getUploadedFile()?.path ?: ""
    }
}