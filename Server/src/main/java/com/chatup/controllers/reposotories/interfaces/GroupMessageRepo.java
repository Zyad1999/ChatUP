package com.chatup.controllers.reposotories.interfaces;

import com.chatup.models.entities.GroupMessage;

import java.util.List;

public interface GroupMessageRepo {
    // create a message - return groupMessageId
    int createGroupMessage(GroupMessage groupMessage);

    // read message by id - return Message
    GroupMessage getGroupMessage(int groupMessageId);

    // update
    boolean updateGroupMessage(GroupMessage groupMessage);

    // delete
    boolean deleteGroupMessage(int groupMessageId);
}