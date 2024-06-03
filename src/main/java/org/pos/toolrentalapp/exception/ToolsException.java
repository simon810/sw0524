package org.pos.toolrentalapp.exception;

import lombok.Getter;

@Getter
public class ToolsException extends Exception{
    public ToolsException(String message, Throwable cause) {
        super(message, cause);
    }
    public ToolsException(String message) {
        super(message);
    }
}
