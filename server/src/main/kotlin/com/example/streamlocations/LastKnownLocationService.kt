package com.example.streamlocations

import com.example.streamlocations.data.location.LocationServiceGrpcKt
import com.example.streamlocations.data.location.Service
import com.example.streamlocations.data.location.userLocation
import io.grpc.Status
import io.grpc.StatusException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * A gRPC service implementation that provides the last known location of a user by simulating
 * a path flow based on the starting location and distance.
 *
 * This service extends the `LocationServiceCoroutineImplBase` from gRPC's generated code.
 * It generates a sequence of location updates (latitudes and longitudes) as the user "travels"
 * along a path from a given starting point.
 */
class LastKnownLocationService : LocationServiceGrpcKt.LocationServiceCoroutineImplBase() {

    private val locationProvider = LocationProvider()

    /**
     * Streams location updates to the client based on the userId provided in the request.
     * The function simulates a 10km path from a starting location with 1500 intermediate steps.
     *
     * @param request The gRPC request containing the userId.
     * @return A flow of `UserLocation` updates that simulates the movement along a path.
     * @throws StatusException if the request does not contain a valid userId.
     */
    override fun getLocation(request: Service.LocationRequest): Flow<Service.UserLocation> = flow {
        if (request.isInitialized && request.userId.isNotEmpty()) {
            locationProvider.generatePathFlow(
                startLat = 12.9177,  // Starting latitude (Silk Board, Bangalore)
                startLng = 77.6238,  // Starting longitude (Silk Board, Bangalore)
                distanceInKm = 10.0,  // Simulate traveling 10 km
                stepCount = 1500  // Number of steps (points) to emit in the path
            ).collect { location ->
                emit(
                    userLocation {
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                )
            }
        } else {
            // If the request is invalid, throw a gRPC StatusException with an appropriate error message
            throw StatusException(Status.INVALID_ARGUMENT.withDescription("UserId is required"))
        }
    }
}

