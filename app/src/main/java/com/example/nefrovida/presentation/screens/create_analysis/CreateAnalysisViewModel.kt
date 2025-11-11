package com.example.nefrovida.presentation.screens.create_analysis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.usecase.CreateAnalysisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateAnalysisState(
    val name: String = "",
    val description: String = "",
    val previousRequirements: String = "",
    val generalCost: String = "",
    val communityCost: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class CreateAnalysisViewModel @Inject constructor(
    private val createAnalysisUseCase: CreateAnalysisUseCase
) : ViewModel() {

    var uiState by mutableStateOf(CreateAnalysisState())
        private set

    fun onNameChange(name: String) {
        uiState = uiState.copy(name = name)
    }

    fun onDescriptionChange(description: String) {
        uiState = uiState.copy(description = description)
    }

    fun onPreviousRequirementsChange(requirements: String) {
        uiState = uiState.copy(previousRequirements = requirements)
    }

    fun onGeneralCostChange(cost: String) {
        uiState = uiState.copy(generalCost = cost)
    }

    fun onCommunityCostChange(cost: String) {
        uiState = uiState.copy(communityCost = cost)
    }

    fun onSuccessDialogDismissed() {
        uiState = uiState.copy(success = false)
    }

    fun createAnalysis() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            val generalCost = uiState.generalCost.toDoubleOrNull()
            val communityCost = uiState.communityCost.toDoubleOrNull()

            if (generalCost == null || communityCost == null) {
                uiState = uiState.copy(isLoading = false, error = "Costo inválido")
                return@launch
            }

            val result = createAnalysisUseCase(
                name = uiState.name,
                description = uiState.description,
                previousRequirements = uiState.previousRequirements,
                generalCost = generalCost,
                communityCost = communityCost
            )

            result.onSuccess {
                uiState = CreateAnalysisState(success = true)
            }.onFailure {
                uiState = uiState.copy(isLoading = false, error = it.message)
            }
        }
    }
}