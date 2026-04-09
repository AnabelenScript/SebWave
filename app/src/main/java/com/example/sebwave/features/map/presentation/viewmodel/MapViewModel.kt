package com.example.sebwave.features.map.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sebwave.features.map.domain.entities.SemaphoreMarker
import com.example.sebwave.features.map.domain.usecases.GetSemaphoreMarkersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapUiState(
    val isLoading: Boolean = false,
    val markers: List<SemaphoreMarker> = emptyList(),
    val selectedMarker: SemaphoreMarker? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getSemaphoreMarkersUseCase: GetSemaphoreMarkersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState(isLoading = true))
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        loadMarkers()
    }

    fun loadMarkers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            getSemaphoreMarkersUseCase().collect { result ->
                result.fold(
                    onSuccess = { markers ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            markers = markers
                        )
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Error al cargar los semáforos"
                        )
                    }
                )
            }
        }
    }

    fun onMarkerSelected(marker: SemaphoreMarker?) {
        _uiState.value = _uiState.value.copy(selectedMarker = marker)
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
