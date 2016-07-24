package org.mockito.internal.handler;

import org.mockito.internal.creation.MockSettingsImpl;
import org.mockito.internal.stubbing.InvocationContainerImpl;
import org.powermock.api.mockito.internal.stubbing.PowerMockInvocationContainerImpl;

/**
 *  @see PowerMockMockHandlerFactory
 */
public class PowerMockMockHandlerImpl extends MockHandlerImpl {
    public PowerMockMockHandlerImpl(MockSettingsImpl mockSettings) {
        super(mockSettings);
        this.invocationContainerImpl = new PowerMockInvocationContainerImpl(mockingProgress, mockSettings);
    }
}
