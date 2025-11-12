package com.example.nefrovida.ui.atoms.laboratory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nefrovida.domain.model.LabAnalysis
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val formattedDate = formatter.format(date)

    return formattedDate
}

@Composable
fun Status(value: String) {
    when (value) {
        "SENT" -> Icon(
            Icons.Outlined.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = Color.Green,
        )
        "LAB" -> Icon(
            Icons.Outlined.Warning,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = Color.Red,
        )
        "PENDING" -> Icon(
            Icons.Outlined.Warning,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = Color(0x996666)
        )
        "REQUESTED" -> Icon(
            Icons.Outlined.DateRange,
            contentDescription = "Requested",
            modifier = Modifier.size(48.dp),
            tint = Color.Yellow
        )
    }
}

@Composable
fun LabAnalysisCard(
    labAnalysis: LabAnalysis
) {
    val name = labAnalysis.name + " " + labAnalysis.parent_last_name + " " + labAnalysis.maternal_last_name

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Outlined.Person,
                contentDescription = null,
                modifier = Modifier.size(46.dp)
                )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )
                Text(formatDate(labAnalysis.analysis_date))
            }
            Status(labAnalysis.analysis_status.toString())
        }
    }
}