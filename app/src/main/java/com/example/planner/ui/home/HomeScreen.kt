package com.example.planner.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    vm: HomeViewModel = viewModel()
) {
    val state by vm.summary.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planner", style = MaterialTheme.typography.titleLarge) },
                scrollBehavior = rememberTopAppBarState().let { null }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text("Dashboard")
                        Text("Tasks")
                        Text("Budget")
                        Text("Analytics")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Add */ }) {
                // Placeholder 
                Text("+")
            }
        }
    ) { inner ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(inner),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { GreetingHeader() }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                    StatCard(
                        title = "Today's Tasks",
                        big = state.todaysTasks.toString(),
                        subtitle = "${state.completedToday} completed",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Budget Used",
                        big = "$${state.budgetUsed.toInt()}",
                        subtitle = "${percent(state.budgetUsed, state.budgetTotal)} of total",
                        modifier = Modifier.weight(1f),
                        accent = true
                    )
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                    StatCard(
                        title = "Overdue",
                        big = state.overdue.toString(),
                        subtitle = "tasks need attention",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Completion Rate",
                        big = "${state.completionRate}%",
                        subtitle = "all time",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item { WeeklyProductivity(state.weeklyCompletion) }
            item { SectionHeader(title = "Today's Tasks", action = "+  Add") }
            item { EmptyCard(text = "No tasks for today", action = "+  Add your first task") }
            item { SectionHeader(title = "Active Budgets", action = "+  Add") }
            item { EmptyCard(text = "No budgets set", action = "+  Create a budget") }
        }
    }
}

@Composable
private fun GreetingHeader() {
    Column(Modifier.fillMaxWidth()) {
        Text("Good morning!", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text("${java.time.LocalDate.now()}" , style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun StatCard(
    title: String,
    big: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    accent: Boolean = false
) {
    val bg = if (accent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val fg = if (accent) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    Column(
        modifier
            .clip(RoundedCornerShape(16.dp))
            .background(bg)
            .padding(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.bodyMedium, color = fg)
        Spacer(Modifier.height(8.dp))
        Text(big, style = MaterialTheme.typography.headlineMedium, color = fg, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, style = MaterialTheme.typography.bodySmall, color = fg.copy(alpha = 0.85f))
    }
}

@Composable
private fun WeeklyProductivity(values: List<Int>) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Weekly Productivity", style = MaterialTheme.typography.titleMedium)
            Text("${values.sum()} tasks completed", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.height(16.dp))
        // Placeholder line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE0F2F1))
        )
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("Sat","Sun","Mon","Tue","Wed","Thu","Fri").forEach { Text(it, style = MaterialTheme.typography.labelSmall) }
        }
    }
}

@Composable
private fun SectionHeader(title: String, action: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        Text(action, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun EmptyCard(text: String, action: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFECEFF1)))
        Spacer(Modifier.height(12.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(8.dp))
        Text(action, color = MaterialTheme.colorScheme.primary)
    }
}

private fun percent(used: Double, total: Double): String {
    if (total <= 0.0) return "0% of total"
    val p = (used / total * 100).toInt()
    return "$p% of total"
}
