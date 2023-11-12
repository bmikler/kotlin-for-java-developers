package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val rightPositions = secret.zip(guess).count { (secretChar, guessChar) -> secretChar == guessChar }

    val commonLetters = "ABCDEF".sumOf { ch ->

        val min = Math.min(secret.count { it == ch }, guess.count { it == ch})

        min
    }
    return Evaluation(rightPositions, commonLetters - rightPositions)
}



