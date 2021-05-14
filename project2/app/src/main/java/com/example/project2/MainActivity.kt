package com.example.project2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.project2.databinding.ActivityMainBinding
import com.example.project2.dialogs.CreateGameDialog
import com.example.project2.dialogs.GameDialogListener
import com.example.project2.dialogs.JoinGameDialog


class MainActivity : AppCompatActivity() , GameDialogListener {

    val TAG:String = "MainActivity"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startGameButton.setOnClickListener {
            createNewGame()
        }

        binding.joinGameButton.setOnClickListener {
            joinGame()
        }

    }

    private fun createNewGame(){
        val dlg = CreateGameDialog()
        dlg.show(supportFragmentManager,"the_CreateGameDialogFragment")

    }

    private fun joinGame(){
        val dlg = JoinGameDialog()
        dlg.show(supportFragmentManager, "the_JoinGameDialogFragment")



    }

    override fun onDialogCreateGame(player: String) {
        Log.d(TAG,player)
        GameManager.createGame(player)
    }

    override fun onDialogJoinGame(player: String, gameId: String) {
        Log.d(TAG,"$player $gameId")
        GameManager.joinGame(player, gameId)

    }

}