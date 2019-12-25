package common

import java.io.File

/**
 * @author Niko Strijbol
 */
interface Problem {

    /**
     * Solve part I of the problem. This method should return the result.
     */
    fun partI(input: File): Any

    /**
     * Solve part II of the problem. This method should return the result.
     */
    fun partII(input: File): Any
}