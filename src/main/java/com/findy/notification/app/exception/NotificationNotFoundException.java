package com.findy.notification.app.exception;

import com.findy.common.exception.ApplicationException;

public class NotificationNotFoundException extends ApplicationException {
    private static final String CODE = "NOTIFICATION_NOT_FOUND";
    private static final int STATUS = 404;

    public NotificationNotFoundException(String message) {
        super(CODE, message, STATUS);
    }
}
