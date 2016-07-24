package org.powermock.api.mockito.internal.verification;

import org.mockito.internal.util.ObjectMethodsGuru;
import org.mockito.internal.util.collections.ListUtil;
import org.mockito.internal.util.collections.ListUtil.Filter;
import org.mockito.internal.verification.RegisteredInvocations;
import org.mockito.invocation.Invocation;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class PowerMockDefaultRegisteredInvocations implements RegisteredInvocations, Serializable {

    private final LinkedList<Invocation> invocations = new LinkedList<Invocation>();

    public void add(Invocation invocation) {
        synchronized (invocations) {
            invocations.add(invocation);
        }
    }

    public void removeLast() {
        synchronized (invocations) {
            if (!invocations.isEmpty()) {
                removeLastPublic();
            }
        }
    }

    private void removeLastPublic() {
        ListIterator<Invocation> iterator = invocations.listIterator(invocations.size());
        while (iterator.hasPrevious()) {
            Invocation invocation = iterator.previous();
            if (Modifier.isPublic(invocation.getMethod().getModifiers())) {
                iterator.remove();
                return;
            }
        }
    }

    public List<Invocation> getAll() {
        List<Invocation> copiedList;
        synchronized (invocations) {
            copiedList = new LinkedList<Invocation>(invocations);
        }

        return ListUtil.filter(copiedList, new RemoveToString());
    }

    public boolean isEmpty() {
        synchronized (invocations) {
            return invocations.isEmpty();
        }
    }

    private static class RemoveToString implements Filter<Invocation> {
        public boolean isOut(Invocation invocation) {
            return new ObjectMethodsGuru().isToString(invocation.getMethod());
        }
    }
}
