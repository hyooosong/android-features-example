package com.example.maskapp.utils

import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class LocationDistance {
    fun getDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double, unit: String): Double {
        val theta = lng1 - lng2
        var distance = sin(deg2rad(lat1)) * sin(deg2rad(lat2)) *
                cos(deg2rad(lat1)) * cos(deg2rad(lat2)) * cos((deg2rad(theta)))

        distance = acos(distance)
        distance = rad2deg(distance)
        distance *= 60 * 1.1515

        if (unit.equals("km")) distance *= 1.609344;
        else if(unit.equals("m")) distance *= 1609.344;

        return distance
    }

    // This function converts decimal degrees to radians
    private fun deg2rad(deg: Double): Double {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private fun rad2deg(rad: Double): Double {
        return (rad * 180 / Math.PI);
    }
}
