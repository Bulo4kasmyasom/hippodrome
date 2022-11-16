import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Timeout;

public class MainTest {

    @Test
    @Timeout(value = 22)
    @Disabled
    public void checkTimeoutExecuteMainMethod() throws Exception {
        Main.main(null);
    }

}
