package kz.theeurasia.policy.calc.bean.holder;

import java.io.Serializable;

import kz.theeurasia.policy.calc.api.ValueHolder;

public abstract class DefaultValueHolder<T> implements Serializable, ValueHolder<T> {
    private static final long serialVersionUID = 2619035175557284058L;

    protected T value;

    // GENERATED

    @Override
    public T getValue() {
	return value;
    }

    @Override
    public void setValue(T value) {
	this.value = value;
    }
}
