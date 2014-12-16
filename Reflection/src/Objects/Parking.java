package Objects;

public class Parking {
    public Car A;
    public int lot_number = -1;
    public String city = "-1";
    
    public void setCar(Car A) {  
        this.A = A;
    }
        
    public Parking (Car A, int LN, String C){
        this.A = A;
        lot_number = LN; 
        city = C;
    }
    
    public Parking(){}; 
}
