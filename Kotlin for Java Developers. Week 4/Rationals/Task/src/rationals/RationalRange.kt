package rationals

import java.util.Random

class RationalRange(override val start: Rational, override val endInclusive: Rational) : ClosedRange<Rational> {
    override operator fun contains(value: Rational): Boolean {
        return value >= start && value <= endInclusive
    }
}
