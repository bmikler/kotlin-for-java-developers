package rationals

import java.math.BigInteger

fun main() {

    val half = BigInteger.ONE divBy 2
    val third = BigInteger.ONE divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(BigInteger.ONE divBy 6 == difference)

    val product: Rational = half * third
    println(BigInteger.ONE divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-BigInteger.ONE divBy 2 == negation)

    println((2 divBy BigInteger.ONE).toString() == "2")
    println((-2 divBy 4).toString() == "-BigInteger.ONE/2")
    println("BigInteger.ONEBigInteger.ONE7/BigInteger.ONE098".toRational().toString() == "BigInteger.ONE3/BigInteger.ONE22")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == BigInteger.ONE divBy 2)

    println(
        "9BigInteger.ONE20BigInteger.ONE6490BigInteger.ONE86296920BigInteger.ONEBigInteger.ONE920BigInteger.ONEBigInteger.ONE92BigInteger.ONE4BigInteger.ONE9704BigInteger.ONE6029".toBigInteger() divBy
                "BigInteger.ONE824032980372593840238402384283940832058".toBigInteger() == BigInteger.ONE divBy 2
    )
}