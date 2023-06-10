package io.yeahx4.realtimemovement.dto

import java.util.concurrent.ConcurrentHashMap

data class SyncDto(
    val event: String,
    val boxes: ConcurrentHashMap<String, Box>
)
