package pl.lodz.p.zzpj.testing.junit.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.junit.jupiter.api.condition.OS.MAC;

@DisplayName("Super Tester")
public class FizzBuzzTest
{
    private FizzBuzz fizzBuzz;
    private static ArrayList<Integer> tableOfNumbers;

    @BeforeAll
    private static void initazileTestArray()
    {
        tableOfNumbers = new ArrayList<>();
        for(int i=1; i<=100; i++)
        {
            tableOfNumbers.add(i);
        }
    }

    @BeforeEach
    private void initializeFizzBuzz()
    {
        fizzBuzz = new FizzBuzz();
    }

    @AfterEach
    private void displayAfterTest()
    {
        System.out.println("Test was lunched");
    }

    @AfterAll
    private static void displayAfterAllTests()
    {
        System.out.println("All tests were lunched");
    }

    @Test
    public void shouldNotBeNegative()
    {
        assertThrows(FizzBuzzException.class, () -> fizzBuzz.getFizzBuzzNumber(-1));
    }

    @Test
	public void shouldMultipleBeDone()
	{
		assertAll("FizzBuzz",
				() -> fizzBuzz.getFizzBuzzNumber(15),
				() -> fizzBuzz.getFizzBuzzNumber(30));
	}

	@Test
	public void shouldBeDoneInLessThan2Sec()
	{
		assertTimeout(Duration.ofSeconds(2), () -> fizzBuzz.getFizzBuzzNumber(0));
	}

	@Test
	public void shouldBeFuzzInIt() throws FizzBuzzException
	{
		assumeTrue(fizzBuzz.getFizzBuzzNumber(30).contains("Fizz"));
	}

	@Test void shouldNotBeBuzzInIt() throws FizzBuzzException
	{
		assumeFalse(fizzBuzz.getFizzBuzzNumber(6).contains("Buzz"));
	}

	@Test
	public void shouldReturnFizzBuzzIfMultipleOf15() throws FizzBuzzException
	{
		for (Integer i : tableOfNumbers)
		{
			assumingThat((i%15 == 0), () -> assertEquals("FizzBuzz", fizzBuzz.getFizzBuzzNumber(i)));
		}
	}

	@Nested
	public class ParametrizedTestClass
	{
		@Tag("WithParameter")
		@ParameterizedTest
		@ValueSource(ints = {3, 9})
		public void shouldContainFizz(int number) throws FizzBuzzException
		{
			assumeTrue(fizzBuzz.getFizzBuzzNumber(number).contains("Fizz"));
		}

		@Tag("WithParameter")
		@ParameterizedTest
		@CsvSource({"5, 10", "125, 300"})
		public void shouldBothContainBuzz(int firstNumber, int secondNumber) throws FizzBuzzException
		{
			assumeTrue(fizzBuzz.getFizzBuzzNumber(firstNumber).contains("Buzz"));
			assumeTrue(fizzBuzz.getFizzBuzzNumber(secondNumber).contains("Buzz"));
		}

		@Tag("WithParameter")
		@ParameterizedTest
		@CsvFileSource(resources = "/data.csv")
		public void shouldBothContainFizzBuzz(int firstNumber, int secondNumber) throws FizzBuzzException
		{
			assumeTrue(fizzBuzz.getFizzBuzzNumber(firstNumber).contains("FizzBuzz"));
			assumeTrue(fizzBuzz.getFizzBuzzNumber(secondNumber).contains("FizzBuzz"));
		}
	}

	@Tag("WithParameter")
	@ParameterizedTest
	@MethodSource("numberProvider")
	public void shouldNotBeANumber(int argument) throws FizzBuzzException
	{
		assertNotEquals("5", fizzBuzz.getFizzBuzzNumber(argument));
		assertNotEquals("10", fizzBuzz.getFizzBuzzNumber(argument));
		assertNotEquals("15", fizzBuzz.getFizzBuzzNumber(argument));
	}

	private static Stream<Integer> numberProvider()
	{
		return Stream.of(5, 10, 15);
	}

	@Disabled
	@Test
	public void shouldBeWrongTest() throws FizzBuzzException
	{
		assertEquals(2, fizzBuzz.getFizzBuzzNumber(2));
	}

	@Tag("Empty")
	@EnabledOnJre({ JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11 })
	@Test
	public void shouldBeMoreModernThanJava8()
	{

	}

	@Tag("Empty")
	@EnabledOnOs(OS.MAC)
	public void shouldRunOnlyOnMacOs()
	{

	}
}