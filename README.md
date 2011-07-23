
Run
===

    git clone git://github.com/neo4j/neo4j-osgi.git
    cd neo4j-osgi
    mvn clean install

    cd ..
    git clone git://github.com/jri/neo4j-osgi-test.git
    cd neo4j-osgi-test
    mvn clean install pax:run


Version History
===============

**0.2** - Activator in separate bundle. Deploys "official" neo-super-bundle from github.

**0.1** - Activator and neo-super-bundle packed into one bundle.
