package com.sunchp.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import java.util.List;

public class CrudExamples {
    public static void create(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.create().forPath(path, payload);
    }

    public static void createEphemeral(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, payload);
    }

    public static String createEphemeralSequential(CuratorFramework client, String path, byte[] payload) throws Exception {
        // this will create the given EPHEMERAL-SEQUENTIAL ZNode with the given data using Curator protection.

        /*
            Protection Mode:
            It turns out there is an edge case that exists when creating sequential-ephemeral nodes. The creation
            can succeed on the server, but the server can crash before the created node name is returned to the
            client. However, the ZK session is still valid so the ephemeral node is not deleted. Thus, there is no
            way for the client to determine what node was created for them.
            Even without sequential-ephemeral, however, the create can succeed on the sever but the client (for various
            reasons) will not know it. Putting the create builder into protection mode works around this. The name of
            the node that is created is prefixed with a GUID. If node creation fails the normal retry mechanism will
            occur. On the retry, the parent path is first searched for a node that has the GUID in it. If that node is
            found, it is assumed to be the lost node that was successfully created on the first try and is returned to
            the caller.
         */
        return client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, payload);
    }

    public static void setData(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.setData().forPath(path, payload);
    }

    public static void setDataAsync(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.getCuratorListenable().addListener(new CuratorListener() {
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {

            }
        });

        client.setData().inBackground().forPath(path, payload);
    }

    public static void setDataAsyncWithCallback(CuratorFramework client, BackgroundCallback callback, String path, byte[] payload) throws Exception {
        client.setData().inBackground(callback).forPath(path, payload);
    }

    public static void delete(CuratorFramework client, String path) throws Exception {
        client.delete().forPath(path);
    }

    public static void guaranteedDelete(CuratorFramework client, String path) throws Exception {
        client.delete().guaranteed().forPath(path);
    }

    public static List<String> watchedGetChildren(CuratorFramework client, String path) throws Exception {
        return client.getChildren().watched().forPath(path);
    }

    public static List<String> watchedGetClildren(CuratorFramework client, String path, Watcher watcher) throws Exception {
        return client.getChildren().usingWatcher(watcher).forPath(path);
    }
}
