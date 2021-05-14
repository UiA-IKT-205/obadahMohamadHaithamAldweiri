package com.example.project2

import com.example.project2.App.Companion.context
import com.example.project2.api.GameService
import com.example.project2.api.data.Game
import com.example.project2.api.data.GameState
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity


typealias GameCallback = (game: Game?) -> Unit

object GameManager {
    var me: Char = '\u0000'
    var otherplayer: Char = '\u0000'
    val TAG:String = "GameManager"
    val StartingGameState:GameState = listOf(mutableListOf('0','0','0'),mutableListOf('0','0','0'),mutableListOf('0','0','0'))

    fun createGame(player:String){

        GameService.createGame(player,StartingGameState) { game: Game?, err: Int? ->
            if (err != null) Log.d(TAG, "Error code : $err")
            else{
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra("game", game)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }
        }
    }

    fun joinGame(player:String, gameId:String){
        GameService.joinGame(player, gameId) { game: Game?, err: Int? ->
            if(err != null) Log.d(TAG, "Error code : $err") else {
                val intent = Intent(context, GameActivity::class.java)
                intent.putExtra("game", game)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent)
            }
        }
    }

    fun pollGame(gameId:String, callback:GameCallback){
        GameService.pollGame(gameId) { game: Game?, err: Int? ->
            if(err != null)Log.d(TAG, "Error code : $err") else callback(game)
        }
    }

    fun updateGame(gameId:String, state:GameState){
        GameService.updateGame(gameId, state) { game: Game?, err: Int? ->
            if(err != null)Log.d(TAG, "Error code : $err")
        }
    }

    fun winner(){
        val intent= Intent(context, winnner::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent)
    }





    fun fixPlayers1(game: Game) {
        if (game.players.size == 1) {
            me = 'X'
            otherplayer = 'O'
        } else {
            me = 'O'
            otherplayer = 'X'
        }
    }
}






