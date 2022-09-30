package ru.gorbunova.tictactoe.presentation.main.menu


import android.net.Uri
import moxy.InjectViewState
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
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

    fun getUser(): User {
        return userRepository.getUser() ?: throw IllegalStateException("User not find")
    }

    fun uploadAvatar(imagePath: String) {

        val file = File(imagePath)
        val body = MultipartBody.Part.createFormData("file", file.name, file.asRequestBody("image/jpg".toMediaTypeOrNull()))

            userRepository.uploadAvatar(SubRX {_, e ->
                if(e != null) {
                    e.printStackTrace()
                    viewState.showError(e.localizedMessage ?: "error")
                    return@SubRX
                }
            }, body)
        updateUserAvatar(getAvatarUrl())
    }

    fun getAvatarUrl(): String {
        return userRepository.getUploadedFile()?.path ?: ""
    }

    fun updateUserAvatar( newAvatarUrl: String? = "") {
        if (newAvatarUrl != null) {
            userRepository.updateUser(SubRX {_, e ->
                if(e != null) {
                    e.printStackTrace()
                    viewState.showError(e.localizedMessage ?: "error")
                    return@SubRX
                }
            }, newAvatarUrl)
        }
    }
}