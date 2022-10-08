import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    public void nameShouldBeNullAndThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 123));
    }

    @Test
    public void nameShouldBeNullAndThrowsExceptionGetMessage() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 123)
        );
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t\t", "\n", "\n\n\n\n"})
    public void nameShouldBeEmpty(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(input, 123));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void nameShouldBeEmptyGetMessage(String input) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(input, 123)
        );
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void speedShouldBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Name horse", -1));
    }

    @Test
    public void speedShouldBeNegativeGetMessage() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Name horse", -1)
        );
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void distanceShouldBeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Name horse", 10, -1));
    }

    @Test
    public void distanceShouldBeNegativeGetMessage() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Name horse", 10, -1)
        );
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }


    @Test
    public void getNameIsFirstParam() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Baby", 1, 2);

        Field nameField = Horse.class.getDeclaredField("name");
        nameField.setAccessible(true);
        String nameValue = (String) nameField.get(horse);
        nameField.setAccessible(false);
        assertEquals("Baby", nameValue);
    }

    @Test
    public void getSpeedIsFirstParam() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Baby", 1.0, 2.0);

        Field nameField = Horse.class.getDeclaredField("speed");
        nameField.setAccessible(true);
        double nameValue = (double) nameField.get(horse);
        nameField.setAccessible(false);
        assertEquals(1, nameValue, 0.1);
    }

    @Test
    public void getDistanceIsFirstParam() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Baby", 1.0, 2.0);

        Field nameField = Horse.class.getDeclaredField("distance");
        nameField.setAccessible(true);
        double nameValue = (double) nameField.get(horse);
        nameField.setAccessible(false);
        assertEquals(2, nameValue, 0.1);
    }

    @Test
    public void returnsNullWhenHorseCreatesWithTwoParams() { // такое себе решение
        Horse horse = new Horse("Лошадка", 1);
        assertEquals(0, horse.getDistance(), 0);
    }


    @Test
    public void moveInvokeGetRandomDoubleMethod() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Baby", 2);
            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
//            mockedStatic.verify(() -> Horse.getRandomDouble(anyDouble(), anyDouble()));
//            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, anyDouble())); // так нельзя
//            mockedStatic.verify(() -> Horse.getRandomDouble(eq(0.2), anyDouble()));
        }
    }

    @ParameterizedTest
    @ValueSource (doubles = {0.2, 0.9, 1.0})
    void moveInvokeGetRandomDoubleMethodFormula (double rand){
        try (MockedStatic<Horse> mockedStatic =  Mockito.mockStatic( Horse.class)) {
            Horse horse = new Horse("Baby", 2, 5);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(rand);
            horse.move();
            assertEquals(5 + 2*rand, horse.getDistance(), 0.1);
        }
    }

}
