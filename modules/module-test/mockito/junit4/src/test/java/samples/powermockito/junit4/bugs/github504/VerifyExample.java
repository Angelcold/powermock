package samples.powermockito.junit4.bugs.github504;

import java.util.Collection;

/**
 *
 */
public class VerifyExample {

    public int getSizeSquared(final Collection c) {
        return getSize(c) * getSize(c);
    }

    public int getSize(final Collection c) {
        return c.size();
    }

}
