/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chess;

/**
 *
 * @author 
 */
public class Move {
    int row, col;

    public Move() {
    }

    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
    
}
