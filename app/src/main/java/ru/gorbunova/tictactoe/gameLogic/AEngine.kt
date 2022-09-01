package ru.gorbunova.tictactoe.gameLogic

import androidx.annotation.CallSuper

abstract class AEngine: IEngine {

    internal var gameState: IGameState? = null
    private val listeners = mutableListOf<(IEngine) -> Unit>()

    @CallSuper
    override fun addListener(l: (IEngine) -> Unit) {synchronized(listeners) {
        if (!listeners.contains(l))
            listeners.add(l)
        render()
    }}

    @CallSuper
    override fun removeListener(l: (IEngine) -> Unit) {synchronized(listeners) {
        listeners.remove(l)
    }}

    @CallSuper //нужна для того, чтобы обязательно был вызов из родительского метода
    override fun endGame() = synchronized(listeners) {
        listeners.clear()
    }

    override fun isGameOver() = gameState?.isGameOver() ?: false

    open fun render() {synchronized(listeners) {
        listeners.onEach { it.invoke(this) }
    }}

 }