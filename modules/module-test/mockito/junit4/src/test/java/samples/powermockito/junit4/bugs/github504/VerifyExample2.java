package samples.powermockito.junit4.bugs.github504;

import java.util.Collection;

/**
 *
 */
public class VerifyExample2 {

    public int getSizeSquared(final Collection c) {
        return getSize(c) * getSize(c);
    }

    public int getSize(final Collection c) {
        // Same implementation as TimesExample, we're just called a private method instead.
        return getSize2(c);
    }

    public int getSize2(final Collection c) {
        return c.size();
    }
}
