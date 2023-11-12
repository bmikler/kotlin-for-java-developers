package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger

class Rational(n: BigInteger, d: BigInteger) : Comparable<Rational>{

    private val numerator: BigInteger
    private val denominator: BigInteger

    init {
        if (d == BigInteger.ZERO)
            throw IllegalArgumentException("Denominator can't be 0")

        val gcd = n.gcd(d)

        if ((n < BigInteger.ZERO && d < BigInteger.ZERO) || (n > BigInteger.ZERO && d < BigInteger.ZERO) ) {
            numerator = n.divide(gcd).unaryMinus()
            denominator = d.divide(gcd).unaryMinus()
        } else {
            numerator = n.divide(gcd)
            denominator = d.divide(gcd)
        }
    }

    operator fun plus(other: Rational): Rational {
        if (this.denominator == other.denominator) {
            Rational(this.numerator + other.numerator, this.denominator)
        }
        val resultNumerator = this.numerator.times(other.denominator).plus(other.numerator.times(this.denominator))
        val resultDenominator = this.denominator.times(other.denominator)
        return Rational(resultNumerator, resultDenominator)
    }

    operator fun minus(other: Rational): Rational {
        if (this.denominator == other.denominator) {
            Rational(this.numerator - other.numerator, this.denominator)
        }
        val resultNumerator = this.numerator.times(other.denominator).minus(other.numerator.times(this.denominator))
        val resultDenominator = this.denominator.times(other.denominator)
        return Rational(resultNumerator, resultDenominator)
    }

    operator fun times(other: Rational): Rational {
        val resultNumerator = this.numerator.times(other.numerator)
        val resultDenominator = this.denominator.times(other.denominator)
        return Rational(resultNumerator, resultDenominator)
    }

    operator fun div(other: Rational): Rational {
        val resultNumerator = this.numerator.times(other.denominator)
        val resultDenominator = this.denominator.times(other.numerator)
        return Rational(resultNumerator, resultDenominator)
    }

    override operator fun compareTo(other: Rational): Int {
        val firstNumerator = if (this.denominator == other.denominator) this.numerator else this.numerator.times(other.denominator)
        val secondNumerator = if (this.denominator == other.denominator) other.numerator else other.numerator.times(this.denominator)

        if (firstNumerator > secondNumerator) {
            return 1
        }
        if (firstNumerator < secondNumerator) {
            return -1
        }
        return 0

    }

    operator fun unaryMinus(): Rational = Rational(numerator.unaryMinus(), denominator)

    override fun toString(): String {
        return if (denominator == BigInteger.ONE) numerator.toString() else "${numerator}/${denominator}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational

        return this.numerator == other.numerator && this.denominator == other.denominator
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }

    operator fun rangeTo(end: Rational): RationalRange = RationalRange(this, end)
}


fun String.toRational(): Rational {
    val splitedString = this.split("/").filter { it.isNotBlank() }
    val numerator = splitedString.first().toBigInteger()
    val denominator = splitedString.getOrNull(1)?.toBigInteger() ?: BigInteger.ONE
    return Rational(numerator, denominator)
}


infix fun Number.divBy(i: Number): Rational = Rational(BigInteger.valueOf(this.toLong()), BigInteger.valueOf(i.toLong()))








