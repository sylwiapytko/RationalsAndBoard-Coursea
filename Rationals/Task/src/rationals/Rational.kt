package rationals

import java.math.BigInteger
import javax.lang.model.element.NestingKind
import javax.xml.crypto.Data

const val DELIMETER = "/"

data class Rational(val n: BigInteger, val d: BigInteger) : Comparable<Rational> {


    fun normalise(): Rational {
        val gcd = n.gcd(d)

        val rational = Rational(n.div(gcd), d.div(gcd))
        if (d.signum() == 1) {
            return rational
        }
        return rational.negate()
    }

    fun negate(): Rational =
        Rational(n.negate(), d.negate())

    override fun toString(): String{
        val (normalisedN, normalisedD) =  this.normalise()

        return if (normalisedD.compareTo(BigInteger.ONE) == 0) "$normalisedN" else "$normalisedN/$normalisedD"
    }

    override operator fun compareTo(other: Rational): Int =
        (n * other.d - d * other.n).signum()

     operator fun plus(other: Rational): Rational {
        val newD = d * other.d
        val newN = n * other.d + d * other.n
        return Rational(newN, newD).normalise()
    }

    operator fun minus(other: Rational): Rational {
        val newD = d * other.d
        val newN = n * other.d - d * other.n
        return Rational(newN, newD).normalise()
    }

    operator fun times(other: Rational): Rational {
        val newD = d * other.d
        val newN = n * other.n
        return Rational(newN, newD).normalise()
    }

    operator fun div(other: Rational): Rational {
        val newD = d * other.n
        val newN = n * other.d
        return Rational(newN, newD).normalise()
    }

    operator fun unaryMinus(): Rational {
        return Rational(n.negate(), d).normalise()
    }


}

infix fun Int.divBy(d: Int): Rational = Rational(this.toBigInteger(), d.toBigInteger())
infix fun Long.divBy(d: Long): Rational = Rational(this.toBigInteger(), d.toBigInteger()).normalise()
infix fun BigInteger.divBy(d: BigInteger): Rational = Rational(this, d).normalise()


fun String.toRational(): Rational {
    return this.toRationalPair().normalise()
}

fun String.toRationalPair(): Rational {
    if (this.contains(DELIMETER)) {
        return Rational(this.substringBefore(DELIMETER).toBigInteger(), this.substringAfter(DELIMETER).toBigInteger())
    } else return Rational(this.substringBefore(DELIMETER).toBigInteger(), "1".toBigInteger())
}

fun main() {


    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)

}