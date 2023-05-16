:: 参数设置 ::
:: 是否随机初始化N-puzzle实例
set RANDOM_GAME=true
:: 程序一次运行多少次N-puzzle实例，该值的有效性取决于 RANDOM_GAME 设置为true
set GAME_ROUND=2
:: 如果 RANDOM_GAME 设置为 false, 则必须提供自定义的 N-puzzle 实例文件的路径（相对或绝对）
:: N-puzzle 实例文件 的规则如下：
:: 1. 每个实例可视作一个矩阵，矩阵的每一行占据文本的一行，元素与元素之间使用空格隔开，每个元素都必须是自然数
:: 2. 实例与实例之间使用 --- 分隔
:: 3. 因此，合法的字符为：数字、空格、-和换行符，即^[\\d\\s\\-]+$
:: 4. 一旦检测到不正确的语法，程序暂停，不会继续运行之后的实例
set PROBLEM_FILE_PATH=./problems.txt
:: PUZZLE 有几行，该值的有效性取决于 RANDOM_GAME 设置为true
set ROW_COUNT=3
:: PUZZLE 有几列，该值的有效性取决于 RANDOM_GAME 设置为true
set COLUMN_COUNT=3
:: 是否连续运行所有 N-puzzle 实例，实例与实例之间不暂停
set AUTO_RUN_ALL_INSTANCES=true

java -jar ./target/npuzzle-0.0.1-SNAPSHOT.jar --random%RANDOM_GAME% --round%GAME_ROUND% --problems%PROBLEM_FILE_PATH% --row%ROW_COUNT% --column%COLUMN_COUNT% --auto-run%AUTO_RUN_ALL_INSTANCES%