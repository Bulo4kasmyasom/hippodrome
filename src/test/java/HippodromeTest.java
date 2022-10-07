import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {

    @Test
    public void shouldBeExceptionWhenParamIsNull()
    {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void shouldBeExceptionWhenParamIsNullGetMessage()
    {
        IllegalArgumentException exception = Assert.assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );
        Assert.assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void shouldBeExceptionWhenListSizeIsEmpty()
    {
        List<Horse> horses = new ArrayList<>();
        Assert.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
    }

    @Test
    public void shouldBeExceptionWhenListSizeIsEmptyGetMessage()
    {
        List<Horse> horses = new ArrayList<>();
        IllegalArgumentException exception = Assert.assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(horses)
        );
        Assert.assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void verifyListHippodromeWithHorses()
    {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("Horse_"+i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        Assert.assertEquals(horseList, hippodrome.getHorses());
    }

    @Test
    public void verifyMoveHorse()
    {
        List<Horse> horsesList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horsesList.add(mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horsesList);
        hippodrome.move();
        for (Horse horse : horsesList) {
            verify(horse).move();
        }
    }

    @Test
    public void checkWinner()
    {
        Horse name1 = new Horse("Name1", 1, 2);
        Horse name2 = new Horse("Name2", 1, 2);
        Horse name3 = new Horse("Name3", 1, 2);
        Horse name4 = new Horse("Name4", 5, 20);

        List<Horse> horseList = new ArrayList<>(){{
           add(name1);
           add(name2);
           add(name3);
           add(name4);
        }};

        Hippodrome hippodrome = new Hippodrome(horseList);
        Assert.assertSame(name4, hippodrome.getWinner());
    }

}
