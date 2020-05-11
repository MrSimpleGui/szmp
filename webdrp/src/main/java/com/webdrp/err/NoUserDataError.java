package com.webdrp.err;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoUserDataError extends RuntimeException {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NoUserDataError(String message) {
        super(message);
        logger.error(message);
    }
}
