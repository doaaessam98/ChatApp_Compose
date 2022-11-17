package com.example.chatapp.utils

import androidx.navigation.NavHostController
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException
import com.example.chatapp.utils.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.internal.resumeCancellableWith
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalCoroutinesApi::class, InternalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
      var res= addOnCompleteListener {
            if (it.exception != null) {

                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, null)
            }



        }
        cont.invokeOnCancellation {
            res.isCanceled
            res.addOnCanceledListener {
                cont.cancel()
            }
        }

    }


    }



fun onBackClicked(navController: NavHostController) {
    navController.popBackStack()

}


