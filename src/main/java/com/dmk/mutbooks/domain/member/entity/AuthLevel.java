package com.dmk.mutbooks.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthLevel {

    NORMAL(3), ADMIN(7);

    private final int level;
}
