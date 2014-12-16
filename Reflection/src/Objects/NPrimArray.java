package Objects;

public class NPrimArray {
        Car []array = null;
        
	public NPrimArray()
	{
		array = new Car[3];
		array[0] = new Car("test", 2012);
		array[1] = new Car("honda", 2000);
                array[2] = new Car("a", 1900);
	}
}
