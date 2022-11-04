package com.example.chatapp.utils

object Constants {
   const val PROFILE_PICTURE_SIZE = 50

    const val USER_FRIENDS ="friends"
    const val USER_COLLECTION = "users"
    const val USER_UID = "uid"
}

object NameLimits {

    const val MIN = 5

    const val MAX = 15

}
object SearchValues {

    const val MIN_LENGTH = NameLimits.MIN

    const val MAX_LENGTH = NameLimits.MAX

    /**
     * The number of milliseconds that passes after the user stopped typing, and the search begins.
     */
    const val SEARCH_DELAY: Long = 1000
}