package hello;

import java.util.ArrayList;
import java.util.List;
public class Case implements Cloneable {
    private List<Integer> PossibleValue;
    private int value;
    private boolean validated;

    public Case(int value){
        this.value = value;
        validated = true;
    }
    public Case(){
        value=0;
        validated = false;
        this.PossibleValue = new ArrayList<>();
        this.PossibleValue.add(1);
        this.PossibleValue.add(2);
        this.PossibleValue.add(3);
        this.PossibleValue.add(4);
        this.PossibleValue.add(5);
        this.PossibleValue.add(6);
        this.PossibleValue.add(7);
        this.PossibleValue.add(8);
        this.PossibleValue.add(9);
    }

    @Override
    public Case clone(){
        Case ret = new Case();
        ret.setValue(this.value);
        ret.setValidated(this.validated);
        if(!this.validated) {
            List<Integer> PossibleValue = new ArrayList<>();
            for (Integer i : this.PossibleValue) {
                PossibleValue.add(i);
            }
            ret.setPossibleValue(PossibleValue);
        }
        return ret;
    }

    public String toString(){
        return value>0?Integer.toString(value):"_";
    }

    public List<Integer> getPossibleValue() {
        return PossibleValue;
    }

    public void setPossibleValue(List<Integer> possibleValue) {
        PossibleValue = possibleValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}