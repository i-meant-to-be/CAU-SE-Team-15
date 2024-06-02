package com.cause15.issuetrackerserver.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;

public class OktServiceTest {
    @Test
    void testTokenizer() {
        String text="프롬프트의 임베딩이 잘못되었어요.";
        OktService oktService = new OktService();
        List<String> ret=oktService.ExtractKoreanTokens(text);
        List<String> temp = Arrays.asList("프롬프트", "프롬프트의 임베딩", "잘못", "임베딩");
        assertThat(ret).isEqualTo(temp);
    }
}
