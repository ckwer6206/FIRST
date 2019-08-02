package com.kakao.kakaotalk.response;

import com.kakao.kakaotalk.StringSet;
import com.kakao.kakaotalk.response.model.MessageFailureInfo;
import com.kakao.test.common.KakaoTestCase;
import com.kakao.util.helper.log.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class MessageSendResponseTest extends KakaoTestCase {
    @Override
    public void setup() {
        super.setup();
    }

    @Test
    public void withoutFailureInfos() throws JSONException {

        JSONObject jsonObject = new JSONObject().put(StringSet.succeed_receiver_ids,
                new JSONArray().put("3651")).put("result_code", 0);

        MessageSendResponse response = MessageSendResponse.CONVERTER.convert(jsonObject.toString());
        assertNotNull(response.succeededIds());
        assertArrayEquals(Collections.singletonList("3651").toArray(), Objects.requireNonNull(response.succeededIds()).toArray());
    }

    @Test
    public void withFailureInfos() throws JSONException {
        Integer code = -532;
        String msg = "daily message limit per sender has been exceeded.";
        String id1 = "26490";
        String id2 = "10050";
        JSONObject jsonObject = new JSONObject().put(
                StringSet.failure_info,
                new JSONArray().put(
                        new JSONObject().put(com.kakao.network.StringSet.code, code)
                                .put(com.kakao.network.StringSet.msg, msg)
                                .put(StringSet.receiver_ids, new JSONArray().put(id2))
                )
        ).put(StringSet.succeed_receiver_ids, new JSONArray().put(id1)).put(StringSet.result_code, -500);

        MessageSendResponse response = MessageSendResponse.CONVERTER.convert(jsonObject.toString());
        Logger.e(response.failureInfos().get(0).getClass().getPackage().getName());
        Logger.e(response.toString());

        assertNotNull(response.succeededIds());
        assertNotNull(response.failureInfos());
        assertEquals(1, Objects.requireNonNull(response.failureInfos()).size());
        MessageFailureInfo failureInfo = Objects.requireNonNull(response.failureInfos()).get(0);
        assertEquals(code.intValue(), failureInfo.code().intValue());
        assertEquals(msg, failureInfo.msg());
        List<String> ids = failureInfo.receiverIds();
        assertEquals(id2, ids.get(0));

//        assertEquals(id2, ids.get(1));
    }

}
