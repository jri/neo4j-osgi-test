
Neo4j 1.8 OSGi Test / Pax Runner
================================

This demo project shows that a Java-embedded Neo4j 1.8 instance -- in particular its Lucene Index component -- can run in an OSGi environment.

The history so far:

- Neo4j 1.2 in conjunction with its "old" index API was the last version which runs in an OSGi environment. Neo4j's new index API is based on service lookup (Java ServiceLoader) which doesn't work with OSGi.

- Neo4j 1.8 introduces a mechanism which allows programmatic injection of the Lucene Index provider. This workarounds the service lookup problem and eventually enables Neo4j to run in an OSGi environment. This is great news for the Neo4j/OSGi fans.

- Unfortunately the Neo4j 1.8 libraries doesn't come as OSGi bundles anymore (Neo4j 1.2 did!). However, the Pax Runner tool makes it easy to transform the libraries into bundles ("auto-wrapping").

This project demonstrates how to provision an Apache Felix OSGi runtime and how to auto-wrap the Neo4j and Apache Lucene libraries on-the-fly by the means of Pax Runner. The demo might provide the basis for your own Neo4j/OSGi project.

And here is the gotcha:

    The JTA Spec bundle must be activated *before* the Neo4j Kernel bundle.

You can do so by ensuring `geronimo-jta_1.1_spec` is listed before `neo4j-kernel` in the Pax Runner's provision commands (see `pom.xml`):

    <provision>
        <param>mvn:org.apache.geronimo.specs/geronimo-jta_1.1_spec/1.1.1</param>
        <param>mvn:org.neo4j/neo4j-kernel/1.8</param>
        ...
        <param>--autoWrap</param>
    </provision>

Alternatively you can set explicit OSGi start levels by appending **`@n`** to a provision command.  
The start level of `geronimo-jta_1.1_spec` must be lower than the `neo4j-kernel` ones.

    <provision>
        <param>mvn:org.neo4j/neo4j-kernel/1.8</param>
        <param>mvn:org.apache.geronimo.specs/geronimo-jta_1.1_spec/1.1.1@4</param>
        ...
        <param>--autoWrap</param>
    </provision>

Here, `4` is explicitly set for `geronimo-jta_1.1_spec`. Pax Runners's default start level is `5`.

If, on the other hand, `geronimo-jta_1.1_spec` is activated *after* `neo4j-kernel` an error occurs:

    java.lang.ClassNotFoundException: javax.transaction.TransactionManager


Running the demo
----------------

Build:

    git clone git://github.com/jri/neo4j-osgi-test.git
    cd neo4j-osgi-test
    mvn clean install

Run:

    mvn pax:run

Stop OSGi platform:

    stop 0

The demo activates 5 bundles (and auto-wrapps the first 3 of them):

- Neo4j Kernel 1.8
- Neo4j Lucene Index 1.8
- Apache Lucene Core 3.5.0
- Java Transaction API (JTA) Spec 1.1
- The test activator

The test activator does nothing but creating an embedded Neo4j instance and a Lucene index.  
If everything went fine a success message is printed.

Experimenting with the POM requires no rebuilding. Just run `mvn pax:run` again.  
To list the active bundles (along with its start levels) use the `lb` command.

The actual provider injection code is taken from here:  
<http://docs.neo4j.org/chunked/stable/tutorials-java-embedded-osgi.html>

Thanks to Peter Neubauer from Neo4j to point me there!


Neo4j/OSGi tests performed
--------------------------

(The left number is the version=tag of this demo project)

**0.6** - Test with Neo4j 1.8 => SUCCESS!

Older tests with Neo4j 1.5:

**0.5** - Activator in separate bundle. => SUCCESS!  
          Provided the `bundle-friendly-classloader` branch of Neo4j 1.5-SNAPSHOT is installed

**0.4** - One super bundle with inlined dependencies. => FAILURE

**0.3** - Activator and all neo packages in one bundle again, but in a proper one  
          (additional inlining of embedded jars suppressed). => FAILURE

**0.2** - Activator in separate bundle. Deploys "official" neo-super-bundle from github. => FAILURE

**0.1** - Activator and neo-super-bundle packed into one bundle. => FAILURE
