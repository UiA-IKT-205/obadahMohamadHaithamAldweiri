package com.example.project2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.project2.GameManager.fixPlayers1
import com.example.project2.GameManager.me
//import com.example.project2.GameManager.fixPlayers
import com.example.project2.GameManager.winner
import com.example.project2.api.data.Game
import com.example.project2.api.data.GameState
import com.example.project2.databinding.ActivityGameBinding
import kotlinx.android.synthetic.main.activity_game.*
/*
class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    var turn: Boolean = false
    var playerTag: Char = '\u0000'
    var opponentTag: Char = '\u0000'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var game: Game? = intent.getParcelableExtra("game")


        val buttonlist = listOf(b1, b2, b3, b4, b5, b6, b7, b8, b9)
        for (b in buttonlist){
            game?.let { getState(it.state,b) }
        }
        turn= true

        //game_Id.text= game!!.gameId
        //(()) first_player.text= game.players[0].toString()

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                GameManager.pollGame(game?.gameId.toString()) { newGame: Game? ->
                    if (game?.players != newGame?.players && newGame != null)
                        with(binding) {
                           // second_player.setText(newGame.players[1] )

                        }

                    if (game?.state != newGame?.state && newGame != null) {
                        game = newGame
                        for (b in buttonlist){
                            game?.let { getState(it.state, b) }
                        }



                        clicking(game, b2, buttonlist)
                        clicking(game, b3, buttonlist)
                        clicking(game, b4, buttonlist)
                        clicking(game, b5, buttonlist)
                        clicking(game, b6, buttonlist)
                        clicking(game, b7, buttonlist)
                        clicking(game, b8, buttonlist)
                        clicking(game, b9, buttonlist)


                        turn=true

                    }

                }
                mainHandler.postDelayed(this, 1000)
            }
        })
//        for (b in buttonlist){
//
//            newGame?.let { getState(it.state, b) }
//
//        }
    }



    private fun changeState(game: Game?,btn:Button) {
        if (turn) {

            when(btn) {

                b1 -> game!!.state[0][0] = 'x'
                b2 -> game!!.state[0][1] = 'x'
                b3 -> game!!.state[0][2] = 'x'
                b4 -> game!!.state[1][0] = 'x'
                b5 -> game!!.state[1][1] = 'x'
                b6 -> game!!.state[1][2] = 'x'
                b7 -> game!!.state[2][0] = 'x'
                b8 -> game!!.state[2][1] = 'x'
                b9 -> game!!.state[2][2] = 'x'
            }


            game?.state?.let { GameManager.updateGame(game.gameId, it) }
            turn = false

        }

    }

    private fun getState(state: GameState, btn:Button) {

        when(btn){
            b1 ->btn.text=  state[0][0].toString()
            b2 -> b2.text= state[0][1].toString()
            b3 ->b3.text=  state[0][2].toString()
            b4 -> b4.text = state[1][0].toString()
            b5 -> b5.text= state[1][1].toString()
            b6 ->b6.text= state[1][2].toString()
            b7 ->b7.text=  state[2][0].toString()
            b8 -> b8.text= state[2][1].toString()
            b9 ->b9.text=  state[2][2].toString()
        }

    }

    private fun win(){
        if (b1.text== b2.text&& b2.text==b3.text&& b1.text!='0'.toString()){
            celibirating()
        }
        else if (b4.text== b5.text&& b5.text==b6.text&& b4.text!='0'.toString()){
            celibirating()
        }
        else if (b7.text== b8.text&& b8.text==b9.text&& b7.text!='0'.toString()){
            celibirating()
        }

        else if (b1.text== b4.text&& b4.text==b7.text&& b1.text!='0'.toString()){
            celibirating()
        }
        else  if (b2.text== b5.text&& b5.text==b8.text&& b2.text!='0'.toString()){
            celibirating()
        }

        else if (b3.text== b6.text&& b6.text==b9.text&& b3.text!='0'.toString()){
            celibirating()
        }

        else if (b1.text== b5.text&& b5.text==b9.text&& b1.text!='0'.toString()){
            celibirating()
        }
        else if (b3.text== b5.text&& b5.text==b7.text&& b3.text!='0'.toString()){
            celibirating()
        }

    }

    private fun celibirating(){
        val intent= Intent(this, winnner::class.java)
        startActivity(intent)
    }

    private fun clicking(game: Game?, btn:Button, buttonlist:List<Button>){

        btn.setOnClickListener {
            if (btn.text=='0'.toString()) {
                for (b in buttonlist){
                    game?.let { getState(it.state, b) }
                }

                changeState(game, btn)
                for (b in buttonlist){
                    game?.let { getState(it.state, b) }
                }


                win()
            }
            for (b in buttonlist){
                game?.let { getState(it.state, b) }
            }
            win()

        }

    }}*/






