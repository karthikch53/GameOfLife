GameOfLife
==========

Java Implementation of Conway's Game of Life.
When you try to run the program, the system prompts you to enter the input file path which has the input patterns.
Once you provide the path, the system prints the next generation of cells by applying these rules..

1.     Any live cell with fewer than two live neighbours dies, as if by loneliness.
2.     Any live cell with more than three live neighbours dies, as if by overcrowding.
3.     Any live cell with two or three live neighbours lives, unchanged, to the next generation.
4.     Any dead cell with exactly three live neighbours comes to life.

These rules are grouped into two logical rule handlers. One rule handler acts on live cells and the other one acts on dead cells.
If you wish to add a rule, do so by providing the implementation for the doAction method of the GolRuleChain
interface. Add the newly added rule in the property file.

Please feel free to fork the code.

~ cheers.!
