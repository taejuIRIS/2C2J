package com.todo.backend.entity;

public enum DoType {
    DO, NOT, DETOX;

    public static DoType returnType(String Dotype) {
        try {
            return DoType.valueOf(Dotype);
        } catch (IllegalArgumentException e) { // 예외 처리: 정의되지 않은 값인 경우
            return DoType.DO;
        }
    }
}
