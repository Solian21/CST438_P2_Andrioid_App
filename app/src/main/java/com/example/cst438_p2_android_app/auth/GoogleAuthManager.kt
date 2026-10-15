package com.example.cst438_p2_android_app.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.example.cst438_p2_android_app.R
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import org.json.JSONObject

/**
 * This class is going to be a helper class for starting Google's sing in process
 */
class GoogleAuthManager(
    // This makes it so that the browser is authorized to open and communicates with Googles Oauth autorization server
    private val activity: Activity
) {
    private val authorizationService = AuthorizationService(activity)

    fun startSignIn(onError: (String) -> Unit = {}) {
        //Reads in the client ID, redirect URI, scope, and discovery URL
        //this info is found in res/raw/auth_config.json
        val config = readGoogleConfig()

        //This finds the authorization endpoints, the token endpoints, and the Oauth configuration
        AuthorizationServiceConfiguration.fetchFromUrl(Uri.parse(config.discoveryUri)) { serviceConfiguration, exception ->

            //If we are unable to find the configuration then we are going to end our process
            if (serviceConfiguration == null || exception != null) {
                onError(
                    exception?.errorDescription ?: "Unable to connect to Google. Check your internet connection and try again."
                )
                return@fetchFromUrl
            }
            // Building our authorization request -> this will be sent to google
            val authorizationRequest = AuthorizationRequest.Builder(
                serviceConfiguration, // the endpoints
                config.clientId, //the client ID
                ResponseTypeValues.CODE, // Google's authorization code
                Uri.parse(config.redirectUri) // Google's reutnr result
            )
                //this will result with
                //openid which is the connect authentication
                // email which is the users email
                // profile which gives us profile information
                .setScope(config.authorizationScope)
                .build()

            //turns our authorization request into an intent that opens googles sign in page
            val authorizationIntent =
                authorizationService.getAuthorizationRequestIntent(
                    authorizationRequest
                )

            //actually opening the sign in page and the equest code lets our MainActivity identify this later
            activity.startActivityForResult(
                authorizationIntent,
                AUTHORIZATION_REQUEST_CODE
            )
        }
    }
    /**
     * This function will handle the results returned by google after a user signs in
     */
    fun authorizationRespone(
        intent: Intent,
        onComplete: (accessToken: String?, errorMessage: String?) -> Unit
    ) {
        //read the authorization response
        val authorizationResponse = AuthorizationResponse.fromIntent(intent)
        //read the errors
        val autorizationException = AuthorizationException.fromIntent(intent)
        //if there are no authorization response then our sign in failed
        if (authorizationResponse == null) {
            onComplete(
                null,
                autorizationException?.errorDescription ?: "Google sign in did not return a response"
            )
            return
        }
        //token request using our authorization code
        val tokenRequest = authorizationResponse.createTokenExchangeRequest()
        //exchanging the authorization codes for tokens
        authorizationService.performTokenRequest(tokenRequest) {
            tokenResponse, tokenException ->
            //if the exchange fails
            if (tokenResponse == null) {
                onComplete(
                    null,
                    tokenException?.errorDescription ?: "The authorization code could not be exchanged"
                )
                return@performTokenRequest
            }
            //this is the token that will be sent to our API
            val accessToken = tokenResponse.idToken
            if (accessToken == null) {
                onComplete(null, "Google did not return an ID token. Please try again.")
                return@performTokenRequest
            }

            //gottta tell our main Activity that th eauthenticaiton succeeded
            onComplete(accessToken, null)
        }
    }

    /**
     * This function takes our auth_config.json file and turns them into Kotlin objects
     */
    private fun readGoogleConfig(): GoogleAuthConfig {
        val inputStream = activity.resources.openRawResource(R.raw.auth_config)

        val jsonText = inputStream
            .bufferedReader()
            .use { it.readText() }

        val json = JSONObject(jsonText)

        return GoogleAuthConfig(
            clientId = json.getString("client_id"),
            redirectUri = json.getString("redirect_uri"),
            authorizationScope = json.getString("authorization_scope"),
            discoveryUri = json.getString("discovery_uri")
        )
    }

    // this will relase our AppAUth resource when the Activity gets destroyed
    fun dispose() {
        authorizationService.dispose()
    }

    private data class GoogleAuthConfig(
        val clientId: String,
        val redirectUri: String,
        val authorizationScope: String,
        val discoveryUri: String
    )

    companion object {
        const val AUTHORIZATION_REQUEST_CODE = 1001
    }
}
