package com.example.planner.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SummaryState(
    val todaysTasks: Int = 0,
    val completedToday: Int = 0,
    val budgetUsed: Double = 0.0,
    val budgetTotal: Double = 0.0,
    val overdue: Int = 0,
    val completionRate: Int = 0, // percentage
    val weeklyCompletion: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0)
)

class HomeViewModel : ViewModel() {
    private val _summary = MutableStateFlow(SummaryState())
    val summary: StateFlow<SummaryState> = _summary.asStateFlow()
}
