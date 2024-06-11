package com.example.owd.navigation

/**
 * Interface representing a navigation destination.
 * Defines properties for the route and screen title of the destination.
 */
interface NavDest {
    /** The route associated with the navigation destination. */
    val route: String

    /** The resource ID of the screen title associated with the navigation destination. */
    val screenTitle: Int
}
