package com.nutech.backend.exception;

import com.nutech.backend.constant.ErrorCode;

public class InvalidException extends CustomException {
    public InvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
