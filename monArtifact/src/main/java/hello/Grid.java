package hello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grid implements Cloneable{

    private Case[][] cellsGrid;
    private Case[][] antiGrid;
    private List<List<Integer>> possibleColumnValues = new ArrayList<List<Integer>>();
    private List<List<Integer>> possibleLineValues = new ArrayList<List<Integer>>();
    private List<List<Integer>> possibleSquareValues = new ArrayList<List<Integer>>();
    private int nbSeted;

    public Grid(){}
    public Grid(Case[][] cellsGrid){
        this.cellsGrid=cellsGrid;
        this.antiGrid = new Case[9][9];
        this.nbSeted=0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.antiGrid[j][i]=cellsGrid[i][j];
                if(cellsGrid[i][j].isValidated()){
                    this.nbSeted++;
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            possibleColumnValues.add(new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
            possibleLineValues.add(new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
            possibleSquareValues.add(new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
        }
    }

    @Override
    public Grid clone(){
        Case[][] cellsGrid = new Case[9][9];
        Case[][] antiGrid = new Case[9][9];
        List<List<Integer>> possibleColumnValues = new ArrayList<>();
        List<List<Integer>> possibleLineValues= new ArrayList<>();
        List<List<Integer>> possibleSquareValues= new ArrayList<>();
        int nbSeted = this.nbSeted;
        for(int i=0;i<9;i++){
            possibleColumnValues.add(new ArrayList<>());
            possibleLineValues.add(new ArrayList<>());
            possibleSquareValues.add(new ArrayList<>());
            for(int val : this.possibleColumnValues.get(i)){
                possibleColumnValues.get(i).add(val);
            }
            for(int val : this.possibleLineValues.get(i)){
                possibleLineValues.get(i).add(val);
            }
            for(int val : this.possibleSquareValues.get(i)){
                possibleSquareValues.get(i).add(val);
            }
            for(int j=0;j<9;j++){
                cellsGrid[i][j] = this.cellsGrid[i][j].clone();
                antiGrid[j][i]=cellsGrid[i][j];
            }
        }

        Grid grid = new Grid();
        grid.setAntiGrid(antiGrid);
        grid.setCellsGrid(cellsGrid);
        grid.setNbSeted(nbSeted);
        grid.setPossibleColumnValues(possibleColumnValues);
        grid.setPossibleLineValues(possibleLineValues);
        grid.setPossibleSquareValues(possibleSquareValues);

        return grid;
    }

    @Override
    public String toString(){
         String res = "****************************\n";
            for(int i=0;i<9*9;i++){
                if(i%9<8){
                    res+=cellsGrid[i/9][i%9].toString()+" ";
                }else{
                    res+=cellsGrid[i/9][i%9].toString()+"\n";
                }
            }
            if(isGridValid()) {
                res+="****************************\n";
            }else{
                res+="************ERROR****************\n";
            }
        return res;
    }

    private boolean isGridValid(){
        List<List<Integer>>line = new ArrayList<>();
        List<List<Integer>> column = new ArrayList<>();
        List<ArrayList<Integer>> square = new ArrayList<>();
        for(int i =0;i<9;i++){
            square.add(new ArrayList<>());
            line.add(new ArrayList<>());
            column.add(new ArrayList<>());
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int valueCell = cellsGrid[i][j].getValue();
                if(valueCell>0 && (line.get(i).contains(valueCell)||column.get(j).contains(valueCell)||square.get((i/3)*3+(j/3)).contains(valueCell))){
                    return false;
                }else{
                    line.get(i).add(valueCell);
                    column.get(j).add(valueCell);
                    square.get((i/3)*3+(j/3)).add(valueCell);
                }
            }
        }
        return true;
    }

    public Case[][] getCellsGrid() {
        return cellsGrid;
    }

    public void setCellsGrid(Case[][] cellsGrid) {
        this.cellsGrid = cellsGrid;
    }

    public Case[][] getAntiGrid() {
        return antiGrid;
    }

    public void setAntiGrid(Case[][] antiGrid) {
        this.antiGrid = antiGrid;
    }

    public List<List<Integer>> getPossibleColumnValues() {
        return possibleColumnValues;
    }

    public void setPossibleColumnValues(List<List<Integer>> possibleColumnValues) {
        this.possibleColumnValues = possibleColumnValues;
    }

    public List<List<Integer>> getPossibleLineValues() {
        return possibleLineValues;
    }

    public void setPossibleLineValues(List<List<Integer>> possibleLineValues) {
        this.possibleLineValues = possibleLineValues;
    }

    public List<List<Integer>> getPossibleSquareValues() {
        return possibleSquareValues;
    }

    public void setPossibleSquareValues(List<List<Integer>> possibleSquareValues) {
        this.possibleSquareValues = possibleSquareValues;
    }

    public int getNbSeted() {
        return nbSeted;
    }

    public void setNbSeted(int nbSeted) {
        this.nbSeted = nbSeted;
    }

}
