import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.Format;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CalculadoraTest {

	private static Calculadora calc;

	@BeforeAll
	public static void setUp() {
		calc = new Calculadora();
	}

	@Test
	public void testeSomaBasica() {
		int result = calc.soma(1, 1);
		Assertions.assertEquals(2, result, "Soma não é 3");
	}

	@Test
	@DisplayName("Retorna valor decimal na divisão")
	public void testeRetornoDecimalDivisao() {
		float result = (float) calc.dividir(10, 3);
		Assertions.assertEquals(3.33, result, 0.01);
	}

	@Test
	public void divisaoPorZeroError() {
		float result = 10 / 0;
		Assertions.assertEquals(0, result);
		// Error
	}

	@Test
	public void falha() {
		throw new AssertionError();
		// Falha
	}

	@Test
	public void divisaoPorZeroExcecao_Junit4() {
		try {
			float result = 10 / 0;
			fail("Exception should have been triggered");
		} catch (ArithmeticException e) {
			assertTrue(("/ by zero").equalsIgnoreCase(e.getMessage()));
		}
	}

	@Test
	public void divisaoPorZeroExcecao_Junit5() {
		Assertions.assertThrows(ArithmeticException.class, () -> {
			float result = 10 / 0;
		});
	}

	@Test
	public void divisaoPorZeroExcecao_Junit5_2() {
		ArithmeticException assertThrows = Assertions.assertThrows(ArithmeticException.class, () -> {
			float result = 10 / 0;
		});
		assertEquals("/ by zero", assertThrows.getMessage());
	}

	@ParameterizedTest
	@CsvSource(value = { "2, 2, 1", "8, 2, 4", "10, 3, 3.33" })
	public void divisaoComSucessoNumerosInteiros(int a, int b, float res) {
		float result = calc.dividir(a, b);
		assertEquals(res, result, 0.01);
	}
}
