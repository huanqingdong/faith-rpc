package tech.nosql.rpc.transport;

import org.apache.commons.io.IOUtils;
import tech.nosql.rpc.protocol.Peer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author faith.huan 2020-02-20 20:47
 */
public class HttpTransportClient implements TransportClient {

    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public byte[] write(byte[] data) {
        try {
            HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setRequestMethod("POST");

            httpConnection.connect();
            httpConnection.getOutputStream().write(data);
            // IOUtils.copy(data,httpConnection.getOutputStream());

            int responseCode = httpConnection.getResponseCode();

            InputStream respIps;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                respIps = httpConnection.getInputStream();
            } else {
                respIps = httpConnection.getErrorStream();
            }

            return IOUtils.readFully(respIps, respIps.available());

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
