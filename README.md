### Sandal -- Simple rANDom wALk simulation

**LA-CC-16-048**

Sandal is a mini-app of particle-mesh based simulation in terms of
relational tables and queries. The physics simulated is rather simple, it
propagates a set of particles in a constant, 2D wind field with Gaussian
turbulence. Sandal demonstrates the feasibility of formulating a non-trivial
computational physics problem using a functional language and Big Data software
platform, specifically, Scala on Apache Spark.

Java SDK and Simple Build Tool (SBT) are required in order to build and run
the simulation. To build the application,

```
$sbt build
```

and SBT will pull all the dependency over Internet. To run the application,

```
$sbt run
```

The application will dump particle positions in terms of x, y, z coordinate
at each time step.
