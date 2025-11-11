package com.example.nefrovida.presentation.screens.laboratory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Suppress("ktlint:standard:function-naming")

@Composable
fun ReportDetailScreen(
    onBackClick: () -> Unit,
    navController : NavController,
    modifier: Modifier = Modifier,
    title: String = "Análisis de Sangre",
    date: String = "02-11-2025",
    doctorInterpretation: String = "Lorem ipsum dolor sit amet consectetur adipiscing, elit turpis dictum dapibus montes volutpat, convallis sodales scelerisque donec pellentesque. Suspendisse habitant accumsan mollis penatibus himenaeos quisque fusce aenean pellentesque nisi, gravida dui leo scelerisque blandit eros quam litora nibh.\n" +
            "\n" +
            "Aenean pulvinar purus libero litora etiam, maecenas scelerisque vehicula mollis, primis nam iaculis ante. Platea consequat posuere a erat cubilia congue scelerisque, pulvinar fames lectus taciti convallis nisi mus, gravida porta malesuada inceptos iaculis dis. Odio nascetur sodales litora integer tincidunt habitant, per etiam accumsan sed quis, sollicitudin risus quisque dictum habitasse.\n" +
            "\n" +
            "Eget aenean luctus velit facilisi interdum habitant sapien nam, egestas dis montes ante metus penatibus dapibus suscipit pharetra, consequat a augue integer tempus quisque torquent. Pretium justo fringilla commodo pharetra urna sagittis iaculis congue fusce maecenas, donec suspendisse augue accumsan himenaeos quisque potenti mus. Cubilia senectus gravida non sagittis duis massa id feugiat vulputate fermentum rutrum curabitur lacinia varius, urna nostra morbi arcu dignissim auctor interdum vestibulum lobortis ridiculus sociosqu diam. Taciti leo class quis vivamus imperdiet parturient aenean ultrices, dictum torquent himenaeos nisl id eleifend rhoncus ullamcorper, facilisis facilisi et nulla gravida phasellus sodales.\n" +
            "\n" +
            "Parturient mollis gravida odio viverra iaculis habitant suspendisse, varius at vulputate sociis tristique hac lacus, egestas proin netus ornare augue euismod. Nec donec vitae commodo in odio, aptent ac morbi massa metus vehicula, pharetra sollicitudin erat class. Eros cum arcu massa ac mattis quisque sociosqu, pellentesque facilisis velit dis ultricies odio. Tristique egestas et lacinia tellus pellentesque suscipit auctor morbi habitasse, curae varius etiam diam senectus aliquet fringilla.\n" +
            "\n" +
            "In sapien aliquet tellus vitae eleifend duis vestibulum non, tortor imperdiet nisi cursus egestas nec senectus inceptos hendrerit, vulputate primis ante accumsan fermentum hac felis. Curabitur natoque suspendisse venenatis tincidunt bibendum magna torquent gravida, integer ante egestas in leo tempus congue proin, curae neque nunc convallis mauris dis odio. Faucibus ultrices dignissim scelerisque sociosqu rutrum praesent taciti dictum felis tincidunt himenaeos, leo parturient convallis maecenas egestas proin massa mus justo blandit dapibus, sed erat iaculis ullamcorper magnis nulla viverra tempor curabitur dui.",
    patientInterpretation: String = "Lorem ipsum dolor sit amet consectetur adipiscing, elit turpis dictum dapibus montes volutpat, convallis sodales scelerisque donec pellentesque. Suspendisse habitant accumsan mollis penatibus himenaeos quisque fusce aenean pellentesque nisi, gravida dui leo scelerisque blandit eros quam litora nibh.\n" +
            "\n" +
            "Aenean pulvinar purus libero litora etiam, maecenas scelerisque vehicula mollis, primis nam iaculis ante. Platea consequat posuere a erat cubilia congue scelerisque, pulvinar fames lectus taciti convallis nisi mus, gravida porta malesuada inceptos iaculis dis. Odio nascetur sodales litora integer tincidunt habitant, per etiam accumsan sed quis, sollicitudin risus quisque dictum habitasse.\n" +
            "\n" +
            "Eget aenean luctus velit facilisi interdum habitant sapien nam, egestas dis montes ante metus penatibus dapibus suscipit pharetra, consequat a augue integer tempus quisque torquent. Pretium justo fringilla commodo pharetra urna sagittis iaculis congue fusce maecenas, donec suspendisse augue accumsan himenaeos quisque potenti mus. Cubilia senectus gravida non sagittis duis massa id feugiat vulputate fermentum rutrum curabitur lacinia varius, urna nostra morbi arcu dignissim auctor interdum vestibulum lobortis ridiculus sociosqu diam. Taciti leo class quis vivamus imperdiet parturient aenean ultrices, dictum torquent himenaeos nisl id eleifend rhoncus ullamcorper, facilisis facilisi et nulla gravida phasellus sodales.\n" +
            "\n" +
            "Parturient mollis gravida odio viverra iaculis habitant suspendisse, varius at vulputate sociis tristique hac lacus, egestas proin netus ornare augue euismod. Nec donec vitae commodo in odio, aptent ac morbi massa metus vehicula, pharetra sollicitudin erat class. Eros cum arcu massa ac mattis quisque sociosqu, pellentesque facilisis velit dis ultricies odio. Tristique egestas et lacinia tellus pellentesque suscipit auctor morbi habitasse, curae varius etiam diam senectus aliquet fringilla.\n" +
            "\n" +
            "In sapien aliquet tellus vitae eleifend duis vestibulum non, tortor imperdiet nisi cursus egestas nec senectus inceptos hendrerit, vulputate primis ante accumsan fermentum hac felis. Curabitur natoque suspendisse venenatis tincidunt bibendum magna torquent gravida, integer ante egestas in leo tempus congue proin, curae neque nunc convallis mauris dis odio. Faucibus ultrices dignissim scelerisque sociosqu rutrum praesent taciti dictum felis tincidunt himenaeos, leo parturient convallis maecenas egestas proin massa mus justo blandit dapibus, sed erat iaculis ullamcorper magnis nulla viverra tempor curabitur dui."
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
            .padding(20.dp)
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )



        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "ARCHIVO", fontWeight = FontWeight.Bold)
                Icon(
                    imageVector = Icons.Filled.Download,
                    contentDescription = "Descargar"
                )

            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .background(color = MaterialTheme.colorScheme.tertiary, RoundedCornerShape(10.dp))
                .padding(12.dp)
        ) {
            Text(
                text = "Interpretación",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = patientInterpretation,
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .background(color = MaterialTheme.colorScheme.tertiary, RoundedCornerShape(10.dp))
                .padding(12.dp)
        ) {
            Text(
                text = "Interpretación",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = doctorInterpretation,
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

}