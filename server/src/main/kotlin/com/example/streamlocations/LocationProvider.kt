package com.example.streamlocations

import com.google.type.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
/**
 * Provides location updates by generating a path based on a starting point,
 * distance, and number of steps.
 */
class LocationProvider {

    /**
     * Generates a flow of LatLng coordinates simulating a path from a starting location.
     *
     * @param startLat The starting latitude.
     * @param startLng The starting longitude.
     * @param distanceInKm The total distance to cover in kilometers.
     * @param stepCount The number of steps/locations to generate along the path.
     * @return A flow of LatLng coordinates representing the path.
     */
    fun generatePathFlow(
        startLat: Double,
        startLng: Double,
        distanceInKm: Double,
        stepCount: Int
    ): Flow<LatLng> = flow {
        val distancePerStep = distanceInKm / stepCount
        var currentLat = startLat
        var currentLng = startLng

        // Assume a straight path to the east for simplicity
        val bearing = 90.0 // Move east

        for (i in 0 until stepCount) {
            val newLocation = getNextLocation(currentLat, currentLng, bearing, distancePerStep)
            currentLat = newLocation.latitude
            currentLng = newLocation.longitude
            emit(newLocation)
            delay(500)  // Simulate car moving every 500ms
        }
    }

    /**
     * Calculates the next GPS coordinate based on the distance and bearing from the current position.
     *
     * @param lat The current latitude.
     * @param lng The current longitude.
     * @param bearing The bearing angle in degrees (0° = North, 90° = East, etc.).
     * @param distanceInKm The distance to move in kilometers.
     * @return The new LatLng position after moving the specified distance.
     */
    private fun getNextLocation(lat: Double, lng: Double, bearing: Double, distanceInKm: Double): LatLng {
        val earthRadius = 6371.0 // Earth's radius in kilometers
        val bearingRad = Math.toRadians(bearing)

        val latRad = Math.toRadians(lat)
        val lngRad = Math.toRadians(lng)

        val distanceRatio = distanceInKm / earthRadius

        val newLatRad = asin(
            sin(latRad) * cos(distanceRatio) +
                    cos(latRad) * sin(distanceRatio) * cos(bearingRad)
        )

        val newLngRad = lngRad + atan2(
            sin(bearingRad) * sin(distanceRatio) * cos(latRad),
            cos(distanceRatio) - sin(latRad) * sin(newLatRad)
        )

        val newLat = Math.toDegrees(newLatRad)
        val newLng = Math.toDegrees(newLngRad)

        return LatLng.newBuilder().setLatitude(newLat).setLongitude(newLng).build()
    }
}
