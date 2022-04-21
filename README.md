# SUSTech-CS102A-project-chess

# 1. Background
Chess is a board game played between two players.It is played on a square chessboard with 64 squares arranged in an eight-by-eight grid, as shown in Figure 1. At the start, each player (one controlling the white pieces, the other controlling the black pieces) controls sixteen pieces: one king, one queen, two rooks, two bishops, two knights, and eight pawns. The object of the game is to checkmate the opponent’s king, whereby the king is under immediate attack (in "check") and there is no way for it to escape. There are also several ways a game can end in a draw.
In this project, we need to write a simple chess game program with a visual graphical interface and basic game rules, such as movement, win/lose decisions, etc. It may sound interesting, so let’s get started.

# 2. Task Specification
The program can be roughly divided into the following tasks, and the score points for each task are given.

   ## 1. Initialization(10%)
   - Board initialization (5%). Initialize the game, form a board with pieces in correct positions and showing the status of the black and white turn.
   - Board reset (5%). Click the reset button, the pieces are restored to their initial positions and the previous game is cleaned up with no conflicts.

   ## 2. Load and store game(20%)
   - Load a game from existing archives and then form a board with correct player turn notification function (2%).
   - Can store a game with board information and the correct player turn (2%).
   - Can load and store game multiple times (2%).
   - Can save steps when storing a game(3%).
   - Have error check on archive when loading the game (5 * 2% = 10%):
     1. The board is not 8*8.
     1. The pieces are not one of six, or not black and white.
     1. Missing next move.
     1. Wrong file format (e.g. txt is specified but json is imported).
     1. Illegal move (the stored movement is illegal).
   - Save the game as a file (2%)

  ## 3. Game rules(40%)
  1. Movement (6 * 2% = 12%). White moves first, after which players alternate turns, moving one piece per turn, except for castling, when two pieces are moved. Each piece has its own way of moving. The rules are as follows:
     1. King: The king moves one square in any direction. There is also a special move called castling that involves moving the king and a rook.
     2. Queen: A queen can move any number of squares along a rank, file, or diagonal, but cannot leap over other pieces
     3. Rook: A rook can move any number of squares along a rank or file, but cannot leap over other pieces. Along with the king, a rook is involved during the king’s castling move.
     4. Bishop: A bishop can move any number of squares diagonally, but cannot leap over other pieces.
     5. Knight: A knight moves to any of the closest squares that are not on the same rank, file, or diag- onal. (Thus the move forms an "𝐿"-shape: two squares vertically and one square horizontally, or two squares horizontally and one square vertically.) The knight is the only piece that can leap over other pieces.Here, we stipulate that there is no restriction on "poor horse leg".
     6. Pawn: A pawn can move forward to the unoccupied square immediately in front of it on the same file, or on its first move it can advance two squares along the same file, provided both squares are unoccupied. A pawn can capture an opponent’s piece on a square diagonally in front of it by moving to that square (black crosses). It cannot capture a piece while advancing along the same file.

  2. Three special moves (3 * 4% = 12%).
     1. En passant. When a pawn makes a two-step advance from its starting position and there is an opponent’s pawn on a square next to the destination square on an adjacent file, then the opponent’s pawn can capture it en passant ("in passing"), moving to the square the pawn passed over. This can be done only on the turn immediately following the enemy pawn’s two-square advance; otherwise, the right to do so is forfeited.
     2. Castling. Once per game, each king can make a move known as castling. Castling consists of moving the king two squares toward a rook of the same color on the same rank, and then placing the rook on the square that the king crossed.Castling is permissible if the following conditions are met(Optional):
        1. There are no pieces between the king and the rook.
        2. The king is not in check.
        3. The king does not pass through or land on any square attacked by an enemy piece.
        4. Neither the king nor the rook has previously moved during the game.
     3. Promotion.When a pawn advances to its eighth rank, as part of the move, it is promoted and must be exchanged for the player’s choice of queen, rook, bishop, or knight of the same color.

  3. End of the game (4 * 4% =16%).
     1. Win. The king is in check and the player has no legal move.
     2. Draw. There are several ways a game can end in a draw:
        1. Perpetual check. If a player can check the other infinite times in a row, with no reasonable chance of escape, the game is drawn.
        2. Threefold repetition. Threefold repetition is when a game is drawn due to the position in a game being the same three times in the game.
        3. Stalemate. If the player to move has no legal move, but is not in check, the position is a stalemate, and the game is drawn.

  ## 4. Graphical interface(10%)
   Design user GUI using Swing or FX. 3 points will be deducted for each time the application is found to require interaction with the console, and 3 points will be deducted for each time the application is found to crash abnormally.
  ## 5. Bonus(30% in week15, 20% in week16)
   1. Platform and aesthetics (maximum 12%).
      1. Adding start menu for different functions.(1%)
      2. Use JFileChooser to load existing archives.(1%)
      3. Game user properties.(2%)
      4. Ranking list.(1%)
      5. Replace board image.(1%)
      6. Embed background image.(1%)
      7. Theme skin switching.(1%)
      8. Embed chess sound effects, background music.(2%, if only one is 1%)
      9. Load and store the game GUI module.(2%)
      10. Load or store game archives for different users.(2%, not add points with (2))
      11. Mouse over pieces or board squares with color change.(2%)
      12. The board size adapts to the window size change.(2%)
      13. All-roundrewriteJOptionPane.(3%)

   2. AI and algorithm(maximum 12%)
      1. Show the next legal move of a piece when it is selected.(1%)
      2. Show alarm when King is attacked.(1%)
      3. Use random moves in PvE mode.(2%)
      4. Use greedy strategy moves in PvE mode.(3%)
      5. Use pruning search algorithms in PvE mode and be able to reasonably describe the evaluation function.(6%)
