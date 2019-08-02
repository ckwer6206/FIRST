package com.kakao.kakaotalk.request;

import android.net.Uri;

import com.kakao.kakaotalk.StringSet;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.MessageTemplateProtocol;
import com.kakao.network.ServerProtocol;
import com.kakao.test.common.KakaoTestCase;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultMessageBroadcastRequestTest extends KakaoTestCase {
    @Override
    public void setup() {
        super.setup();
    }

    @Test
    public void normal() {
        DefaultMessageBroadcastRequest request = new DefaultMessageBroadcastRequest(Arrays.asList(1L, 2L),
                FeedTemplate.newBuilder(
                        ContentObject.newBuilder(
                                "title",
                                "imageUrl",
                                LinkObject.newBuilder().build()
                        ).build()
                ).build());

        Map<String, String> params = request.getParams();
        assertEquals("POST", request.getMethod());

        Uri uri = request.getUriBuilder().build();
        assertEquals(ServerProtocol.TALK_MESSAGE_DEFAULT_V2_PATH, Objects.requireNonNull(uri.getPath()).substring(1));
        assertEquals("1,2", params.get(StringSet.receiver_ids));
        assertNotNull(params.get(MessageTemplateProtocol.TEMPLATE_OBJECT));
    }
}
