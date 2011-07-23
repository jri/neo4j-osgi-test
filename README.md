
Preparation
===========

    git clone git://github.com/neo4j/neo4j-osgi.git
    cd neo4j-osgi
    mvn clean install

    cd ..
    git clone git://github.com/jri/neo4j-osgi-test.git
    cd neo4j-osgi-test


Run
===

For description of the various scenarios see the version history

Scenario 1

    git checkout 0.1
    mvn clean install pax:run

Scenario 2

    git checkout 0.2
    mvn clean install pax:run

Scenario 3

    git checkout 0.3
    mvn clean install pax:run

Scenario 4

    git checkout 0.4
    mvn clean install pax:run


Version History
===============

**0.4** - One super bundle with inlined dependencies.

**0.3** - Activator and all neo packages in one bundle again, but in a proper one
          (additional inlining of embedded jars suppressed).

**0.2** - Activator in separate bundle. Deploys "official" neo-super-bundle from github.

**0.1** - Activator and neo-super-bundle packed into one bundle.
