package com.example.nefrovida.ui.molecule.laboratory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.domain.model.LabAnalysis
import com.example.nefrovida.ui.atoms.Pill
import com.example.nefrovida.ui.atoms.laboratory.LabAnalysisCard

@Composable
fun LabAnalysisListContent(
    labAnalysisList: List<LabAnalysis>,
    loadMoreItems: (Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    var page by remember { mutableStateOf(1) }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = scrollState.layoutInfo.totalItemsCount
            lastVisibleItem >= totalItems - 1
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            loadMoreItems(page)
            page += 1
        }
    }
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Pill(
                "Search",
                Icons.Outlined.Search,
                onClick = {  }
            )
            Pill(
                "Filter",
                Icons.Default.ArrowDropDown,
                onClick = {  }
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(top = 44.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = scrollState
        ) {
            itemsIndexed(labAnalysisList) { idx, labAnalysis ->
                LabAnalysisCard(labAnalysis)
            }
        }
    }

}