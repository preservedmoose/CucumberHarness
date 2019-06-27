package com.preservedmoose.cucumberharness;

import lombok.var;

public class StepRowAssertionFactory
{
    public static <T>StepRowAssertion<T> Should(T actualValue)
    {
        var stepRowAssertion = new StepRowAssertion<T>(actualValue);
        return stepRowAssertion;
    }
}
