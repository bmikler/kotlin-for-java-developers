package board

import java.lang.IllegalArgumentException

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)


open class SquareBoardImpl(final override val width: Int) : SquareBoard {

    private val cells: Array<Array<Cell>> = Array(width) { row -> Array(width) { column -> Cell(row + 1, column + 1) } }

    override fun getCellOrNull(i: Int, j: Int): Cell? = cells.getOrNull(i - 1)?.getOrNull(j - 1)

    override fun getCell(i: Int, j: Int): Cell =
        getCellOrNull(i, j) ?: throw IllegalArgumentException("Cell ($i, $j) is out of the board.")

    override fun getAllCells(): Collection<Cell> = cells.flatMap { it.asList() }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
        jRange.mapNotNull { column -> getCellOrNull(i, column) }.toList()

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
        iRange.mapNotNull { row -> getCellOrNull(row, j) }.toList()

    override fun Cell.getNeighbour(direction: Direction): Cell? =
        when (direction) {
            Direction.UP -> getCellOrNull(this.i - 1, this.j)
            Direction.DOWN -> getCellOrNull(this.i + 1, this.j)
            Direction.LEFT -> getCellOrNull(this.i, this.j - 1)
            Direction.RIGHT -> getCellOrNull(this.i, this.j + 1)
        }
}

class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {

    private val map: MutableMap<Cell, T?> = mutableMapOf()

    init {
        getAllCells().forEach { cell -> map[cell] = null }
    }

    override fun get(cell: Cell): T? = map[cell]
    override fun getEmptyCells(): Collection<Cell> = map.filter { it.value == null }.keys

    override fun all(predicate: (T?) -> Boolean): Boolean = getAllCells().all { cell -> predicate(get(cell)) }

    override fun any(predicate: (T?) -> Boolean): Boolean = getAllCells().any { cell -> predicate(get(cell)) }

    override fun find(predicate: (T?) -> Boolean): Cell? =
        map.filter { predicate(it.value) }.map { it.key }.firstOrNull()

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
        map.filter { predicate(it.value) }.map { it.key }.toList()

    override fun set(cell: Cell, value: T?) {
        map[cell] = value
    }
}