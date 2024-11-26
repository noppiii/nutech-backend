package com.nutech.backend.exception;

import com.nutech.backend.constant.ErrorCode;

public class BadRequestException extends CustomException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
