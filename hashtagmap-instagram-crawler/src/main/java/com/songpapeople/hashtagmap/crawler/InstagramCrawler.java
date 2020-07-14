package com.songpapeople.hashtagmap.crawler;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.songpapeople.hashtagmap.HashtagDto;
import org.jsoup.nodes.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstagramCrawler {
    private static final String INSTAGRAM_URL_FORMAT = "https://www.instagram.com/explore/tags/%s/?hl=ko";
    private static final String HASHTAG_COUNT_REGEX = "\"edge_hashtag_to_media\":\\{\"count\":[0-9]+";
    private static final String HASHTAG_COUNT_BEFORE_STRING = "count\":";
    private static final String HASHTAG_POST_INFO_REGEX = "\"edge_hashtag_to_top_posts\":";

    private static int findLastIdx(String substringBody) {
        int bracketCount = 0;
        for (int i = 0; i < substringBody.length(); i++) {
            if (substringBody.charAt(i) == '{') {
                bracketCount++;
                continue;
            }
            if (substringBody.charAt(i) == '}') {
                bracketCount--;
            }
            if (bracketCount == 0) {
                return i;
            }
        }
        return 0;
    }

    public HashtagDto createHashtagDto(String placeName) {
        Document doc = Crawler.crawling(String.format(INSTAGRAM_URL_FORMAT, placeName));
        String hashtagCount = findHashtagCount(doc.head().toString());

        String popularInfo = slicePopularInfo(doc.body().toString());

        JsonElement json = JsonParser.parseString(popularInfo);
        return null;
    }

    private String findHashtagCount(String head) {
        Pattern pattern = Pattern.compile(HASHTAG_COUNT_REGEX);
        Matcher matcher = pattern.matcher(head);

        if (matcher.find()) {
            String hashtagCount = matcher.group().split(HASHTAG_COUNT_BEFORE_STRING)[1];
            return hashtagCount;
        }
        throw new IllegalArgumentException("해시태그 카운트를 찾을 수 없습니다.");
    }

    private String slicePopularInfo(String body) {
        int startIdx = body.lastIndexOf(HASHTAG_POST_INFO_REGEX) + HASHTAG_POST_INFO_REGEX.length();
        String substringBody = body.substring(startIdx);

        int lastIdx = findLastIdx(substringBody);

        return substringBody.substring(0, lastIdx + 1);
    }
}
