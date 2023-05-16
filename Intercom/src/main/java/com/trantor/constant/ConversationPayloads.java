package com.trantor.constant;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ConversationPayloads {
    private List<Conversation> payloads;

}