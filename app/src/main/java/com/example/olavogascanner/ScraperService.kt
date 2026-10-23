package com.example.olavogascanner

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class ScraperService {

    suspend fun searchOLX(query: String): List<String> = withContext(Dispatchers.IO) {
        val results = mutableListOf<String>()
        try {
            val url = "https://www.olx.pl/oferty/q-$query/"
            val doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .get()

            val elements = doc.select("h6") // tytuly ogłoszeń na OLX
            for (element in elements.take(10)) {
                results.add("OLX: " + element.text())
            }
        } catch (e: Exception) {
            results.add("Błąd pobierania OLX: ${e.localizedMessage}")
        }
        return@withContext results
    }

    suspend fun searchMetaAds(query: String): List<String> = withContext(Dispatchers.IO) {
        val results = mutableListOf<String>()
        try {
            // Meta Ads wymaga oficjalnego Graph API Tokenu do pobierania wyników w formacie JSON
            // Poniżej przygotowany szkielet logiki zapytania
            results.add("Meta Ads: Wymagany Graph API Token dla zapytania '$query'")
        } catch (e: Exception) {
            results.add("Błąd Meta Ads: ${e.localizedMessage}")
        }
        return@withContext results
    }
}
