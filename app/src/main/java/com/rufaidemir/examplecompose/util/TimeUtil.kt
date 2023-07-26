package com.rufaidemir.examplecompose.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun stringToLocalDateTime(text:String="2022-01-06 20:30:45", patternText:String="yyyy-MM-dd HH:mm"): LocalDateTime {
    val pattern = DateTimeFormatter.ofPattern(patternText)
    return LocalDateTime.parse(text, pattern)
}
fun stringToMiliSeconds(text:String="2022-01-06 20:30:45", patternText:String="yyyy-MM-dd HH:mm"): Long {
    val pattern = DateTimeFormatter.ofPattern(patternText)
    return LocalDateTime.parse(text, pattern).toEpochSecond(ZoneOffset.UTC)
}


fun yinelemeZamanAraligiHesapla(baslangicTarihi:LocalDate, yinelemeAraligi: Int, yinelemePeriyodu: ChronoUnit): LocalDateTime {
    val now = LocalDateTime.now()
    var yinelemeTarihi = baslangicTarihi.plus(yinelemeAraligi.toLong(), yinelemePeriyodu)

    // Eğer yineleme tarihi, şu anki tarihten küçükse bir sonraki periyoda atlayalım
    if (yinelemeTarihi.isBefore(now.toLocalDate())) {
        yinelemeTarihi = yinelemeTarihi.plus(yinelemeAraligi.toLong(), yinelemePeriyodu)
    }

    return yinelemeTarihi.atStartOfDay()
}