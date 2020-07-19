package guava;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        int i = foo(4, 2018);
        System.out.println(i);
    }
    public static int foo(int a,int b){
        if (a>=b){
            if (a == b)
                return a;
                else
                    return 0;
        }else {
            return foo(a+1,b-1)+a+b;
        }
    }
}
