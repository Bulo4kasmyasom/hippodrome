import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {
    public static final Class<Horse> HORSE_CLASS = Horse.class;
    public static final Class<IllegalArgumentException> EXPECTED_IA_EXCEPTION_CLASS = IllegalArgumentException.class;
    public static final String HORSE_NAME = "Bulo4ka";

    @Test
    public void whenNameIsNullThenThrowsException() {
        ThrowingRunnable actualThrowingRunnable = () -> new Horse(null, 123);
        assertThrows(EXPECTED_IA_EXCEPTION_CLASS, actualThrowingRunnable);
    }

    @Test
    public void whenNameIsNullThenThrowsExceptionGetMessage() {
        Class<IllegalArgumentException> expectedException = IllegalArgumentException.class;
        ThrowingRunnable actualThrowingRunnable = () -> new Horse(null, 123);

        IllegalArgumentException exception = assertThrows(expectedException, actualThrowingRunnable);
        String expected = "Name cannot be null.";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t\t", "\n", "\n\n\n\n"})
    public void nameInConstructorShouldBeEmpty(String input) {
        ThrowingRunnable actualThrowingRunnable = () -> new Horse(input, 123);

        assertThrows(EXPECTED_IA_EXCEPTION_CLASS, actualThrowingRunnable);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\r", "\n", "\r\n"})
    public void nameInConstructorShouldBeEmptyGetMessage(String valueSource) {
        ThrowingRunnable actualThrowingRunnable = () -> new Horse(valueSource, 123);

        IllegalArgumentException exception = assertThrows(EXPECTED_IA_EXCEPTION_CLASS, actualThrowingRunnable);
        String expected = "Name cannot be blank.";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void whenSpeedInConstructorIsNegativeThenTrowsException() {
        ThrowingRunnable actualThrowingRunnable = () -> new Horse(HORSE_NAME, -1);

        assertThrows(EXPECTED_IA_EXCEPTION_CLASS, actualThrowingRunnable);
    }

    @Test
    public void whenSpeedIsNegativeGetMessage() {
        ThrowingRunnable actualThrowingRunnable = () -> new Horse(HORSE_NAME, -1);
        IllegalArgumentException exception = assertThrows(EXPECTED_IA_EXCEPTION_CLASS, actualThrowingRunnable);

        String expected = "Speed cannot be negative.";
        String actual = exception.getMessage();
        
        assertEquals(expected, actual);
    }

    @Test
    public void whenDistanceInConstructorIsNegativeThenThrowsException() {
        ThrowingRunnable actualThrowingRunnable = () -> new Horse(HORSE_NAME, 10, -1);

        assertThrows(EXPECTED_IA_EXCEPTION_CLASS, actualThrowingRunnable);
    }

    @Test
    public void whenDistanceInConstructorIsNegativeThenThrowsExceptionGetMessage() {
        ThrowingRunnable actualThrowingRunnable = () -> new Horse(HORSE_NAME, 10, -1);

        IllegalArgumentException exception = assertThrows(EXPECTED_IA_EXCEPTION_CLASS, actualThrowingRunnable);
        String expected = "Distance cannot be negative.";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }


    @Test
    public void whenNameInConstructorEqualsFieldName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse(HORSE_NAME, 1, 2);
        Field nameField = HORSE_CLASS.getDeclaredField("name");

        nameField.setAccessible(true);
        String actual = (String) nameField.get(horse);
        nameField.setAccessible(false);

        assertEquals(HORSE_NAME, actual);
    }

    @Test
    public void whenSpeedInConstructorEqualsFieldName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse(HORSE_NAME, 1.0, 2.0);
        Field speedField = HORSE_CLASS.getDeclaredField("speed");

        speedField.setAccessible(true);
        double actual = (double) speedField.get(horse);
        speedField.setAccessible(false);
        int expected = 1;
        double delta = 0.1;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void whenDistanceInConstructorEqualsFieldName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse(HORSE_NAME, 1.0, 2.0);
        Field distanceField = HORSE_CLASS.getDeclaredField("distance");

        distanceField.setAccessible(true);
        double actual = (double) distanceField.get(horse);
        distanceField.setAccessible(false);
        int expected = 2;
        double delta = 0.1;

        assertEquals(expected, actual, delta);
    }

    @Test
    public void returnsZeroWhenHorseCreatesWithTwoParamsInConstructor() {
        Horse horse = new Horse(HORSE_NAME, 1);
        int expected = 0;
        int delta = 0;
        double actual = horse.getDistance();

        assertEquals(expected, actual, delta);
    }


    @Test
    public void whenHorseMovingThenVerifyMethodGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(HORSE_CLASS)) {
            Horse horse = new Horse(HORSE_NAME, 2);
            horse.move();

            mockedStatic.verify(() -> {
                double min = 0.2;
                double max = 0.9;
                Horse.getRandomDouble(min, max);
            });
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.9, 1.0})
    void whenHorseMovingThenGetRandomDoubleWithFormula(double valueSource) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(HORSE_CLASS)) {

            Horse horse = new Horse(HORSE_NAME, 2, 5);
            mockedStatic.when(() -> {
                double min = 0.2;
                double max = 0.9;
                Horse.getRandomDouble(min, max);
            }).thenReturn(valueSource);

            horse.move();
            double expected = 5 + 2 * valueSource;
            double actual = horse.getDistance();
            double delta = 0.1;

            assertEquals(expected, actual, delta);
        }
    }

}
