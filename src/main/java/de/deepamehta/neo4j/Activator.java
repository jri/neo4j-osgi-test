package de.deepamehta.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Map;
import java.util.logging.Logger;



public class Activator implements BundleActivator {

    private Logger logger = Logger.getLogger(getClass().getName());

    private GraphDatabaseService neo4j;

    @Override
    public void start(BundleContext context) {
        logger.info("========== Starting Neo4j OSGi Test ==========");
        neo4j = new EmbeddedGraphDatabase("test-db-" + System.currentTimeMillis());
        Index<Node> index = neo4j.index().forNodes("exact");
    }

    @Override
    public void stop(BundleContext context) {
        logger.info("========== Stopping Neo4j OSGi Test ==========");
        if (neo4j != null) {
            neo4j.shutdown();
        }
    }
}
