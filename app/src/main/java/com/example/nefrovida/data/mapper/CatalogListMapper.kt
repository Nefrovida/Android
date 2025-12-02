package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.CatalogListDto
import com.example.nefrovida.domain.model.CatalogList

fun CatalogListDto.toDomain(): CatalogList =
    CatalogList(
        appointments = appointments,
        analysis = analysis,
    )
