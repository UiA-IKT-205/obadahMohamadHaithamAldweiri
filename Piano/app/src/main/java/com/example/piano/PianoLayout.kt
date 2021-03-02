package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.piano.HalfTonePianoKeyFragment.Companion.newInstance
import kotlinx.android.synthetic.main.fragment_piano_layout.view.*
import com.example.piano.databinding.FragmentPianoLayoutBinding
import com.example.piano.databinding.FragmentPianoLayoutBinding.*
import kotlinx.android.synthetic.main.fragment_full_tonekey.view.*
import kotlinx.android.synthetic.main.fragment_halftonekey.view.*

class PianoLayout : Fragment() {

    private var _binding:FragmentPianoLayoutBinding? = null
    private val binding get() = _binding!!

    private val whiteFullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2", "D2", "E2", "F2", "G2", "A2", "B2") // 14 her

    private val blackHalfTones = listOf("C#", "D#", "F#", "G#", "A#", "C#2", "D#2", "F#2", "G#2", "A#2") // 10 her



    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = inflate(layoutInflater)
        val view = binding.root

        val fmwhite = childFragmentManager
        val ftwhite = fmwhite.beginTransaction()

        val fmblack = childFragmentManager
        val ftblack = fmblack.beginTransaction()

        whiteFullTones.forEach() {
            val whitePianoKey = FullTonePianoKeyFragment.newInstance(it)

            whitePianoKey.onKeyDown = {
                println("White piano key down: $it")
            }

            whitePianoKey.onKeyUp = {
                println("White piano key up: $it")
            }

            ftwhite.add(view.layoutforwhitepianokeys.id, whitePianoKey, "note_$it")

        }

        ftwhite.commit()


        blackHalfTones.forEach() {
            val blackPianoKey = HalfTonePianoKeyFragment.newInstance(it)

            blackPianoKey.onKeyDown = {
                println("Black piano key down: $it")
            }

            blackPianoKey.onKeyUp = {
                println("Black piano key up: $it")
            }

            ftblack.add(view.layoutforblackpianokeys.id, blackPianoKey, "note_$it")

        }

        ftblack.commit()


        return view



    }


}