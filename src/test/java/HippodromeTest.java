import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {

    public static final Class<IllegalArgumentException> EXPECTED_IA_EXCEPTION_CLASS = IllegalArgumentException.class;

    @Test
    public void whenConstructorIsNullTrowsException() {
        ThrowingRunnable throwingRunnable = () -> new Hippodrome(null);
        Assert.assertThrows(EXPECTED_IA_EXCEPTION_CLASS, throwingRunnable);
    }

    @Test
    public void whenConstructorIsNullTrowsExceptionGetMessage() {
        ThrowingRunnable throwingRunnable = () -> new Hippodrome(null);

        IllegalArgumentException exception = Assert.assertThrows(EXPECTED_IA_EXCEPTION_CLASS, throwingRunnable);
        String expected = "Horses cannot be null.";
        String actual = exception.getMessage();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldBeExceptionWhenHorsesListSizeInConstructorIsEmpty() {
        List<Horse> horseList = new ArrayList<>();
        ThrowingRunnable throwingRunnable = () -> new Hippodrome(horseList);

        Assert.assertThrows(EXPECTED_IA_EXCEPTION_CLASS, throwingRunnable);
    }

    @Test
    public void shouldBeExceptionWhenHorsesListSizeInConstructorIsEmptyGetMessage() {
        List<Horse> horseList = new ArrayList<>();
        ThrowingRunnable throwingRunnable = () -> new Hippodrome(horseList);

        IllegalArgumentException exception = Assert.assertThrows(EXPECTED_IA_EXCEPTION_CLASS, throwingRunnable);
        String expected = "Horses cannot be empty.";
        String actual = exception.getMessage();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void verifyListHippodromeWithHorses() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("Horse_" + i, i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horseList);
        List<Horse> actual = hippodrome.getHorses();

        Assert.assertEquals(horseList, actual);
    }

    @Test
    public void checkWhatInvokedMethodMoveAtAllHorses() {
        List<Horse> horsesList = new ArrayList<>();
        Hippodrome hippodrome = new Hippodrome(horsesList);
        Horse mock = mock(Horse.class);

        for (int i = 0; i < 50; i++) {
            horsesList.add(mock);
        }

        hippodrome.move();
        for (Horse horse : horsesList) {
            verify(horse).move();
        }
    }

    @Test
    public void checkWinnerWhenHorseDistanceHasMoreThenOther() {
        List<Horse> horseList = new ArrayList<>();

        Horse horse1 = new Horse("Name1", 1, 20);
        Horse horse2 = new Horse("Name2", 1, 30);

        horseList.add(horse1);
        horseList.add(horse2);

        Hippodrome hippodrome = new Hippodrome(horseList);
        Horse actualWinner = hippodrome.getWinner();

        Assert.assertSame(horse2, actualWinner);
    }

}
