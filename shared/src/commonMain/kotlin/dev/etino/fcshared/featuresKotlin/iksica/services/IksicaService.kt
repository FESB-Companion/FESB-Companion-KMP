package dev.etino.fcshared.featuresKotlin.iksica.services

import com.fleeksoft.ksoup.Ksoup
import dev.etino.fcshared.featuresKotlin.networking.CustomCookieStorage
import dev.etino.fcshared.featuresKotlin.networking.NetworkServiceResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol

class IksicaService(
    private val client: HttpClient,
    private val cookieStorage: CustomCookieStorage
) : IksicaServiceInterface {

    override suspend fun getStudentInfo(): NetworkServiceResult.IksicaResult {
        val response: HttpResponse = client.get("https://issp.srce.hr/student")
        val success = response.status.value in 200..299
        val body = response.bodyAsText()

        checkIfLoggedIn(body, "Failure getStudentInfo: Not logged in")?.let { return it }
        if (!success) {
            return NetworkServiceResult.IksicaResult.Failure(Throwable("Failure getAspNetSessionSAML"))
        }

        return NetworkServiceResult.IksicaResult.Success(body)
    }

    override suspend fun getReceipts(oib: String): NetworkServiceResult.IksicaResult {
        val response: HttpResponse = client.get("https://issp.srce.hr/student/studentracuni?oib=$oib")
        val success = response.status.value in 200..299
        val body = response.bodyAsText()
        val doc = Ksoup.parse(body)

        //it can happen that there are no receipts in the last 30 days so it returns the start page (https://issp.srce.hr/student)
        // with text under the student link that says
        // "- nema računa u zadnjih 30 dana."

        if (doc.selectFirst("p.text-danger")?.text()
                ?.contains("- nema računa u zadnjih 30 dana.") == true
        ) {
            return NetworkServiceResult.IksicaResult.Failure(Throwable("Failure getRacuni: nema računa u zadnjih 30 dana"))
        }

        checkIfLoggedIn(body, "Failure getRacuni: Not logged in")?.let { return it }
        if (!success) {
            return NetworkServiceResult.IksicaResult.Failure(Throwable("Failure getRacuni"))
        }

        return NetworkServiceResult.IksicaResult.Success(body)
    }

    override suspend fun getReceipt(url: String): NetworkServiceResult.IksicaResult {
        val response: HttpResponse = client.get("https://issp.srce.hr$url")
        val success = response.status.value in 200..299
        val body = response.bodyAsText()

        checkIfLoggedIn(body, "Failure getRacun: Not logged in")?.let { return it }
        if (!success) {
            return NetworkServiceResult.IksicaResult.Failure(Throwable("Failure getRacun"))
        }

        return NetworkServiceResult.IksicaResult.Success(body)
    }

    private suspend fun checkIfLoggedIn(body: String, failureMessage: String): NetworkServiceResult.IksicaResult? {
        if (Ksoup.parse(body).selectFirst("h2")?.text()
                ?.contains("Odaberi nacin prijave u sustav") == true
        ) {
            clearSession()
            return NetworkServiceResult.IksicaResult.Failure(Throwable(failureMessage))
        }
        return null
    }

    private suspend fun clearSession() = cookieStorage.clearISSPCookie()

    companion object {
        private val SCHEME = URLProtocol.HTTPS

        val targetUrl = URLBuilder().apply {
            protocol = SCHEME
            host = "issp.srce.hr"
        }.build()

    }
}


