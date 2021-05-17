package com.tekydevelop.radixfm.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tekydevelop.domain.model.TopAlbumsData
import com.tekydevelop.domain.usecase.TopAlbumsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TopAlbumsViewModel(private val topAlbumsUseCase: TopAlbumsUseCase) : ViewModel() {

    val topAlbums: LiveData<TopAlbumsData> get() = _topAlbums
    private val _topAlbums = MutableLiveData<TopAlbumsData>()

    val error: LiveData<String> get() = _error
    private val _error = MutableLiveData<String>()

    fun getTopAlbumData() {
        viewModelScope.launch {
            topAlbumsUseCase.getTopAlbums().onStart {
            }.catch { e ->
                _error.value = e.localizedMessage
            }.collect {
                _topAlbums.value = it
            }
        }
    }
}