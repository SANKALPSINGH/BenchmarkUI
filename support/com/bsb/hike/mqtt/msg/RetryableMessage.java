package com.bsb.hike.mqtt.msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class RetryableMessage extends Message
{

    protected short messageId;

    public RetryableMessage(Header header)
            throws IOException
    {
        super(header);
    }

    public RetryableMessage(Type type)
    {
        super(type);
    }

    @Override
    protected int messageLength()
    {
        return 2;
    }

    @Override
    protected void writeMessage(OutputStream out)
            throws IOException
    {
        int id = getMessageId();
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeShort(id);
    }

    @Override
    protected void readMessage(InputStream in, int msgLength)
            throws IOException
    {
        DataInputStream dis = new DataInputStream(in);
        setMessageId(dis.readShort());
    }

    public void setMessageId(short messageId)
    {
        this.messageId = messageId;
    }

    public short getMessageId()
    {
        if (messageId == -1)
        {
            messageId = nextId();
        }
        return messageId;
    }


}
