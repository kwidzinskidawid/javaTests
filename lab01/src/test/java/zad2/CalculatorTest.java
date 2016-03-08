package zad2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import zad2.Calculator;

public class CalculatorTest {
	Calculator calc = new Calculator();
	
	@Test
	public void checkAdding(){
		assertEquals(5.624563, calc.add(2.31221, 3.312353), 0.000001);

	}
	
	@Test
	public void checkSubtracting(){
		assertEquals(-1.3309, calc.sub(2.3121, 3.643), 0.0001);

	}
	
	@Test
	public void checkMultiplying(){
		assertEquals(7.210701986, calc.multi(2.113212, 3.4122), 0.000000001);

	}
	
	@Test
	public void checkDividing(){
		assertEquals(1.660615, calc.div(3.32123, 2), 0.000001);

	}
	
	//Todo
	@Ignore
	@Test(expected = ArithmeticException.class)
	public void checkDividingException(){
		calc.div(2.41231,0);
	}
	
	@Test
	public void checkGreater(){
		assertTrue("failure - should be true", calc.greater(3.312, 2.12));
		assertFalse("failure - should be false", calc.greater(-6.412, 2.41112));
		assertEquals(true, calc.greater(6.52, -3.2123114));
		assertEquals(false, calc.greater(-6.54321, -3.123));
		assertSame("failure - should be same", false, calc.greater(0.1231, 3.543));
	}
	
}
