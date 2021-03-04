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
import java.io.File
import java.io.FileOutputStream
import android.os.SystemClock
import android.widget.Toast

import com.example.piano.data.Note
import kotlinx.android.synthetic.main.fragment_piano_layout.*

class PianoLayout : Fragment() {

    private var _binding:FragmentPianoLayoutBinding? = null
    private val binding get() = _binding!!

    private val whiteFullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2", "D2", "E2", "F2", "G2", "A2", "B2") // 14 her

    private val blackHalfTones = listOf("C#", "D#", "F#", "G#", "A#", "C#2", "D#2", "F#2", "G#2", "A#2") // 10 her

    private var noteSheet:MutableList<Note> = mutableListOf<Note>()

    private var recordingIsOn:Boolean  = false


    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = inflate(layoutInflater)
        val view = binding.root

        val fragmentmanagerwhite = childFragmentManager
        val fragmentTransactiontwhite = fragmentmanagerwhite.beginTransaction()





        whiteFullTones.forEach() {
            val whitePianoKey = FullTonePianoKeyFragment.newInstance(it)
            var startTimeOfNote:Long = 0

            whitePianoKey.onKeyDown = { note ->
                startTimeOfNote = SystemClock.elapsedRealtime() - timerecorderChronometer.base
                println("White piano key pressed: $note")
            }

            whitePianoKey.onKeyUp = {
                val thenotetimeduration= SystemClock.elapsedRealtime() - timerecorderChronometer.base

                val note = Note(it, startTimeOfNote, thenotetimeduration)

                noteSheet.add(note)

                println("White piano key released: $note")
            }


            fragmentTransactiontwhite.add(view.layoutforwhitepianokeys.id, whitePianoKey, "note_$it")

        }

        fragmentTransactiontwhite.commit()





        val fragmentmanagerblack = childFragmentManager
        val fragmentTransactionblack = fragmentmanagerblack.beginTransaction()


        blackHalfTones.forEach() {note ->
            val blackPianoKey = newInstance(note)
            var startTimeOfNote:Long =0


            blackPianoKey.onKeyDown = {note ->
                startTimeOfNote = SystemClock.elapsedRealtime() - timerecorderChronometer.base
                println("Black piano key down: $note")
            }


            blackPianoKey.onKeyUp = {
                println("Black piano key up: $note")
            }

            fragmentTransactionblack.add(view.layoutforblackpianokeys.id, blackPianoKey, "note_$note")

        }

        fragmentTransactionblack.commit()







        view.startStopRecordingButton.setOnClickListener {
            if(!recordingIsOn){
                noteSheet.clear()
                startRecordingTimer()
                startStopRecordingButton.text = "stop_recording"
            } else {
                stopRecordingTimer()
                startStopRecordingButton.text = "reset_the_recording"
            }
        }
        view.savingthenotesheetButton.setOnClickListener{
            var fileName = view.fileNameInput.text.toString()
            val filePath = this.activity?.getExternalFilesDir(null)
            val newtuneFile = (File(filePath, fileName))



            when {
                noteSheet.count() == 0 -> Toast.makeText(activity, "enter notes in here", Toast.LENGTH_SHORT).show()
                fileName.isEmpty() -> Toast.makeText(activity, "enter the file name here", Toast.LENGTH_SHORT).show()
                filePath == null -> Toast.makeText(activity, "it does not exist", Toast.LENGTH_SHORT).show()
                newtuneFile.exists() -> Toast.makeText(activity, "the file already exists", Toast.LENGTH_SHORT).show()



                else -> {
                    fileName = "$fileName.music"
                    FileOutputStream(newtuneFile, true).bufferedWriter().use { writer ->
                        noteSheet.forEach() {
                            writer.write("${it.toString()}\n")
                        }
                    }
                    Toast.makeText(activity, "Your file has been saved!", Toast.LENGTH_SHORT).show()
                    noteSheet.clear()
                    fileNameInput.text.clear()
                    FileOutputStream(newtuneFile).close()


                    println("lagret som: $fileName")
                    println("lagret i: $filePath/$fileName")
                }
            }
        }
        return view
    }

    private fun startRecordingTimer(){
        if (!recordingIsOn){
            timerecorderChronometer.base = SystemClock.elapsedRealtime()
            timerecorderChronometer.start()
            recordingIsOn = true
        }
    }

    private fun stopRecordingTimer(){
        if (recordingIsOn){
            timerecorderChronometer.stop()
            recordingIsOn = false
        }
    }
}

private fun String.isEmpty(): Boolean { return isEmpty() }
