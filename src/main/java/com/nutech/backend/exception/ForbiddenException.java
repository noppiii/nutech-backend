package com.nutech.backend.exception;

import com.nutech.backend.constant.ErrorCode;

public class ForbiddenException extends CustomException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}