package technited.minds.gurumantra.ui.test

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import technited.minds.gurumantra.R
import technited.minds.gurumantra.data.local.AnswersDao
import technited.minds.gurumantra.data.local.QuestionsDao
import technited.minds.gurumantra.databinding.ActivityExamBinding
import technited.minds.gurumantra.model.AnswersItem
import technited.minds.gurumantra.model.EndTest
import technited.minds.gurumantra.model.QuestionsItem
import technited.minds.gurumantra.model.Tsd
import technited.minds.gurumantra.ui.WebPage
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.inject.Inject

@AndroidEntryPoint
class ExamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExamBinding
    private var questionList: List<QuestionsItem?>? = null
    private var isExamRunning = false
    private var questionCounter = 0
    private var questionCountTotal = 0
    private var currentQuestion: QuestionsItem? = null
    private var answered = false
    private var testTime: Int = 1
    private var testId: Int = 0
    private lateinit var type: String

    @Inject
    lateinit var userSharedPreferences: SharedPrefs

    @Inject
    lateinit var localQuestions: QuestionsDao

    @Inject
    lateinit var localAnswers: AnswersDao
    private val examViewModel: ExamViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exam)
        binding.apply {
            btnNext.setOnClickListener {
                showNextQuestion()
            }
            startStopButton.setOnClickListener {
                startExam()
            }
            btnPrev.setOnClickListener {
                showPreviousQuestion()
            }
            answerGroup.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->

                when {
                    option1.isChecked -> {
                        checkAnswer(1)
                    }
                    option2.isChecked -> {
                        checkAnswer(2)
                    }
                    option3.isChecked -> {
                        checkAnswer(3)
                    }
                    option4.isChecked -> {
                        checkAnswer(4)
                    }
                    option5.isChecked -> {
                        checkAnswer(5)
                    }
                }

            }
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        testId = intent.getStringExtra("id")?.toInt() ?: 0
        type = intent.getStringExtra("type")!!
        examViewModel.getStartTest(userSharedPreferences["id"]!!,testId.toString(),type)
        setUpObservers()
    }

    private fun setUpObservers() {
        examViewModel.testStart.observe(this, {
            when (it.status) {
                Resource.Status.LOADING -> {
//                    binding.animationView.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    val tests = it.data
                    lifecycleScope.launchWhenStarted {
                        if (tests != null) {
                            localQuestions.insertAll(tests.ques)
                        }
                    }
                    if (tests != null) {

                        binding.exam = tests.tsts
                        testTime = tests.tsts.duration *  1000 * 60
                        binding.studentName.text = userSharedPreferences["name"]
                        questionList = tests.ques
                        questionCountTotal = questionList?.size!!
                    }

                }
                Resource.Status.ERROR -> {
                    MaterialDialog(this).show {
                    title(text = "API ERROR")
                    message(text = it.message)
                    cornerRadius(16f)
                    positiveButton(text = "OK") { dialog ->
                        dialog.dismiss()
                    }
                }
//                    binding.animationView.visibility = View.GONE

                }
            }
        })
        examViewModel.testResultUrl.observe(this, {
            Log.d("asa", "setUpObservers: ${it?.data!!.resultUrl}")
            when {
                it.status == Resource.Status.SUCCESS && it.data.resultUrl.isNotEmpty() -> {
                    val intent = Intent(this@ExamActivity, WebPage::class.java)
                    intent.putExtra("url",it.data.resultUrl)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }

    private fun startExam() {
        isExamRunning = true
        showNextQuestion()
        binding.startStopButton.visibility = View.GONE
        binding.questionGroup.visibility = View.VISIBLE
        object : CountDownTimer(testTime.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.timer.text =
                    f.format(hour).toString() + ":" + f.format(min) + ":" + f.format(sec)
                if (hour == 0.toLong() && min <= 15.toLong()) {
                    binding.paperBar.background = (getDrawable(R.color.red))
                }
            }

            override fun onFinish() {
                endExam()
            }
        }.start()
        Toast.makeText(this@ExamActivity, "Test is Started", Toast.LENGTH_SHORT).show()


    }

    private fun showNextQuestion() {
        binding.apply {
            answerGroup.clearCheck()
            questionView.smoothScrollTo(0, 0)
            when {
                questionCounter < questionCountTotal -> {
                    currentQuestion = questionList?.get(questionCounter)
                    questions = currentQuestion!!
                    questionCounter++
                    if (questionCounter == questionCountTotal)
                        btnNext.text = "Finish"
                    totalQuestionCount.text = " $questionCounter/$questionCountTotal"
                    answered = false
                }
                else -> {
                    endExam()
                }
            }
        }
    }

    private fun showPreviousQuestion() {
        binding.apply {
            questionView.smoothScrollTo(0, 0);
            answerGroup.clearCheck()
            questionCounter--
            when {
                questionCounter > -1 -> {
                    currentQuestion = questionList?.get(questionCounter)
                    questions = currentQuestion!!
                    totalQuestionCount.text = " ${questionCounter + 1}/$questionCountTotal"
                    answered = false
                }
                questionCounter == questionCountTotal -> {
                    btnNext.text = "Finish"
                }
                else -> {
                    Toast.makeText(
                        this@ExamActivity,
                        "This is the first question",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun checkAnswer(answer: Int) {

        val answersItem = AnswersItem(
            currentQuestion?.qId!!,
            currentQuestion?.tsecId!!,
            answer
        )
        CoroutineScope(Dispatchers.IO).launch {
            localAnswers.insert(answersItem)
        }
        Log.d(
            "asa",
            "checkedAnswer: $answer "
        )
        answered = true
    }

    private fun endExam() {
        isExamRunning = false
        binding.timer.text = "Exam Over"
        Toast.makeText(this@ExamActivity, "Test Over", Toast.LENGTH_SHORT)
            .show()

        localAnswers.getAnswersList().observe(this, {
            if (it.isNotEmpty()) {
                submitTest(it)
            }
        })
    }

    //
    private fun submitTest(answers: List<AnswersItem>) {
        examViewModel.submitTest(
            EndTest(
                Tsd(
                    testId,
                    userSharedPreferences["id"]!!.toInt(),
//            timeTaken
                    10
                ), answers
            )
        )
    }

    override fun onBackPressed() {
        if (isExamRunning)
            Toast.makeText(this@ExamActivity, "Disabled during Test", Toast.LENGTH_SHORT)
                .show()
        else
            super.onBackPressed()
    }

}