package com.nutech.backend.entity.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialCode {

    NORMAL("normal");

    private final String type;
}