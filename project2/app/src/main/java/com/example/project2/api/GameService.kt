package com.example.project2.api

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.project2.App
//import com.example.project2.GameManager.state
import com.example.project2.R
import com.example.project2.api.data.Game
import com.example.project2.api.data.GameState
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject


typealias GameServiceCallback = (state: Game?, errorCode:Int? ) -> Unit

object GameService {

    val TAG:String = "GameService"
    private val context = App.context
    private val requestQue:RequestQueue = Volley.newRequestQueue(context)


    private enum class APIEndpoints(val url:String) {
        CREATE_GAME("%1s%2s%3s".format(context.getString(R.string.protocol),
            context.getString(R.string.domain),
            context.getString(R.string.base_path)))
    }


    fun createGame(playerId:String, state:GameState, callback:GameServiceCallback) {
        val url = APIEndpoints.CREATE_GAME.url
        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("state",state)

        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
            {
                val players = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                val gameId:String = it.get("gameId").toString()
                val state = Json.decodeFromString<List<MutableList<Char>>>(it.get("state").toString())

                val thegame = Game(players, gameId, state)
                Log.d(TAG, "Starting : $thegame")
                callback(thegame,null)
            }, {
                callback(null, it.networkResponse.statusCode)
            } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }
        requestQue.add(request)
    }

    fun joinGame(playerId:String, gameId:String, callback: GameServiceCallback){
        val url = APIEndpoints.CREATE_GAME.url+"/"+gameId+"/join"
        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("gameId",gameId)

        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
            {
                val players = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                val gameId:String = it.get("gameId").toString()
                val state = Json.decodeFromString<List<MutableList<Char>>>(it.get("state").toString())
                val thegame = Game(players, gameId, state)

                Log.d(TAG, "Joining : $thegame")
                callback(thegame,null)
            }, {
                callback(null, it.networkResponse.statusCode)
            } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }
        requestQue.add(request)

    }

    fun updateGame(gameId: String, gameState:GameState, callback: GameServiceCallback){

        val url = APIEndpoints.CREATE_GAME.url+"/"+gameId+"/update"
        val requestData = JSONObject()
        requestData.put("gameId", gameId)
        requestData.put("state", gameState)

        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
            {
                val players = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                val gameId:String = it.get("gameId").toString()
                val state = Json.decodeFromString<List<MutableList<Char>>>(gameState.toString())
                val thegame = Game(players, gameId, state)

                Log.d(TAG,"Updating : $thegame")
                callback(thegame,null)
            }, {
                callback(null, it.networkResponse.statusCode)
            } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }
        requestQue.add(request)
    }

    fun pollGame(gameId: String,callback:GameServiceCallback){
        val url = APIEndpoints.CREATE_GAME.url+"/"+gameId+"/poll"
        val requestData = JSONObject()
        val request = object : JsonObjectRequest(Request.Method.GET,url, requestData,
            {
                val players = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                val state = Json.decodeFromString<List<MutableList<Char>>>(it.get("state").toString())
                val thegame = Game(players, gameId, state)

                callback(thegame,null)
            }, {
                callback(null, it.networkResponse.statusCode)
            } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }
        requestQue.add(request)
    }
}


