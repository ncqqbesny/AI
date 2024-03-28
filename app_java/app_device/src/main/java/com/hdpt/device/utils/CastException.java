package com.hdpt.device.utils;

import java.text.MessageFormat;

public class CastException extends RuntimeException{
    private static final long serialVersionUID = -3827654885147659357L;
    private String errorCode;
    public CastException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public CastException(String newerrorCode, Throwable cause)
    {
        super(newerrorCode, cause);
    }

    public CastException(String errorCode, String message, Throwable cause, Object... messageParams)
    {
        super(MessageFormat.format(message, messageParams), cause);
        this.errorCode = errorCode;
    }
}
