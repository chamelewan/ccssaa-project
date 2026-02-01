package com.ccssaa.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // User
    USER_NOT_FOUND("U001", "사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL("U002", "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD("U003", "비밀번호가 일치하지 않습니다."),
    
    // Product
    PRODUCT_NOT_FOUND("P001", "상품을 찾을 수 없습니다."),
    INVALID_PRODUCT_STATUS("P002", "유효하지 않은 상품 상태입니다."),
    
    // Transaction
    CANNOT_BUY_OWN_PRODUCT("T001", "본인의 상품은 구매할 수 없습니다."),
    PRODUCT_ALREADY_RESERVED("T002", "이미 예약된 상품입니다."),
    
    // Common
    INVALID_INPUT_VALUE("C001", "입력값이 올바르지 않습니다."),
    UNAUTHORIZED("C002", "인증이 필요합니다."),
    FORBIDDEN("C003", "권한이 없습니다."),
    INTERNAL_SERVER_ERROR("C999", "서버 에러가 발생했습니다.");

    private final String code;
    private final String message;
}
