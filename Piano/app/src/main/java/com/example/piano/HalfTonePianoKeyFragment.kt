package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_full_tonekey.view.*
import com.example.piano.databinding.FragmentHalftonekeyBinding
import kotlinx.android.synthetic.main.fragment_halftonekey.view.*

class HalfTonePianoKeyFragment : Fragment() {

    private var _binding:FragmentHalftonekeyBinding? = null
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

        _binding = FragmentHalftonekeyBinding.inflate(inflater)
        val view = binding.root

        view.blackPianoKeyButton.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> this@HalfTonePianoKeyFragment.onKeyDown?.invoke(note)
                    MotionEvent.ACTION_UP -> this@HalfTonePianoKeyFragment.onKeyUp?.invoke(note)
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
            HalfTonePianoKeyFragment().apply {
                arguments = Bundle().apply {
                    putString("NOTE", note)

                }
            }
    }
}