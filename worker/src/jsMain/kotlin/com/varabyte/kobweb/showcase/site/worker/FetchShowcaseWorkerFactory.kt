package com.varabyte.kobweb.showcase.site.worker

import com.varabyte.kobweb.browser.coroutines.asCoroutineDispatcher
import com.varabyte.kobweb.browser.http.http
import com.varabyte.kobweb.serialization.createIOSerializer
import com.varabyte.kobweb.showcase.site.model.ShowcaseSite
import com.varabyte.kobweb.showcase.site.model.ShowcaseSiteRequest
import com.varabyte.kobweb.showcase.site.model.ShowcaseSiteResponse
import com.varabyte.kobweb.worker.OutputDispatcher
import com.varabyte.kobweb.worker.WorkerFactory
import com.varabyte.kobweb.worker.WorkerStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

internal class FetchShowcaseWorkerFactory : WorkerFactory<ShowcaseSiteRequest, ShowcaseSiteResponse> {
    override fun createStrategy(postOutput: OutputDispatcher<ShowcaseSiteResponse>) = WorkerStrategy<ShowcaseSiteRequest> { input ->
        CoroutineScope(self.asCoroutineDispatcher()).launch {
            try {
                val jsonText = self.http.getBytes(input.dataUrl).decodeToString()

                // Parse into a List first, then wrap it in our response object
                val parsedSites = Json.decodeFromString<List<ShowcaseSite>>(jsonText)
                postOutput(ShowcaseSiteResponse(sites = parsedSites))
            } catch (e: Exception) {
                console.error("Failed to fetch or parse showcase sites: ${e.message}")
                postOutput(ShowcaseSiteResponse(sites = emptyList()))
            }
        }
    }


    override fun createIOSerializer() = Json.createIOSerializer<ShowcaseSiteRequest, ShowcaseSiteResponse>()
}