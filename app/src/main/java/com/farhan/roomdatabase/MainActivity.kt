package com.farhan.roomdatabase

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview_tasks.layoutManager = LinearLayoutManager(this)

        floating_button_add.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(intent)
        }

        getTasks()
    }

    private fun getTasks(){
        class GetTasks :
                AsyncTask<Void?, Void?, List<Task>>() {
            override fun doInBackground(vararg voids: Void?): List<Task>? {
                return DatabaseClient
                        .getInstance(applicationContext)
                        .appDatabase
                        .taskDao()
                        .all
            }

            override fun onPostExecute(tasks: List<Task>?) {
                super.onPostExecute(tasks)
                val adapter = TasksAdapter(this@MainActivity, tasks)
                recyclerview_tasks.adapter = adapter
            }

        }

        val gt = GetTasks()
        gt.execute()
    }
}