package com.fame.viewmodel

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fame.R
import com.fame.listeners.DateTimeListeners
import com.fame.model.TodoModel
import com.fame.repository.MainRepository
import com.fame.utils.PopupUtils
import kotlinx.coroutines.launch

class MainViewModel(val mainRepository: MainRepository) : ViewModel() {
    var id: Int = -1
    var tiltle = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var date = MutableLiveData<String>()
    var time = MutableLiveData<String>()
    var todoList = MutableLiveData<List<TodoModel>>()

    init {
        tiltle.value = ""
        description.value = ""
        date.value = "Select Date"
        time.value = "Select Time"
    }

    fun clickOnDate(view: View) {
        PopupUtils.showDatePicker(view.context, view as TextView, object : DateTimeListeners {
            override fun dataTimeListners(value: String) {
                date.value = value
                view.setText(date.value)
            }
        }
        )
    }

    fun clickOnTime(view: View) {
        PopupUtils.showTimePicker(view.context, view as TextView, object : DateTimeListeners {
            override fun dataTimeListners(value: String) {
                time.value = value
                view.setText(time.value )
            }
        }
        )
    }

    fun selectRecord() {
        viewModelScope.launch {
            todoList.value = mainRepository.selectTodoList()
        }
    }

    fun deleteAndSelect(todoModel: TodoModel) {
        viewModelScope.launch {
            todoList.value = mainRepository.deleteAndSelect(todoModel)
        }
    }

    fun saveOrUpdate(view: View) {
        val button = view as Button
        if (tiltle.value.isNullOrBlank()) {
            PopupUtils.getAlertMessage(view.context, view.context.getString(R.string.enter_title))
        } else if (description.value.isNullOrBlank()) {
            PopupUtils.getAlertMessage(
                view.context,
                view.context.getString(R.string.enter_description)
            )
        } else if (date.value.isNullOrBlank() || date.value.equals("Select Date", true)) {
            PopupUtils.getAlertMessage(view.context, view.context.getString(R.string.enter_date))
        } else if (time.value.isNullOrBlank() || time.value.equals("Select Time", true)) {
            PopupUtils.getAlertMessage(view.context, view.context.getString(R.string.enter_time))
        } else {
            if (button.text.toString().equals("Save", true)) {
                val user = TodoModel(null, tiltle.value, description.value, date.value, time.value)
                viewModelScope.launch {
                    todoList.value = mainRepository.saveAndSelect(user)
                }
            } else {
                viewModelScope.launch {
                    val user = TodoModel(id, tiltle.value, description.value, date.value, time.value)
                    todoList.value = mainRepository.updateAndSelect(user)
                }
            }
        }
    }

}