package com.dluche.timetogo.ui.feature.timecounter.screen

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dluche.timetogo.R
import com.dluche.timetogo.ui.feature.timecounter.events.TimeCounterEvents
import com.dluche.timetogo.ui.feature.timecounter.state.TimeCounterState
import com.dluche.timetogo.ui.feature.timecounter.util.TimeMaskTransformation
import com.dluche.timetogo.ui.feature.timecounter.viewmodel.TimeCounterViewModel
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun TimeCounterRoute(
    modifier: Modifier = Modifier,
    viewModel: TimeCounterViewModel = koinViewModel<TimeCounterViewModel>()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()

    TimeCounterScreen(
        state = state.value,
        dispatchEvent = viewModel::dispatchEvent,
        modifier = modifier
    )
}

@ExperimentalMaterial3Api
@Composable
fun TimeCounterScreen(
    state: TimeCounterState,
    modifier: Modifier,
    dispatchEvent: (TimeCounterEvents) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.time_counter),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            )
        }

    ) { innerPadding ->
        val workTimeFocus = remember { FocusRequester() }
        val lunchTimeFocus = remember { FocusRequester() }
        val initialTimeFocus = remember { FocusRequester() }
        val endTimeFocus = remember { FocusRequester() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .focusRequester(workTimeFocus),
                    value = state.workTime,
                    onValueChange = { dispatchEvent(TimeCounterEvents.UpdateWorkTime(it)) },
                    label = { Text(stringResource(R.string.work_time)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = TimeMaskTransformation(),
                    keyboardActions = KeyboardActions(
                        onNext = { lunchTimeFocus.requestFocus() },
                        onDone = { lunchTimeFocus.requestFocus() },
                    )
                )

                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .focusRequester(lunchTimeFocus),
                    value = state.lunchInterval,
                    onValueChange = { dispatchEvent(TimeCounterEvents.UpdateLunchInterval(it)) },
                    label = { Text(stringResource(R.string.lunch_interval)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = TimeMaskTransformation(),
                    keyboardActions = KeyboardActions(
                        onNext = { initialTimeFocus.requestFocus() },
                        onDone = { initialTimeFocus.requestFocus() },
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .focusRequester(initialTimeFocus),
                    value = state.initialTime,
                    onValueChange = { dispatchEvent(TimeCounterEvents.UpdateInitialTime(it)) },
                    label = { Text(stringResource(R.string.initial_time)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = TimeMaskTransformation(),
                    keyboardActions = KeyboardActions(
                        onNext = { endTimeFocus.requestFocus() },
                        onDone = { endTimeFocus.requestFocus() }
                    ),
                )

                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .focusRequester(endTimeFocus),
                    value = state.endTime,
                    onValueChange = { dispatchEvent(TimeCounterEvents.UpdateEndTime(it)) },
                    label = { Text(stringResource(R.string.end_time)) },
                    visualTransformation = TimeMaskTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                )
            }

            CalculatedDisplayComponent(
                label = stringResource(R.string.calculated_time_to_go),
                state.calculatedLeaveTime
            )

            Spacer(modifier = Modifier.height(8.dp))

            CalculatedDisplayComponent(
                label = stringResource(R.string.real_worked_time),
                state.realWorkedTime
            )
        }
    }
}

@Composable
private fun ColumnScope.CalculatedDisplayComponent(
    label: String,
    time: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .align(Alignment.CenterHorizontally),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = label,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = time,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TimeCounterScreenPreview() {
    TimeCounterScreen(
        state = TimeCounterState(
            initialTime = "0800",
            endTime = "0000",
            lunchInterval = "0100",
            workTime = "0815",
            calculatedLeaveTime = "0000",
            realWorkedTime = "0000"
        ),
        dispatchEvent = {},
        modifier = Modifier
    )
}

