package com.kakao.kakaotalk.request;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kakao.kakaotalk.StringSet;
import com.kakao.network.ServerProtocol;
import com.kakao.util.helper.Utility;

import java.util.List;
import java.util.Map;

/**
 * 커스텀 템플릿을 이용한 동보 메시지 전송
 * @author kevinkang
 */
public class CustomMessageBroadcastRequest extends CustomMessageRequest {
    private List<Long> receiverIds;

    public CustomMessageBroadcastRequest(@NonNull List<Long> receiverIds, @NonNull String templateId, @Nullable Map<String, String> args) {
        super(templateId, args);
        this.receiverIds = receiverIds;
    }

    @Override
    public Uri.Builder getUriBuilder() {
        return super.getUriBuilder().path(ServerProtocol.TALK_MESSAGE_SEND_V2_PATH);
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = super.getParams();
        params.put(StringSet.receiver_ids, Utility.asCsv(receiverIds));
        return params;
    }
}
