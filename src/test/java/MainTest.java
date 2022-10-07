import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class MainTest {

    @Test
    @Timeout(value = 22)
    @Disabled
    public void checkTimeExecute() throws Exception {
        Main.main(null);
    }

}
