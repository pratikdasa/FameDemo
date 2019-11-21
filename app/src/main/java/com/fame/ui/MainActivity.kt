package com.fame.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.fame.R
import com.fame.adapters.TodoListAdapter
import com.fame.databinding.ActivityMainBinding
import com.fame.factory.MainViewModelFactory
import com.fame.listeners.RvListeners
import com.fame.model.TodoModel
import com.fame.utils.PopupUtils
import com.fame.viewmodel.MainViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware, RvListeners {

    override val kodein by kodein()
    val mainViewModelFactory: MainViewModelFactory by instance()
    val viewModel: MainViewModel by viewModels {
        mainViewModelFactory
    }
    lateinit var adapter: TodoListAdapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TodoListAdapter(this)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                mainViewmodel = viewModel
                lifecycleOwner = this@MainActivity
                todoListAdapter = adapter
            }
        observerRespone()
        viewModel.selectRecord()
    }

    fun observerRespone() {
        val selectedList = Observer<List<TodoModel>> {
            Log.e("select", "" + it.size)
            viewModel.tiltle.value = ""
            viewModel.description.value = ""
            viewModel.date.value = getString(R.string.select_date)
            viewModel.time.value = getString(R.string.select_time)
            binding.btnSaveUpdate.setText(getString(R.string.save))
            adapter.saveUser(it as ArrayList<TodoModel>)
        }
        viewModel.todoList.observe(this, selectedList)
    }

    override fun onItemClick(view: View, todoModel: TodoModel) {
        if (view.id == R.id.imgDelete) {
            PopupUtils.getConfirmationDialog(this,
                getString(R.string.delete_confirmation),
                object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        viewModel.deleteAndSelect(todoModel)
                    }
                },
                object : View.OnClickListener {
                    override fun onClick(v: View?) {
                    }
                }
            )
        } else {
            viewModel.id = todoModel.id!!
            viewModel.tiltle.value = todoModel.title!!
            viewModel.description.value = todoModel.description!!
            viewModel.date.value = todoModel.date!!
            viewModel.time.value = todoModel.time!!
            binding.btnSaveUpdate.setText(getString(R.string.update))
        }
    }

}


