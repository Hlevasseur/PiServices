package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SudokuController {

    @RequestMapping("/sudoku")
    public String ResolveSudoku(){


        Case[][] cellsgrid = new Case[9][9];
        String gridString;
        try{
            gridString = FileUtils.readFIle("C:\\git\\gs-rest-service\\monArtifact\\src\\main\\resources\\sudoku\\grid.properties");
        }catch (IOException e){
            return null;
        }
        int position = 0;
        for(char c : gridString.toCharArray()){
            if(Character.isDigit(c)){
                cellsgrid[position/9][position%9] = new Case(((int) c)-48);
                position++;
            }else if(c == '_'){
                cellsgrid[position/9][position%9] = new Case();
                position++;
            }
            if((position)/9==9){
               break;
            }
        }
        Grid grid = new Grid(cellsgrid);
        System.out.println(grid.toString());
        Resolver resolver = new Resolver(grid);
        grid = resolver.ResolveSudoku();
        System.out.println(grid.toString());
        return grid.toString();
    }
}
