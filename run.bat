:: 参数设置
set RANDOM_GAME=true
set GAME_ROUND=1
set PROBLEM_FILE_PATH=./problems.txt
set ROW_COUNT=3
set COLUMN_COUNT=3

java -jar ./target/npuzzle-0.0.1-SNAPSHOT.jar --random%RANDOM_GAME% --round%GAME_ROUND% --problems%PROBLEM_FILE_PATH% --row%ROW_COUNT% --column%COLUMN_COUNT%