import androidx.compose.runtime.Composable
import com.example.nefrovida.data.remote.dto.ServiceItemDto
import com.example.nefrovida.presentation.screens.catalog.comps.AnalysisCard

@Composable
fun AnalysisList(
    services: List<ServiceItemDto>,
    onReserve: (ServiceItemDto) -> Unit,
) {
    androidx.compose.foundation.lazy.LazyColumn {
        items(services.size) { index ->
            val item = services[index]

            AnalysisCard(
                item = item,
                onReserve = onReserve,
            )
        }
    }
}
