package de.deepamehta.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexProvider;
import org.neo4j.index.lucene.LuceneIndexProvider;
import org.neo4j.kernel.ListIndexIterable;
import org.neo4j.kernel.impl.cache.CacheProvider;
import org.neo4j.kernel.impl.cache.SoftCacheProvider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;



public class Activator implements BundleActivator {

    private GraphDatabaseService neo4j;
    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void start(BundleContext context) {
        logger.info("========== Starting Neo4j 1.8 OSGi Test ==========");
        //
        // the cache providers
        List<CacheProvider> cacheProviders = new ArrayList<CacheProvider>();
        cacheProviders.add(new SoftCacheProvider());
        //
        // the index providers
        List<IndexProvider> provs = new ArrayList<IndexProvider>();
        provs.add(new LuceneIndexProvider());
        ListIndexIterable indexProviders = new ListIndexIterable();
        indexProviders.setIndexProviders(provs);
        //
        // the database setup
        GraphDatabaseFactory factory = new GraphDatabaseFactory();
        factory.setIndexProviders(indexProviders);
        factory.setCacheProviders(cacheProviders);
        neo4j = factory.newEmbeddedDatabase("neoj4-db");
        //
        // create Lucene index
        Index<Node> nodeIndex = neo4j.index().forNodes("nodes");
        //
        logger.info("\n\n*** Neo4j database and Lucene index created successfully! ***\n\n" +
            "type \"lb\"     to see the list of active bundles\n" +
            "type \"stop 0\" to shutdown the OSGi platform\n");
    }

    @Override
    public void stop(BundleContext context) {
        logger.info("========== Stopping Neo4j 1.8 OSGi Test ==========");
        if (neo4j != null) {
            neo4j.shutdown();
        }
    }
}
