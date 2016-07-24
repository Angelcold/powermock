package org.powermock.api.mockito.internal.verification;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.creation.MockSettingsImpl;
import org.mockito.internal.invocation.InvocationMatcher;
import org.mockito.internal.listeners.MockingProgressListener;
import org.mockito.internal.progress.ArgumentMatcherStorage;
import org.mockito.internal.progress.IOngoingStubbing;
import org.mockito.internal.progress.MockingProgress;
import org.mockito.internal.stubbing.InvocationContainerImpl;
import org.mockito.internal.stubbing.answers.Returns;
import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.invocation.Invocation;
import org.mockito.verification.VerificationMode;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;


public class PowerMockDefaultRegisteredInvocationsTest {
    
    private InvocationContainerImpl container;
    private Invocation invocation;
    private LinkedList<Throwable> exceptions;

    @Before
    public void setUp() throws Exception {
        container = new InvocationContainerImpl(new DummyMockingProgress(), new MockSettingsImpl());
        invocation = new InvocationBuilder().toInvocation();
        exceptions = new LinkedList<Throwable>();
    }

    @Test
    public void should_be_thread_safe() throws Throwable {
        doShouldBeThreadSafe(container);
    }

    //works 50% of the time
    private void doShouldBeThreadSafe(final InvocationContainerImpl invocationContainer) throws Throwable {
        //given
        Thread[] t = new Thread[200];
        final CountDownLatch starter = new CountDownLatch(200);
        for (int i = 0; i < t.length; i++ ) {
            t[i] = new Thread() {
                public void run() {
                    try {
                        starter.await(); //NOPMD
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    invocationContainer.setInvocationForPotentialStubbing(new InvocationMatcher(invocation));
                    invocationContainer.addAnswer(new Returns("foo"));
                    invocationContainer.findAnswerFor(invocation);
                }
            };
            t[i].setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                public void uncaughtException(Thread t, Throwable e) {
                    exceptions.add(e);
                }
            });
            t[i].start();

            starter.countDown();
        }

        //when
        for (Thread aT : t) {
            aT.join();
        }

        //then
        if (exceptions.size() != 0) {
            throw exceptions.getFirst();
        }
    }

    @Test
    public void should_return_invoked_mock() throws Exception {
        container.setInvocationForPotentialStubbing(new InvocationMatcher(invocation));

        assertThat(invocation.getMock()).isEqualTo(container.invokedMock());
    }

    @Test
    public void should_tell_if_has_invocation_for_potential_stubbing() throws Exception {
        container.setInvocationForPotentialStubbing(new InvocationBuilder().toInvocationMatcher());
        assertThat(container.hasInvocationForPotentialStubbing()).isTrue();

        container.addAnswer(new ReturnsEmptyValues());
        assertThat(container.hasInvocationForPotentialStubbing()).isFalse();
    }
    
    private class DummyMockingProgress implements MockingProgress {
        @Override
        public void reportOngoingStubbing(IOngoingStubbing iOngoingStubbing) {

        }

        @Override
        public IOngoingStubbing pullOngoingStubbing() {
            return null;
        }

        @Override
        public void verificationStarted(VerificationMode verificationMode) {

        }

        @Override
        public VerificationMode pullVerificationMode() {
            return null;
        }

        @Override
        public void stubbingStarted() {

        }

        @Override
        public void stubbingCompleted(Invocation invocation) {

        }

        @Override
        public void validateState() {

        }

        @Override
        public void reset() {

        }

        /**
         * Removes ongoing stubbing so that in case the framework is misused
         * state validation errors are more accurate
         */
        @Override
        public void resetOngoingStubbing() {

        }

        @Override
        public ArgumentMatcherStorage getArgumentMatcherStorage() {
            return null;
        }

        @Override
        public void mockingStarted(Object mock, Class classToMock) {

        }

        @Override
        public void setListener(MockingProgressListener listener) {

        }
    }
}