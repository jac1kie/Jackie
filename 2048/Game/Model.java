package game2048;

import java.util.Formatter;
import java.util.Observable;

import static com.google.common.truth.Truth.assertThat;


/** The state of a game of 2048.
 *  @author Jacqueline Dominguez Davalos
 *  @King Sung Li
 *
 */
public class Model extends Observable {
    /**
     * Current contents of the board.
     */
    private Board _board;
    /**
     * Current score.
     */
    private int _score;
    /**
     * Maximum score so far.  Updated when game ends.
     */
    private int _maxScore;
    /**
     * True iff game is ended.
     */
    private boolean _gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /**
     * Largest piece value.
     */
    public static final int MAX_PIECE = 2048;

    /**
     * A new 2048 game on a board of size SIZE with no pieces
     * and score 0.
     */
    public Model(int size) {
        this._board = new Board(size);
        // TODO: Fill in this constructor.
    }

    /**
     * A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes.
     */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {

      //  this._board = new Board(rawValues);

        // TODO: Fill in this constructor.
        _board = new Board(rawValues);
        _score = score;
        _maxScore = maxScore;
        _gameOver = gameOver;
    }

    /**
     * Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     * 0 <= COL < size(). Returns null if there is no tile there.
     * Used for testing. Should be deprecated and removed.
     */
    public Tile tile(int col, int row) {
        return _board.tile(col, row);
    }

    /**
     * Return the number of squares on one side of the board.
     * Used for testing. Should be deprecated and removed.
     */
    public int size() {
        return _board.size();
    }

    /**
     * Return true iff the game is over (there are no moves, or
     * there is a tile with value 2048 on the board).
     */
    public boolean gameOver() {
        checkGameOver();
        if (_gameOver) {
            _maxScore = Math.max(_score, _maxScore);
        }
        return _gameOver;
    }

    /**
     * Return the current score.
     */
    public int score() {
        return _score;
    }

    /**
     * Return the current maximum game score (updated at end of game).
     */
    public int maxScore() {
        return _maxScore;
    }

    /**
     * Clear the board to empty and reset the score.
     */
    public void clear() {
        _score = 0;
        _gameOver = false;
        _board.clear();
        setChanged();
    }

    /**
     * Add TILE to the board. There must be no Tile currently at the
     * same position.
     */
    public void addTile(Tile tile) {
        _board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /**
     * Tilt the board toward SIDE. Return true iff this changes the board.
     * <p>
     * 1. If two Tile objects are adjacent in the direction of motion and have
     * the same value, they are merged into one Tile of twice the original
     * value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     * tilt. So each move, every tile will only ever be part of at most one
     * merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     * value, then the leading two tiles in the direction of motion merge,
     * and the trailing tile does not.
     */


    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Fill in this function.
        for (int col = 0; col < _board.size(); col++) { // loop through columns
            _board.setViewingPerspective(side); // setting initial perspective
            for (int row = _board.size() - 1; row > -1; row -= 1) { // loop through the row
                Tile curTile = _board.tile(col, row); // storing the current tile
                if (curTile != null) { // if it has a value
                    for (int nextrow = row - 1; nextrow > -1; nextrow-=1) { // then we go to the next row
                        Tile DownTile = _board.tile(col, nextrow); // storing the value for this tile
                        if (DownTile != null) { // if it has value
                            if (curTile.value() == DownTile.value()) { // then we check if it has the same value
                                _board.move(col, row, DownTile); // we merge
                                _score = _score + 2 * DownTile.value(); // update the score
                                changed = true; // update changed status
                                row = nextrow; // update current tile in this current column
                                break;
                            }
                        }
                    }
                }
            }
        }
        // If the first tile we check is null
        for (int col = 0; col < _board.size(); col +=1){
            for (int row = _board.size() - 1; row > -1; row -=1){
                Tile curTile = _board.tile(col, row);
                if (curTile == null){
                    for (int nextrow = row - 1; nextrow > -1; nextrow -= 1){
                        Tile DownTile = _board.tile(col, nextrow);
                        if (DownTile != null){
                            _board.move(col, row, DownTile);
                            changed = true;
                            break;
                        }
                    }
                }
            }
        }
        _board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }



    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        _gameOver = checkGameOver(_board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {

        for (int row = 0; row < b.size(); row+=1) {
            for (int col = 0; col < b.size(); col+=1) {
                if (b.tile(col, row) == null) {
                    return true;
                }
            }
        }

            // TODO: Fill in this function.
            return false;
        }


    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
       // for(i=0; i<b.size();i++){
        for (int row = 0; row < b.size(); row+=1) {
            for (int col = 0; col < b.size(); col+=1) {
                if (b.tile(col, row) == null) continue;
                else if (b.tile(col, row).value() == MAX_PIECE) {
                    return true;
                }
            }
                }

        return false;
    }



    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */

    public static boolean CheckAdjacent(Board b){
        for(int col=0; col<b.size(); col++){
            for(int row=0; row< b.size(); row++){
                Tile temptile= b.tile(col, row);
                if(temptile != null){
                    if(row>0 && b.tile(col, row-1)!=null && b.tile(col, row-1).value()== temptile.value()){
                        return true;
                    }
                    if (col < b.size()-1 && b.tile(col+1, row) !=null && b.tile(col +1, row).value()== temptile.value()) {
                        return true;
                    }
                    if(row< b.size()-1 && b.tile(col, row+1)!=null && b.tile(col, row +1).value() == temptile.value()){
                        return true;

                    }
                    if (col>0 && b.tile(col-1, row) != null && b.tile(col-1, row).value()== temptile.value()){
                        return true;
                    }

                }
            }
        }
        return false;
    }

    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists(b)) {
            return true;
        }
        if(CheckAdjacent(b)) {

                    return true;
                }

            return false;
        }

        // TODO: Fill in this function.


    /** Returns the model as a string, used for debugging. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    /** Returns whether two models are equal. */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    /** Returns hash code of Model’s string. */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
