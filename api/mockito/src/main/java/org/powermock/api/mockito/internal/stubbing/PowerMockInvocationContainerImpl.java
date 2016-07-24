package org.powermock.api.mockito.internal.stubbing;

import org.mockito.internal.progress.MockingProgress;
import org.mockito.internal.stubbing.InvocationContainerImpl;
import org.mockito.mock.MockCreationSettings;
import org.powermock.api.mockito.internal.verification.PowerMockDefaultRegisteredInvocations;
import org.powermock.reflect.Whitebox;

/**
 *
 */
public class PowerMockInvocationContainerImpl extends InvocationContainerImpl {

    public PowerMockInvocationContainerImpl(MockingProgress mockingProgress, MockCreationSettings mockSettings) {
        super(mockingProgress, mockSettings);
        if (!mockSettings.isStubOnly()) {
            Whitebox.setInternalState(this, "registeredInvocations", new PowerMockDefaultRegisteredInvocations());
        }
    }

}

