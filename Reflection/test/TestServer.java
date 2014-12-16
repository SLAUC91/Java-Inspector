import Objects.*;
import static org.junit.Assert.assertTrue;
import org.junit.*;

public class TestServer {
    
    public TestServer() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
     @Test
     public void TestSimpleObj() {
         Car A = new Car("abc", 123);
         Servermain.Serial(A);
         Object curObj = Clientmain.DeSerial("Output.xml");
         Car newA = (Car) curObj;
         assertTrue((A.Name).equals(newA.Name));
         assertTrue(A.Year == newA.Year);
     }
     
     @Test
     public void TestRefeObj() {
         Car A = new Car("abc", 123);
         Parking B = new Parking(A, 1, "abc");
         Servermain.Serial(A);
         Object curObj = Clientmain.DeSerial("Output.xml");
     }
     
     @Test
     public void TestParray() {
         int[] a = {1,2,3};
         String[] b = {"a","B","c"};
         PrimArray A = new PrimArray(a,b);
         Servermain.Serial(A);
         Object curObj = Clientmain.DeSerial("Output.xml");
         PrimArray B = (PrimArray) curObj;
         for (int i = 0; i < A.aint.length; i++){
            assertTrue(A.aint[i] == B.aint[i]);
         }
         for (int j = 0; j < A.aint.length; j++){
            assertTrue((A.astring[j]).equals(B.astring[j]));
         }
     }
     
     @Test
     public void TestNParray() {
         NPrimArray A = new NPrimArray();
         Servermain.Serial(A);
         Object curObj = Clientmain.DeSerial("Output.xml");
     }
     
     @Test
     public void Testcol() {
        Collect A = new Collect();
        Servermain.Serial(A);
        Object curObj = Clientmain.DeSerial("Output.xml");
     }
}
