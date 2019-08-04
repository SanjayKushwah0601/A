package com.sanjay.networking.constraint

/**
 * A superclass used to represent all sorting and filtering query parameter values
 *
 * Defines the pattern used to add a sort or filter query parameter to a call
 */

abstract class SortFilter(
        var type: String,
        var value: String
) {
    override fun toString(): String {
        return type + "__" + value
    }
}