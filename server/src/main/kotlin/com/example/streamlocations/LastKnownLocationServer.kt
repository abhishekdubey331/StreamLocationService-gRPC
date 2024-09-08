package com.example.streamlocations

import io.grpc.ServerBuilder

/**
 * A gRPC server that provides the last known location service.
 *
 * @property port The port on which the server will listen.
 * @property LastKnownLocationService The service that handles location-related requests.
 */
class LastKnownLocationServer(
    private val port: Int, lastKnownLocationService: LastKnownLocationService
) {

    private val server = ServerBuilder.forPort(port).addService(lastKnownLocationService).build()

    /**
     * Starts the gRPC server and listens on the provided port.
     * Adds a shutdown hook to stop the server gracefully when the JVM shuts down.
     */
    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@LastKnownLocationServer.stop()
                println("*** server shut down")
            },
        )
    }

    /**
     * Stops the gRPC server.
     */
    private fun stop() {
        server.shutdown()
    }

    /**
     * Blocks the current thread until the server shuts down.
     */
    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}

/**
 * Entry point for starting the LastKnownLocationServer.
 * Reads the port from the environment variable "PORT" or defaults to 9000.
 */
fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 9000
    val server = LastKnownLocationServer(port, LastKnownLocationService())
    server.start()
    server.blockUntilShutdown()
}

