/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hamp_it.tictactoe_server;

/**
 *
 * @author Tim
 */
public final class Field {
    private final FieldStatusEnum[][] field;
    
    public Field() {
        field = new FieldStatusEnum[3][3];
        resetField();
    }
    
    public void resetField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = FieldStatusEnum.NONE;
            }
        }
    }
    
    public void setFieldPos(int x, int y, FieldStatusEnum status) {
        field[x][y] = status;
    }
    public FieldStatusEnum getFieldPos(int x, int y) {
        return field[x][y];
    }
    public boolean isDraw() {
        /*if (isWon() != FieldStatusEnum.NONE) {
            return true;
        }*/
        int x = 0;
        int o = 0;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (field[i][j] == FieldStatusEnum.O) {
                    o++;
                } else if (field[i][j] == FieldStatusEnum.X) {
                    x++;
                }
            }
        }
        return x+o >= 9;
    }
    
    public FieldStatusEnum isWon() {
        int x = 0;
        int o = 0;
        
        //Zeilen -
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (field[i][j] == FieldStatusEnum.O) {
                    o++;
                } else if (field[i][j] == FieldStatusEnum.X) {
                    x++;
                }
            }
            if (o == 3) {
                return FieldStatusEnum.O;
            } else if (x == 3) {
                return FieldStatusEnum.X;
            } else {
                x = 0;
                o = 0;
            }
        }
        
        //Spalten |
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (field[j][i] == FieldStatusEnum.O) {
                    o++;
                } else if (field[j][i] == FieldStatusEnum.X) {
                    x++;
                }
            }
            if (o == 3) {
                return FieldStatusEnum.O;
            } else if (x == 3) {
                return FieldStatusEnum.X;
            } else {
                x = 0;
                o = 0;
            }
        }
        
        //Diagonal \
        for(int i = 0; i < 3; ++i) {
            if (field[i][i] == FieldStatusEnum.O) {
                o++;
            } else if (field[i][i] == FieldStatusEnum.X) {
                x++;
            }
        }
        if (o == 3) {
                return FieldStatusEnum.O;
        } else if (x == 3) {
                return FieldStatusEnum.X;
        } else {
                x = 0;
                o = 0;
        }
        
        //Diagonal /
        for(int i = 0; i < 3; ++i) {
            if (field[2-i][i] == FieldStatusEnum.O) {
                o++;
            } else if (field[2-i][i] == FieldStatusEnum.X) {
                x++;
            }
        }
        if (o == 3) {
                return FieldStatusEnum.O;
        } else if (x == 3) {
                return FieldStatusEnum.X;
        } else {
                return FieldStatusEnum.NONE;
        }
    }
}
