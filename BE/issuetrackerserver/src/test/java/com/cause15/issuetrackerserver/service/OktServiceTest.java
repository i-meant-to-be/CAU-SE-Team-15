package com.cause15.issuetrackerserver.service;

import org.junit.jupiter.api.Test;

import java.util.List;

public class OktServiceTest {
    @Test
    void testTokenizer() {
        String text="프롬프트의 임베딩이 잘못되었어요. 요청 바디형식이 잘못되어 500에러가 발생하네요";
        OktService oktService = new OktService();
        List<String> ret=oktService.ExtractKoreanTokens(text);
        System.out.println(ret);
    }
}
