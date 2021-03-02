package com.example.piano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlin.*



class FullTonekey : Fragment() {

    private var _binding: FullTonekeyBinding? = null
    private val binding get() = _binding!!

    private lateinit var note:String

    var onKeyDown:((note:String) -> Unit)? = null
    var onKeyUp:((note:String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getString("NOTE") ?: "?"

        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentFulltonePianoKeyBinding.inflate(inflater)
        val view = binding.root

        view.whitePianoKeyButton.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> this@FulltonePianoKeyFragment.onKeyDown?.invoke(note)
                    MotionEvent.ACTION_UP -> this@FulltonePianoKeyFragment.onKeyUp?.invoke(note)
                }
                return true
            }
        })

        return view
        // return inflater.inflate(R.layout.fragment_white_piano_key, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(note: String) =
            FulltonePianoKeyFragment().apply {
                arguments = Bundle().apply {
                    putString("NOTE", note)

                }
            }
    }
}