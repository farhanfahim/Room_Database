package com.farhan.roomdatabase

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_task.*


class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        button_save.setOnClickListener {
            saveTask()
        }

    }

    private fun saveTask(){
        val task = editTextTask.text.toString().trim()
        val desc = editTextDesc.text.toString().trim()
        val finishBy = editTextFinishBy.text.toString().trim()


        if (task.isEmpty()) {
            editTextTask.error = "Task required"
            editTextTask.requestFocus()
            return
        }

        if (desc.isEmpty()) {
            editTextDesc.error = "Desc required"
            editTextDesc.requestFocus()
            return
        }

        if (finishBy.isEmpty()) {
            editTextFinishBy.error = "Finish by required"
            editTextFinishBy.requestFocus()
            return
        }

        class SaveTask :
            AsyncTask<Void?, Void?, Void?>() {
            protected override fun doInBackground(vararg params: Void?): Void? {

                //creating a task
                val taskModel = Task()
                taskModel.task = task.toString()
                taskModel.desc = desc.toString()
                taskModel.finishBy = finishBy.toString()
                taskModel.isFinished = false

                //adding to database
                DatabaseClient.getInstance(applicationContext).appDatabase
                    .taskDao()
                    .insert(taskModel)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                finish()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
            }

        }

        val st = SaveTask()
        st.execute()



    }

}