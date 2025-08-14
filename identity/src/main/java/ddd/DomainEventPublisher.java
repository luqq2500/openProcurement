package ddd;

import java.util.List;

public class DomainEventPublisher {
    @SuppressWarnings("unchecked")
    private static final ThreadLocal<List> subscribers = new ThreadLocal<>();

    private static final ThreadLocal<Boolean> publishing = new ThreadLocal<Boolean>() {
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };
    public static DomainEventPublisher getInstance() {
        return new DomainEventPublisher();
    }
    public DomainEventPublisher() {
        super();
    }
}
