package com.nutech.backend.exception;

import com.nutech.backend.constant.ErrorCode;

public class DuplicateException extends CustomException {
    public DuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}