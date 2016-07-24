package samples.powermockito.junit4.bugs.github504;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({VerifyExample.class, VerifyExample2.class})
public class GitHub504Test {

    @Test
    public void testSpy_Good() {
        VerifyExample example = new VerifyExample();
        VerifyExample spy = PowerMockito.spy(example);
        when(spy.getSize(anyCollection())).thenReturn(11);

        assertThat(spy.getSizeSquared(Collections.emptyList())).isEqualTo(121);

        verify(spy).getSizeSquared(Collections.emptyList());
        verify(spy, times(2)).getSize(anyCollection());
        verifyNoMoreInteractions(spy);
    }

    @Test
    public void testSpy_Bad() throws Exception {
        VerifyExample2 example = new VerifyExample2();
        VerifyExample2 spy = Mockito.spy(example);
        when(spy.getSize(anyCollection())).thenReturn(11);

        assertThat(spy.getSizeSquared(Collections.emptyList())).isEqualTo(121);

        verify(spy).getSizeSquared(Collections.emptyList());
        //verifyPrivate(spy).invoke("getSize2", anyC);
        verify(spy, times(2)).getSize(anyCollection()); // <--- Why is it 3 instead of 2?
        verifyNoMoreInteractions(spy);
    }

}
