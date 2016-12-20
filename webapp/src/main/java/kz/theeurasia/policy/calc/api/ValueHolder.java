package kz.theeurasia.policy.calc.api;

public interface ValueHolder<T> {
    T getValue();

    void setValue(T value);
}
