package com.example.nefrovida.ui.molecule.laboratory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nefrovida.ui.atoms.Subtitle
import kotlinx.coroutines.launch
import java.time.LocalDate

data class AnalysisInfo(
    val analysis_id: Int,
    val name: String
)

@OptIn(ExperimentalLayoutApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LabAnalysisFilter(
    modifier: Modifier = Modifier,
    onChange: (
        startDate: LocalDate?,
        endDate: LocalDate?,
        selectedAnalysis: List<Int>,
        status: FilterStatus
    ) -> Unit,
    onClose: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var analysisList by remember { mutableStateOf(listOf<AnalysisInfo>()) }

    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedAnalysis by remember { mutableStateOf(setOf<Int>()) }
    var status by remember { mutableStateOf(FilterStatus()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            analysisList = listOf(
                AnalysisInfo(1, "Hemograma"),
                AnalysisInfo(2, "Glucosa"),
                AnalysisInfo(3, "Colesterol"),
                AnalysisInfo(4, "Triglicéridos")
            )
        }
    }

    Card(
        modifier = modifier
            .width(320.dp)
            .height(420.dp)
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Subtitle("Rango de fechas")
            Spacer(Modifier.height(4.dp))
            FlowRow(
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("De")
                OutlinedTextField(
                    value = startDate?.toString() ?: "",
                    onValueChange = { v -> startDate = if (v.isNotEmpty()) LocalDate.parse(v) else null },
                    modifier = Modifier
                        .width(140.dp)
                        .height(20.dp),
                    placeholder = { Text("dd-mm-yyyy") },
                    singleLine = true
                )
                Text("a")
                OutlinedTextField(
                    value = endDate?.toString() ?: "",
                    onValueChange = { v -> endDate = if (v.isNotEmpty()) LocalDate.parse(v) else null },
                    modifier = Modifier
                        .width(140.dp)
                        .height(20.dp),
                    placeholder = { Text("dd-mm-yyyy") },
                    singleLine = true
                )
            }

            Spacer(Modifier.height(8.dp))
            Subtitle("Estatus")
            Column {
                FilterCheckbox(
                    checked = status.sent,
                    onCheckedChange = { status = status.copy(sent = it) },
                    label = "Enviado",
                    icon = { Icon(
                        Icons.Filled.Verified,
                        contentDescription = null,
                        tint = Color.Green
                    ) }
                )
                FilterCheckbox(
                    checked = status.pending,
                    onCheckedChange = { status = status.copy(pending = it) },
                    label = "Pendiente",
                    icon = { Icon(
                        Icons.Outlined.Warning,
                        contentDescription = null,
                        tint = Color(0xFF996666)) }
                )
                FilterCheckbox(
                    checked = status.lab,
                    onCheckedChange = { status = status.copy(lab = it) },
                    label = "En laboratorio",
                    icon = { Icon(
                        Icons.Outlined.Science,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error) }
                )
                FilterCheckbox(
                    checked = status.lab,
                    onCheckedChange = { status = status.copy(lab = it) },
                    label = "Solicitado",
                    icon = { Icon(
                        Icons.Outlined.DateRange,
                        contentDescription = null,
                        tint = Color.Yellow) }
                )
            }

            Spacer(Modifier.height(8.dp))
            Subtitle("Tipo de examen" )

            LazyColumn(modifier = Modifier.height(120.dp)) {
                items(analysisList.size) { idx ->
                    val a = analysisList[idx]
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Checkbox(
                            checked = selectedAnalysis.contains(a.analysis_id),
                            onCheckedChange = { checked ->
                                selectedAnalysis =
                                    if (checked) selectedAnalysis + a.analysis_id
                                    else selectedAnalysis - a.analysis_id
                            }
                        )
                        Text(a.name)
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(Color(0xFFCCCCCC)),
                    onClick = {
                        startDate = null
                        endDate = null
                        selectedAnalysis = emptySet()
                        status = FilterStatus()
                        onChange(null, null, emptyList(), status)
                        onClose()
                    }
                ) {
                    Text("Borrar", color = Color(0xFF000000))
                }

                Button(
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(Color.Green),
                    onClick = {
                        onChange(
                            startDate,
                            endDate,
                            selectedAnalysis.toList(),
                            status
                        )
                        onClose()
                    },

                ) {
                    Text("Buscar")
                }
            }
        }
    }
}

@Composable
fun FilterCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    icon: @Composable (() -> Unit)? = null
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        if (icon != null) {
            Box(Modifier.size(20.dp), contentAlignment = Alignment.Center) { icon() }
        }
        Text(label)
    }
}

data class FilterStatus(
    val sent: Boolean = false,
    val pending: Boolean = false,
    val lab: Boolean = false
)