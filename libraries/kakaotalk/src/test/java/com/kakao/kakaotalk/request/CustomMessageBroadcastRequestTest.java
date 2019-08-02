package com.kakao.kakaotalk.request;

import android.net.Uri;

import com.kakao.kakaotalk.StringSet;
import com.kakao.network.ServerProtocol;
import com.kakao.test.common.KakaoTestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class CustomMessageBroadcastRequestTest extends KakaoTestCase {
    @Override
    public void setup() {
        super.setup();
    }

    @Test
    public void normal() {
        CustomMessageBroadcastRequest request = new CustomMessageBroadcastRequest(Collections.singletonList(1L), "1234", null);
        Map<String, String> params = request.getParams();
        assertEquals("1", params.get(StringSet.receiver_ids));
        assertEquals("1234", params.get(StringSet.template_id));
        assertNull(params.get(StringSet.template_args));
    }

    @Test
    public void twoIds() throws JSONException {
        Map<String, String> args = new HashMap<>();
        args.put("key1", "value1");
        args.put("key2", "value2");
        CustomMessageBroadcastRequest request = new CustomMessageBroadcastRequest(Arrays.asList(1L, 2L), "1234", args);

        assertEquals("POST", request.getMethod());

        Uri uri = request.getUriBuilder().build();
        assertEquals(ServerProtocol.TALK_MESSAGE_SEND_V2_PATH, Objects.requireNonNull(uri.getPath()).substring(1));
        Map<String, String> params = request.getParams();
        assertEquals("1,2", params.get(StringSet.receiver_ids));
        assertEquals("1234", params.get(StringSet.template_id));
        assertEquals(new JSONObject().put("key1", "value1").put("key2", "value2").toString(),
                params.get(StringSet.template_args));
    }
}
