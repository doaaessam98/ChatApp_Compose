package com.example.chatapp.utils

import androidx.navigation.NavHostController
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.InternalCoroutinesApi

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