class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    var me: Char = '\u0000'
    var otherplayer: Char = '\u0000'
    var activeplayer: Boolean = false


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var game: Game? = intent.getParcelableExtra("game")


        val buttonlist = listOf(b1, b2, b3, b4, b5, b6, b7, b8, b9)

        for (button in buttonlist) {
            game?.let { updatestate(it.state, button) }
        }



        if (game != null) {
            if (game.players.size == 1) {
                me = 'X'
                otherplayer = 'O'
            } else {
                me = 'O'
                otherplayer = 'X'
            }

            fixplayers2(game)
        }



        activeplayer= true

       clicking(game, b1, buttonlist)
       clicking(game, b2, buttonlist)
       clicking(game, b3, buttonlist)
       clicking(game, b4, buttonlist)
       clicking(game, b5, buttonlist)
       clicking(game, b6, buttonlist)
       clicking(game, b7, buttonlist)
       clicking(game, b8, buttonlist)
       clicking(game, b9, buttonlist)

        //todo change location of everything

        if (game != null) {
            showgameid.text = game.gameId
        }





        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                GameManager.pollGame(game?.gameId.toString()) { thegame: Game? ->
                    if (game?.players != thegame?.players && thegame != null)
                        with(binding) {
                            otherplayer1.setText(thegame.players[1] + "\n is playing with O")

                        }

                    if (game?.state != thegame?.state && thegame != null) {
                        game = thegame
                        for (button in buttonlist) {
                            game?.let { updatestate(it.state, button) }
                        }

                        clicking(game, b1, buttonlist)
                        clicking(game, b2, buttonlist)
                        clicking(game, b3, buttonlist)
                        clicking(game, b4, buttonlist)
                        clicking(game, b5, buttonlist)
                        clicking(game, b6, buttonlist)
                        clicking(game, b7, buttonlist)
                        clicking(game, b8, buttonlist)
                        clicking(game, b9, buttonlist)

                        activeplayer= true
                    }
                }
                mainHandler.postDelayed(this, 1000)

            }
        })
        for (button in buttonlist) {
            game?.let { updatestate(it.state, button) }
        }
    }

    private fun fixplayers2(game: Game) {
        if (game.players.size == 1) {
            binding.mine.setText(game.players[0] + "\n is playing with X")
        } else {
            with(binding) {
                        mine.setText(game!!.players[0] + "\n is playing with X")
                otherplayer1.setText(game!!.players[1] + "\n is playing with O")
            }

        }

    }



    private fun alterstate(game: Game?,buttonAccessVariable:Button) {
        if (activeplayer) {
            when(buttonAccessVariable) {
                b1 -> game!!.state[0][0] = me
                b2 -> game!!.state[0][1] = me
                b3 -> game!!.state[0][2] = me
                b4 -> game!!.state[1][0] = me
                b5 -> game!!.state[1][1] = me
                b6 -> game!!.state[1][2] = me
                b7 -> game!!.state[2][0] = me
                b8 -> game!!.state[2][1] = me
                b9 -> game!!.state[2][2] = me
            }
            game?.state?.let { GameManager.updateGame(game.gameId, it) }
            activeplayer = false

        }

    }

    private fun updatestate(state: GameState, buttonAccessVariable: Button) {

            when (buttonAccessVariable) {
                b1 -> b1.text = state[0][0].toString()
                b2 -> b2.text = state[0][1].toString()
                b3 -> b3.text = state[0][2].toString()
                b4 -> b4.text = state[1][0].toString()
                b5 -> b5.text = state[1][1].toString()
                b6 -> b6.text = state[1][2].toString()
                b7 -> b7.text = state[2][0].toString()
                b8 -> b8.text = state[2][1].toString()
                b9 -> b9.text = state[2][2].toString()
            }

    }
    private fun clicking(game: Game?, buttonAccessVariable:Button, buttonlist:List<Button>){
        buttonAccessVariable.setOnClickListener {
            if (buttonAccessVariable.text=='0'.toString()) {
                for (button in buttonlist){
                    game?.let { updatestate(it.state, button) }
                }
                alterstate(game, buttonAccessVariable)
                for (button in buttonlist){
                    game?.let { updatestate(it.state, button) }
                }
                figureTheWinner()
            }
            for (button in buttonlist){
                game?.let { updatestate(it.state, button) }
            }
            figureTheWinner()
        }
    }

    private fun figureTheWinner(){
        if (b1.text== b2.text && b2.text==b3.text&& b1.text == me.toString()){
            winner()
        }
        else if (b4.text== b5.text&& b5.text== b6.text&& b4.text ==me.toString()){
            winner()
        }
        else if (b7.text== b8.text&& b8.text==b9.text&& b7.text==me.toString()){
            winner()
        }

        else if (b1.text== b4.text&& b4.text==b7.text&& b1.text==me.toString()){
            winner()
        }
        else  if (b2.text== b5.text&& b5.text==b8.text&& b2.text==me.toString()){
            winner()
        }

        else if (b3.text== b6.text&& b6.text==b9.text&& b3.text==me.toString()){
            winner()
        }

        else if (b1.text== b5.text&& b5.text==b9.text&& b1.text==me.toString()){
            winner()
        }
        else if (b3.text== b5.text&& b5.text==b7.text&& b3.text==me.toString()){
            winner()
        }

    }
}
