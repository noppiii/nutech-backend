package com.nutech.backend.exception;

import com.nutech.backend.constant.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}