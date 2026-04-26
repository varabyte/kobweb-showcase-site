package com.varabyte.kobweb_showcase_site.worker

import com.varabyte.kobweb.serialization.createIOSerializer
import com.varabyte.kobweb.worker.OutputDispatcher
import com.varabyte.kobweb.worker.WorkerFactory
import com.varabyte.kobweb.worker.WorkerStrategy
import com.varabyte.kobweb_showcase_site.model.ShowcaseSite
import com.varabyte.kobweb_showcase_site.model.ShowcaseSiteResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.js.Promise

internal class FetchShowcaseWorkerFactory : WorkerFactory<String, ShowcaseSiteResponse> {

    override fun createStrategy(postOutput: OutputDispatcher<ShowcaseSiteResponse>) = WorkerStrategy<String> { input ->
        @OptIn(DelicateCoroutinesApi::class) GlobalScope.launch {
            try {
                val responsePromise = js("fetch(input)") as Promise<dynamic>
                val response = responsePromise.await()

                val textPromise = response.text() as Promise<String>
                val jsonText = textPromise.await()

                // Parse into a List first, then wrap it in our response object
                val parsedSites = Json.decodeFromString<List<ShowcaseSite>>(jsonText)
                postOutput(ShowcaseSiteResponse(sites = parsedSites))

            } catch (e: Exception) {
                console.error("Failed to fetch or parse showcase sites: ${e.message}")
                postOutput(ShowcaseSiteResponse(sites = emptyList()))
            }
        }
    }


    override fun createIOSerializer() = Json.createIOSerializer<String, ShowcaseSiteResponse>()
}