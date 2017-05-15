java-genetic-programming
========================

java-genetic-programming is a library of java implementation for algorithms in the fields of Genetic Programming.

The main purpose of this library is to provide java developers with a tool set of genetic programming techniques

Installation:
-------------

To install the package using maven, add the following dependency to your POM file:

.. code-block:: xml

    <dependency>
      <groupId>com.github.chen0040</groupId>
      <artifactId>java-genetic-programming</artifactId>
      <version>1.0.3</version>
    </dependency>


Usage
-----

To use the algorithms or data structures in your python code:

.. code-block:: java

    import com.github.chen0040.gp.lgp.LGP;
    import com.github.chen0040.gp.commons.BasicObservation;
    import com.github.chen0040.gp.commons.Observation;
    import com.github.chen0040.gp.lgp.gp.Population;
    import com.github.chen0040.gp.lgp.program.operators.*;

    LGP lgp = new LGP();
    lgp.getOperatorSet().addAll(new Plus(), new Minus(), new Divide(), new Multiply(), new Power());
    lgp.getOperatorSet().addIfLessThanOperator();
    lgp.addConstants(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
    lgp.setRegisterCount(6);
    lgp.getObservations().addAll(trainingData);
    lgp.setCostEvaluator((program, observations)->{
     double error = 0;
     for(Observation observation : observations){
        program.execute(observation);
        error += Math.pow(observation.getOutput(0) - observation.getPredictedOutput(0), 2.0);
     }

     return error;
    });

    long startTime = System.currentTimeMillis();
    Population pop = lgp.newPopulation();
    pop.initialize();
    while (!pop.isTerminated())
    {
     pop.evolve();
     logger.info("Mexican Hat Symbolic Regression Generation: {}, elapsed: {} seconds", pop.getCurrentGeneration(), (System.currentTimeMillis() - startTime) / 1000);
     logger.info("Global Cost: {}\tCurrent Cost: {}", pop.getGlobalBestProgram().getCost(), pop.getCostInCurrentGeneration());
    }

    logger.info("best solution found: {}", pop.getGlobalBestProgram());


Features
--------

* Linear Genetic Programming
* Tree Genetic Programming (Coming Soon)
* Grammatical Evolution (Coming Soon)
* Gene Experssion Programming (Coming Soon)
* NSGA-II Genetic Programming for multi-objective optimization


Tests:
------

the unit tests of all algorithms and data structures can be run with the following command from the root folder:

.. code-block:: bash

    $ /mvnw test -Punit-test


Contributing:
-------------

Contributions are always welcome. Check out the contributing guidelines to get
started.

.. _`docs`: http://java-genetic-programming.readthedocs.org/en/latest/
.. _`documentation`: http://java-genetic-programming.readthedocs.org/en/latest/

Table of Contents:
------------------

.. toctree::
   :maxdepth: 4

   linear-genetic-programming
