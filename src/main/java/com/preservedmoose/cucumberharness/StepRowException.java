package com.preservedmoose.cucumberharness;

public class StepRowException extends RuntimeException
{
    private static final long serialVersionUID = 3833597061605819261L;

    public StepRowException()
    {
    }

    public StepRowException(String message)
    {
        super(message);
    }

    public StepRowException(String message, Exception innerException)
    {
        super(message, innerException);
    }
}
