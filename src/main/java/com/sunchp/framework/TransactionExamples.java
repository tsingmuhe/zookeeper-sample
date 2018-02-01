package com.sunchp.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;

import java.util.Collection;

public class TransactionExamples {
    public static Collection<CuratorTransactionResult> transaction(CuratorFramework client) throws Exception {
        return client.inTransaction()
                .create().forPath("/a/path", "hello".getBytes())
                .and()
                .setData().forPath("/another/path", "hello".getBytes())
                .and()
                .delete().forPath("/yet/another/path")
                .and()
                .commit();
    }
}
