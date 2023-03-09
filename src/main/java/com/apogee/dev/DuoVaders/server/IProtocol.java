package com.apogee.dev.DuoVaders.server;

public interface IProtocol {
    public void execute(IContext context, java.io.InputStream in, java.io.OutputStream out);
}
