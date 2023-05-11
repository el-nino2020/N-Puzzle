# N-Puzzle

## [**What is N-Puzzle?**](https://algorithmsinsight.wordpress.com/graph-theory-2/a-star-in-general/implementing-a-star-to-solve-n-puzzle/)

N-Puzzle or sliding puzzle is a popular puzzle that consists of N tiles where $\forall k \in \Z, k \ge 2, N = k^2 -1$, e.g. $N = 8,15,24,\cdots$ . 

The puzzle is divided into $k$ rows and $k$ columns. e.g. 15-Puzzle will have 4 rows and 4 columns, an 8-Puzzle will have 3 rows and 3 columns and so on. 

The puzzle consists of **one empty space** where the tiles can be moved and thus the puzzle is solved when a particular goal pattern is formed like the following:
![](MarkdownImages/2f65df9e05519ae6c94bd7cac20b246b92299866.png)

本质上，我们需要不断地移动唯一的空格，直到其他有数字的拼图满足条件，因此是一个 Single Agent Search 问题

## 相关文献

[Single-Agent Search - Sliding Tile Puzzle Domain](https://www.movingai.com/SAS/STP/)


