package zad1;

import static org.junit.Assert.*;

import org.junit.Test;


public class CalculatorTest {
	
	Calculator calc = new Calculator();
	
	@Test
	public void checkAdding(){
		assertEquals(5, calc.add(2, 3));
		assertEquals(1, calc.add(-2, 3));
		assertEquals(-1, calc.add(2, -3));
		assertEquals(-5, calc.add(-2, -3));
		assertEquals(3, calc.add(0, 3));
		assertEquals(3, calc.add(3, 0));
	}
	
	@Test
	public void checkSubtracting(){
		assertEquals(-1, calc.sub(2, 3));
		assertEquals(-5, calc.sub(-2, 3));
		assertEquals(5, calc.sub(2, -3));
		assertEquals(1, calc.sub(-2, -3));
		assertEquals(-3, calc.sub(0, 3));
		assertEquals(3, calc.sub(3, 0));
	}
	
	@Test
	public void checkMultiplying(){
		assertEquals(6, calc.multi(2, 3));
		assertEquals(-6, calc.multi(-2, 3));
		assertEquals(-6, calc.multi(2, -3));
		assertEquals(6, calc.multi(-2, -3));
		assertEquals(0, calc.multi(0, 3));
		assertEquals(0, calc.multi(3, 0));
	}
	
	@Test
	public void checkDividing(){
		assertEquals(1, calc.div(3, 2));
		assertEquals(-3, calc.div(-6, 2));
		assertEquals(-2, calc.div(6, -3));
		assertEquals(2, calc.div(-6, -3));
		assertEquals(0, calc.div(0, 3));
	}
	
	@Test(expected = ArithmeticException.class)
	public void checkDividingException(){
		calc.div(2,0);
	}
	
	@Test
	public void checkGreater(){
		assertTrue("failure - should be true", calc.greater(3, 2));
		assertFalse("failure - should be false", calc.greater(-6, 2));
		assertEquals(true, calc.greater(6, -3));
		assertEquals(false, calc.greater(-6, -3));
		assertSame("failure - should be same", false, calc.greater(0, 3));
	}
	
	
}
