package com.cause15.issuetrackerserver.service;


import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.phrase_extractor.KoreanPhraseExtractor;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import org.springframework.stereotype.Service;
import scala.Product;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.List;

@Service
public class OktService {
    public List<String>ExtractKoreanTokens(String text) {
        //정규화
        CharSequence normalized = OpenKoreanTextProcessorJava.normalize(text);
        //토큰화
        Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);

        //추출
        List<KoreanPhraseExtractor.KoreanPhrase> phrases = OpenKoreanTextProcessorJava.extractPhrases(tokens, true, true);
        List<String>ExtractedTokens=new ArrayList<>();
        for(Product product: phrases){
            String str=product.toString();
            int idx=str.indexOf('(');
            if (idx != -1)
                ExtractedTokens.add(str.substring(0,idx));
        }
        return ExtractedTokens;
    }

}