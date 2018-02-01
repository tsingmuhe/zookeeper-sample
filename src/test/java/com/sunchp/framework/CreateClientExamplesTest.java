package com.sunchp.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Assert;
import org.junit.Test;

public class CreateClientExamplesTest {
    @Test
    public void createSimple() {
        CuratorFramework curatorFramework = CreateClientExamples.createSimple("127.0.0.1:2181");
        Assert.assertNotNull(curatorFramework);
    }

    @Test
    public void createWithOptions() {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CreateClientExamples.createWithOptions("127.0.0.1:2181", retryPolicy, 15 * 1000, 60 * 1000);
        Assert.assertNotNull(curatorFramework);
    }
}