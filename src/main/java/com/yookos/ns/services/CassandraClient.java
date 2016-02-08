package com.yookos.ns.services;

import com.datastax.driver.core.Cluster;
import org.springframework.stereotype.Component;

/**
 * Created by jome on 2016/01/19.
 */

@Component
public class CassandraClient {
    private Cluster cluster;

}
