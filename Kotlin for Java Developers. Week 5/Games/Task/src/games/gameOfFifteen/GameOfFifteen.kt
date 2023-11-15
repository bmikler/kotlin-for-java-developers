package games.gameOfFifteen

import board.*
import games.game.Game
import java.lang.RuntimeException

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer, 4)


class GameOfFifteen(private val initializer: GameOfFifteenInitializer, width: Int) : Game, SquareBoardImpl(width) {
    val board = createGameBoard<Int?>(4)
    override fun initialize() {
        val permutation = initializer.initialPermutation
        board.getAllCells()
            .forEachIndexed { index, cell -> board[cell] = permutation.getOrNull(index) }
    }

    override fun canMove(): Boolean {
        return true
    }

    override fun hasWon(): Boolean {
        return board.getAllCells().mapNotNull { board[it] } == (1..15).toList()
    }

    override fun processMove(direction: Direction) {
        with(board) {
            val emptyCell = find { it == null }
            val cellToMove = emptyCell!!.getNeighbour(direction.reversed())

            set(emptyCell, get(cellToMove!!))
            set(cellToMove, null)
        }
    }

    override fun get(i: Int, j: Int): Int? {
        return board[Cell(i, j)]
    }
}
