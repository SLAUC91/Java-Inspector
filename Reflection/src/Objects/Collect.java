package Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Collect implements Serializable{
    
    public List<Integer> num1 = new ArrayList<Integer>();;
    public Collect(){ };
    public void setList(int A){
        
        num1.add(A);
    }
    
}
