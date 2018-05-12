package hello;

public class Resolver {

    private boolean updated;
    private Grid grid;


    public Resolver(Case[][] grid) {
        this.grid = new Grid(grid);
    }
    public Resolver(Grid grid){
        this.grid = grid;
    }

    //private void checkCellPossibilities(Grid grid) {
    private void checkCellPossibilities() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //If i don't know the value
                if (grid.getCellsGrid()[i][j].getValue() == 0) {
                    for (int k = 0; k < 9; k++) {
                        //check the line
                        if (grid.getCellsGrid()[i][k].getValue() != 0) {
                            grid.getCellsGrid()[i][j].getPossibleValue().remove((Integer)grid.getCellsGrid()[i][k].getValue());
                        }
                        //check the column
                        if (grid.getCellsGrid()[k][j].getValue() != 0) {
                            grid.getCellsGrid()[i][j].getPossibleValue().remove((Integer)grid.getCellsGrid()[k][j].getValue());
                        }
                       //check the square
                        if (grid.getCellsGrid()[k % 3 + (i / 3) * 3][k / 3 + (j / 3) * 3].getValue() != 0) {
                            grid.getCellsGrid()[i][j].getPossibleValue().remove((Integer)grid.getCellsGrid()[k % 3 + (i / 3) * 3][k / 3 + (j / 3) * 3].getValue());
                        }
                    }
                }
            }
        }
    }

    private void setCellValue(Case c, int value){
        c.setValue(value);
        c.setValidated(true);
        c.getPossibleValue().clear();
        updated = true;
        grid.setNbSeted(grid.getNbSeted()+1);
        checkCellPossibilities();
        System.out.println(grid.toString());
    }

    private boolean CheckLineorSquarePossibilities(){
        for (int i = 0; i < 9; i++) { // for each line
            for (int j = 0; j < 9; j++) {//checking each cell to remove value to find
                grid.getPossibleLineValues().get(i).remove((Integer)grid.getCellsGrid()[i][j].getValue());
                grid.getPossibleColumnValues().get(j).remove((Integer)grid.getCellsGrid()[i][j].getValue());
                grid.getPossibleSquareValues().get((i/3)*3+(j/3)).remove((Integer)grid.getCellsGrid()[i][j].getValue());
            }
            for (int value:grid.getPossibleLineValues().get(i)) {
                if(searchOnlyPossibleCellForValue(value,grid.getCellsGrid()[i])){
                    System.out.println("onlyPossVal line "+value);
                    grid.getPossibleLineValues().get(i).remove((Integer)value);
                    return true;
                }
            }
            for(int value:grid.getPossibleColumnValues().get(i)){
                if(searchOnlyPossibleCellForValue(value,grid.getAntiGrid()[i])){
                    System.out.println("onlyPossVal Col "+value);
                    grid.getPossibleColumnValues().get(i).remove((Integer)value);
                    return true;
                }
            }
            for(int value:grid.getPossibleSquareValues().get(i)){
                if(searchOnlyPossibleCellForValueinSquare(value,i)){
                    System.out.println("onlyPossVal Square "+value);
                    grid.getPossibleSquareValues().get(i).remove((Integer)value);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean searchOnlyPossibleCellForValueinSquare(int value,int squareNumber){
        Case[] cases = new Case[9];
        for(int i=0;i<9;i++){
            cases[i] =grid.getCellsGrid()[i/3+3*(squareNumber/3)][(i%3)+3*(squareNumber%3)];
        }
        return searchOnlyPossibleCellForValue(value,cases);
    }
    private boolean searchOnlyPossibleCellForValue(int value,Case[] cases){
        Case candidat = null;
        for(Case c : cases){
            if(c.getPossibleValue()!=null &&!c.isValidated()&& c.getPossibleValue().contains(value)){
                if(candidat==null){
                    candidat=c;
                }else{
                    return false;
                }
            }
        }
        if(candidat!=null){
            setCellValue(candidat,value);
            return true;
        }
        return false;
    }

    public Grid ResolveSudoku() {

        checkCellPossibilities();
        while (grid.getNbSeted() < 81) {
            System.out.println(grid.toString());
            updated = false;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (grid.getCellsGrid()[i][j].getValue()==0 && grid.getCellsGrid()[i][j].getPossibleValue().size() == 1) {
                        System.out.println("only possible value for cell "+i+" "+j+" : "+grid.getCellsGrid()[i][j].getPossibleValue().get(0));
                        setCellValue(grid.getCellsGrid()[i][j],grid.getCellsGrid()[i][j].getPossibleValue().get(0));
                    }
                }
            }
            while(CheckLineorSquarePossibilities()){}
            if (!updated) {
                // faire un pari

                for(int seuilPossibleValue = 2;seuilPossibleValue<9;seuilPossibleValue++) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if(!grid.getCellsGrid()[i][j].isValidated() && grid.getCellsGrid()[i][j].getPossibleValue().size()==seuilPossibleValue){
                                for(int possibleValue : grid.getCellsGrid()[i][j].getPossibleValue()){
                                    Grid gridcopy = grid.clone();
                                    gridcopy.getCellsGrid()[i][j].setValue(possibleValue);
                                    gridcopy.getCellsGrid()[i][j].setValidated(true);
                                    gridcopy.getCellsGrid()[i][j].getPossibleValue().clear();
                                    gridcopy.setNbSeted(gridcopy.getNbSeted()+1);
                                    Resolver r = new Resolver(gridcopy);
                                    Grid g = r.ResolveSudoku();
                                    if(g.getNbSeted()>=81){
                                        return g;
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }

        }
        return grid;
    }
}

