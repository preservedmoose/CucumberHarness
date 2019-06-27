package com.preservedmoose.cucumberharness;

public class StepRowAssertion<T>
{
    private final T _actual;

    public StepRowAssertion(T actual)
    {
        _actual = actual;
    }

    public void Be(T expected, String reason, Object... reasonArguments)
    {
        if (!_actual.equals(expected)) throw new StepRowException(String.format(reason, reasonArguments));
    }

    public void BeNull(String reason, Object... reasonArguments)
    {
        //if (_actual instanceof ValueType) throw new ClassCastException();

        // ReSharper disable once CompareNonConstrainedGenericWithNull
        if (_actual != null) throw new StepRowException(String.format(reason, reasonArguments));
    }

    public void NotBeNull(String reason, Object... reasonArguments)
    {
        //if (_actual instanceof ValueType) throw new ClassCastException();

        // ReSharper disable once CompareNonConstrainedGenericWithNull
        if (_actual == null) throw new StepRowException(String.format(reason, reasonArguments));
    }

    public void BeTrue(String reason, Object... reasonArguments)
    {
        if (!(_actual instanceof Boolean)) throw new ClassCastException();

        Boolean value = (Boolean) _actual;
        if (!value) throw new StepRowException(String.format(reason, reasonArguments));
    }

    public void BeFalse(String reason, Object... reasonArguments)
    {
        if (!(_actual instanceof Boolean)) throw new ClassCastException();

        Boolean value = (Boolean) _actual;
        if (value) throw new StepRowException(String.format(reason, reasonArguments));
    }
}
