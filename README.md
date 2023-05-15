# 环境

- JDK 8
- Maven 3.8.x

# 使用

- 程序入口：NPuzzleApplication
- 目前是随机生成一个长度为`GlobalConstants#PUZZLE_ROW_COUNT`、宽度为`GlobalConstants#PUZZLE_COLUMN_COUNT` （仅有的2个可变的配置参数）的N-puzzle实例，然后让每个算法执行这个实例，看看各个算法的效果
- 之后考虑实现自定义 N-puzzle 实例 

# N-Puzzle

## [**What is N-Puzzle?**](https://algorithmsinsight.wordpress.com/graph-theory-2/a-star-in-general/implementing-a-star-to-solve-n-puzzle/)

N-Puzzle or sliding puzzle is a popular puzzle that consists of N tiles where $\forall k \in \Z, k \ge 2, N = k^2 -1$, e.g. $N = 8,15,24,\cdots$ . 

The puzzle is divided into $k$ rows and $k$ columns. e.g. 15-Puzzle will have 4 rows and 4 columns, an 8-Puzzle will have 3 rows and 3 columns and so on. 

The puzzle consists of **one empty space** where the tiles can be moved and thus the puzzle is solved when a particular goal pattern is formed like the following:
<img title="" src="MarkdownImages/2f65df9e05519ae6c94bd7cac20b246b92299866.png" alt="" data-align="center">

本质上，我们需要不断地移动唯一的空格，直到其他有数字的拼图满足条件，因此是一个 Single Agent Search 问题

## 相关文献

[Single-Agent Search - Sliding Tile Puzzle Domain](https://www.movingai.com/SAS/STP/)

## 算法

- 这些算法的实现都在`org.example.npuzzle.strategy.impl`下

### 1. Depth First Search

### 2. Breath First Search

- 对于这个问题，BFS等价于 UCS, Uniform-cost search，因此不再重复实现了

### 3. A-star

参考：https://algorithmsinsight.wordpress.com/graph-theory-2/a-star-in-general/implementing-a-star-to-solve-n-puzzle/

下面罗列的是使用的一些 heuristic 函数

#### 3.1. Manhattan Distance

#### 3.2. Hamming Distance

#### 3.3. Linear Conflict + Manhattan Distance

#### 3.4. Euclidean distance

### 4.  Bidirectional BFS

参考：https://www.geeksforgeeks.org/bidirectional-search/

> **Why bidirectional approach?**
> 
> Because in many cases it is faster, it dramatically reduce the amount of required exploration.
> Suppose if branching factor of tree is **b** and distance of goal vertex from source is **d**, then the normal BFS/DFS searching complexity would be $O(b^d).$ On the other hand, if we execute two search operation then the complexity would be $O(b^{d/2})$ for each search and total complexity would be $O(b^{d/2} +b^{d/2})$ which is far less than $O(b^d)$.
> 
> **When to use bidirectional approach?**
> 
> We can consider bidirectional approach when- 
> 
> 1. Both initial and goal states are unique and completely defined.
> 2. The branching factor is exactly the same in both directions.
> 
> **Performance measures**
> 
> - Completeness : Bidirectional search is complete if BFS is used in both searches.
> - Optimality : It is optimal if BFS is used for search and paths have uniform cost.
> - Time and Space Complexity : Time and space complexity is $O(b^{d/2})$. 
