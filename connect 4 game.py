from itertools import groupby, chain

NONE = '.'
X = 'X'
O = 'O'
class Board:
    
        def diagonalsPos (matrix, cols, rows):
                """Get positive diagonals, going from bottom-left to top-right."""
                for diagonal in ([(j, i - j) for j in range(cols)] for i in range(cols + rows -1)):
                        yield [matrix[i][j] for i, j in diagonal if i >= 0 and j >= 0 and i < cols and j < rows]

        def diagonalsNeg (matrix, cols, rows):
                """Get negative diagonals, going from top-left to bottom-right."""
                for diagonal in ([(j, i - cols + j + 1) for j in range(cols)] for i in range(cols + rows - 1)):
                        yield [matrix[i][j] for i, j in diagonal if i >= 0 and j >= 0 and i < cols and j < rows]

class Game:
	def __init__ (self, cols = 6, rows = 7, requiredToWin = 4):
		"""Create a new game."""
		self.cols = cols
		self.rows = rows
		self.win = requiredToWin
		self.board = [[NONE] * rows for _ in range(cols)]

	def insert (self, column, figure):
		"""Insert the figure in the given column."""
		c = self.board[column]
		if c[0] != NONE:
			raise Exception('Column is full')

		i = -1
		while c[i] != NONE:
			i -= 1
		c[i] = figure

		self.checkForWin()

	def checkForWin (self):
		"""Check the current board for a winner."""
		w = self.getWinner()
		if w:
			self.printBoard()
			raise Exception(w + ' won!')

	def getWinner (self):
		"""Get the winner on the current board."""
		lines = (
			self.board, # columns
			zip(*self.board), # rows
			Board.diagonalsPos(self.board, self.cols, self.rows),
			Board.diagonalsNeg(self.board, self.cols, self.rows) 
		)

		for line in chain(*lines):
			for figure, group in groupby(line):
				if figure != NONE and len(list(group)) >= self.win:
					return figure

	def printBoard (self):
		"""Print the board."""
		print('  '.join(map(str, range(self.cols))))
		for y in range(self.rows):
			print('  '.join(str(self.board[x][y]) for x in range(self.cols)))
		print()


if __name__ == '__main__':
	g = Game()
	turn = X
	while True:
		g.printBoard()
		row = input('{}\'s turn: '.format('X' if turn == X else 'O'))
		g.insert(int(row), turn)
		turn = O if turn == X else X
