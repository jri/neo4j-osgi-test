
Run Neo4j OSGi Test
===================

The branch `bundle-friendly-classloader` of Neo4j 1.5-SNAPSHOT contains a fix that enables the use of the lucene indexer while running in OSGi.

The error "No index provider 'lucene' found" disappears.

Build Neo4j:

    git clone git://github.com/neo4j/community.git
    cd community
    git checkout bundle-friendly-classloader
    git pull
    mvn clean install
    cd ..

Build Neo4j OSGi bundle and run test via Pax Exam:

    git clone git://github.com/neo4j/neo4j-osgi.git
    cd neo4j-osgi
    mvn clean install
    cd ..

Build and run test in standalone Felix:

    git clone git://github.com/jri/neo4j-osgi-test.git
    cd neo4j-osgi-test
    mvn clean install pax:run

This provisions a Felix runtime (with the aid of Pax Runner) and installs to 2 bundles: the Neo4j bundle and the user application's activator.

If the line "Lucene index successfully created!" appears the test is successful.  
(leave the Felix shell with `stop 0`)

Thanks to Peter Neubauer (Neo4j) and Toni Menzel (OPS4J)!


Scenarios tried
===============

To test the various scenarios tried checkout the respective version tag and run `mvn clean install pax:run`

**0.5** - Activator in separate bundle. => SUCCESS!  
          Provided the `bundle-friendly-classloader` branch of Neo4j 1.5-SNAPSHOT is installed

**0.4** - One super bundle with inlined dependencies. => FAILURE

**0.3** - Activator and all neo packages in one bundle again, but in a proper one  
          (additional inlining of embedded jars suppressed). => FAILURE

**0.2** - Activator in separate bundle. Deploys "official" neo-super-bundle from github. => FAILURE

**0.1** - Activator and neo-super-bundle packed into one bundle. => FAILURE
