package kz.theeurasia.policy.calc.api.holder;

public interface ValueHolder<T> {
    T getValue();

    void setValue(T value);
}
