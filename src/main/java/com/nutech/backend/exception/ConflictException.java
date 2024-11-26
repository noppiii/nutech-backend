package com.nutech.backend.exception;

import com.nutech.backend.constant.ErrorCode;

public class ConflictException extends CustomException {
    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}