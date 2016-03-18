package com.bsb.hike.mqtt.msg;

import java.io.IOException;

public class PubRelMessage extends RetryableMessage
{

    public PubRelMessage(short messageId)
    {
        super(Type.PUBREL);
        setMessageId(messageId);
    }

    public PubRelMessage(Header header)
            throws IOException
    {
        super(header);
    }
}
