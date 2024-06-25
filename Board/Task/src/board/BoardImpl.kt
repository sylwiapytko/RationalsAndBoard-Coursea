package board

import board.Direction.*


open class SquareBoardImpl(override val width: Int) : SquareBoard {

    val cells: Collection<Cell>

    init {
        cells = mutableListOf<Cell>()
        for (i in 1..width) {
            for (j in 1..width)
                cells.add(Cell(i, j))
        }
    }

    override fun getAllCells(): Collection<Cell> {
        return cells
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return cells.find { it == Cell(i, j) }
    }

    override fun getCell(i: Int, j: Int): Cell {
        return getCellOrNull(i, j) ?: throw IllegalArgumentException()

    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        if (iRange.last > iRange.first)
            return cells.filter { it.j == j && it.i in iRange }
        else return cells.filter { it.j == j && it.i in iRange }.reversed()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        if (jRange.last > jRange.first)
            return cells.filter { it.i == i && it.j in jRange }
        else return cells.filter { it.i == i && it.j in jRange }.reversed()
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            LEFT -> cells.find { it == Cell(this.i, this.j - 1) }
            RIGHT -> cells.find { it == Cell(this.i, this.j + 1) }
            UP -> cells.find { it == Cell(this.i - 1, this.j) }
            DOWN -> cells.find { it == Cell(this.i + 1, this.j) }
            else -> null
        }
    }


}


class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {
    val cellValues = mutableMapOf<Cell, T?>()

    init {
        cells.forEach{cellValues.put(it, null)}
    }

    override fun get(cell: Cell): T? {
        return cellValues.get(cell)
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return filter(predicate).size == cellValues.size
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return filter(predicate).isNotEmpty()
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return filter(predicate).first()
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellValues.filterValues(predicate).keys
    }

    override fun set(cell: Cell, value: T?) {
        cellValues.put(cell, value)
    }

}


fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

