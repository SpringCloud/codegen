package cn.springcloud.codegen.common;

import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        LinkedList<BigDecimal> values = new LinkedList<>();
        values.add(BigDecimal.valueOf(.1));
        values.add(BigDecimal.valueOf(1.1));
        values.add(BigDecimal.valueOf(2.1));
        values.add(BigDecimal.valueOf(.1));

        // Classical Java approach
        BigDecimal sum = BigDecimal.ZERO;
        for(BigDecimal value : values) {
            System.out.println(value);
            sum = sum.add(value);
        }
        System.out.println("Sum = " + sum);

        // Java 8 approach
        values.forEach((value) -> System.out.println(value));
        System.out.println("Sum = " + values.stream().mapToDouble(BigDecimal::doubleValue).sum());
        System.out.println(values.stream().mapToDouble(BigDecimal::doubleValue).summaryStatistics().toString());
    }
}
