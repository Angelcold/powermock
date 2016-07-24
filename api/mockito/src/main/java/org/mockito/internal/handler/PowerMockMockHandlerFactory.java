package org.mockito.internal.handler;

import org.mockito.internal.InternalMockHandler;
import org.mockito.internal.creation.MockSettingsImpl;
import org.mockito.internal.stubbing.InvocationContainerImpl;

/**
 *  The PowerMock class is created in the Mockito namespace, because I need custom implementation of the
 *  {@link MockHandlerImpl} and this class is package-private. I need custom implementation to create a PowerMock's
 *  instance of {@link InvocationContainerImpl} to correct handle invocation of private methods when public method is
 *  called during stubbing.
 *  For more information see https://github.com/jayway/powermock/issues/504
 *
 */
public class PowerMockMockHandlerFactory {
    public static InternalMockHandler create(MockSettingsImpl settings) {
        InternalMockHandler handler = new PowerMockMockHandlerImpl(settings);
        InternalMockHandler nullResultGuardian = new NullResultGuardian(handler);

        return new InvocationNotifierHandler(nullResultGuardian, settings);
    }
}
