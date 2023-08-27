package com.example.quizapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.adapter.OptionAdapter
import com.example.quizapp.models.Question
import com.example.quizapp.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_question.btnNext
import kotlinx.android.synthetic.main.activity_question.btnPrevious
import kotlinx.android.synthetic.main.activity_question.btnSubmit
import kotlinx.android.synthetic.main.activity_question.description
import kotlinx.android.synthetic.main.activity_question.optionList

class QuestionActivity : AppCompatActivity() {
    var quizzes : MutableList<Quiz>? =null
    var questions : MutableMap<String,Question>? =null
    var index=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setUpFirestore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        btnPrevious.setOnClickListener {
            index--
            bindViews()
        }
        btnNext.setOnClickListener {
            index++
            bindViews()
        }
        btnSubmit.setOnClickListener {
            Log.d("FINALQUIZ",questions.toString())
            val intent = Intent(this,ResultActivity::class.java)
            val json = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ",json)
            startActivity(intent)
        }
    }

    private fun setUpFirestore() {
        val firestore= FirebaseFirestore.getInstance()
        val date =intent.getStringExtra("DATE")
        if(date!=null){
            val collectionReference=firestore.collection("quizzes").whereEqualTo("title",date)
                .get()
                .addOnSuccessListener {
                    if (it!=null && !it.isEmpty){
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindViews()
                    }
                }
        }
    }

    private fun bindViews() {
        btnPrevious.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        btnNext.visibility = View.GONE

        if(index==1){               //first Question
            btnNext.visibility=View.VISIBLE
        }
        else if(index== questions!!.size){             //Last Question
            btnSubmit.visibility=View.VISIBLE
        }
        else{                                               // middle questions
            btnPrevious.visibility= View.VISIBLE
            btnNext.visibility=View.VISIBLE
        }

        val question = questions!!["questions $index"]
        question?.let{
            description.text = it.description
            val optionAdapter = OptionAdapter(this,it)
            optionList.layoutManager= LinearLayoutManager(this)
            optionList.adapter=optionAdapter
            optionList.setHasFixedSize(true)
        }

    }
}