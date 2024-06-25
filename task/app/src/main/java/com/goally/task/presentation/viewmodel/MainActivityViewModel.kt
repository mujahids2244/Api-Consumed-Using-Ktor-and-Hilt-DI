package com.goally.task.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goally.task.domain.models.ApiState
import com.goally.task.domain.models.ErrorModel
import com.goally.task.domain.models.FilmDataModel
import com.goally.task.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _data: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val data:MutableStateFlow<ApiState> = _data

    fun getFilmData(key:String,page:Int) {
        viewModelScope.launch {
            val result = repository.getFilmData<FilmDataModel>(key,page)
            if (result.success && result.data != null) {
                _data.value = ApiState.Success(result.data)
            } else {
                val model = ErrorModel(1, "Data is not Available!")
                _data.value = ApiState.Error(model)
            }
        }
    }
}

