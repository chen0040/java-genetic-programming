# java-genetic-programming
This package provides java implementation of various genetic programming paradigms such as linear genetic programming, tree genetic programming, gene expression programming, etc

[![Build Status](https://travis-ci.org/chen0040/java-genetic-programming.svg?branch=master)](https://travis-ci.org/chen0040/java-genetic-programming) [![Coverage Status](https://coveralls.io/repos/github/chen0040/java-genetic-programming/badge.svg?branch=master)](https://coveralls.io/github/chen0040/java-genetic-programming?branch=master) [![Documentation Status](https://readthedocs.org/projects/java-genetic-programming/badge/?version=latest)](http://java-genetic-programming.readthedocs.io/en/latest/?badge=latest)
                                                                                                                                                                                                                                                                                                                  
More details are provided in the [docs](http://java-genetic-programming.readthedocs.io/en/latest/?badge=latest) for implementation, complexities and further info.

# Install

Add the following dependency to your POM file:

```xml
<dependency>
  <groupId>com.github.chen0040</groupId>
  <artifactId>java-genetic-programming</artifactId>
  <version>1.0.14</version>
</dependency>
```

# Features

* Linear Genetic Programming

    - Initialization
    
       + Full Register Array 
       + Fixed-length Register Array
   
    - Crossover
     
        + Linear
        + One-Point
        + One-Segment
    
    - Mutation
   
        + Micro-Mutation
        + Effective-Macro-Mutation
        + Macro-Mutation
    
    - Replacement
   
        + Tournament
        + Direct-Compete
    
    - Default-Operators
   
        + Most of the math operators
        + if-less, if-greater
        + Support operator extension
        
* Tree Genetic Programming

    - Initialization 
    
        + Full
        + Grow
        + PTC 1
        + Random Branch
        + Ramped Full
        + Ramped Grow
        + Ramped Half-Half
        
    - Crossover
    
        + Subtree Bias
        + Subtree No Bias
        
    - Mutation
    
        + Subtree
        + Subtree Kinnear
        + Hoist
        + Shrink
        
    - Evolution Strategy
    
        + (mu + lambda)
        + TinyGP


    
Future Works

* Grammar-based Genetic Programming
* Gene Expression Programming



# Usage of Linear Genetic Programming

### Create training data

The sample code below shows how to generate data from the "Mexican Hat" regression problem (Please refers to the Tutorials.mexican_hat() in the source code on how to create the data):

```java
List<Observation> data = Tutorials.mexican_hat();
```

We can split the data generated into training and testing data:

```java
List<Observation> data = Tutorials.mexican_hat();
CollectionUtils.shuffle(data);
TupleTwo<List<Observation>, List<Observation>> split_data = CollectionUtils.split(data, 0.9);
List<Observation> trainingData = split_data._1();
List<Observation> testingData = split_data._2();
```
### Create and train the LGP

 
The sample code below shows how the LGP can be created and trained:

```java
import com.github.chen0040.gp.lgp.LGP;
import com.github.chen0040.gp.commons.BasicObservation;
import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.lgp.gp.Population;
import com.github.chen0040.gp.lgp.program.operators.*;

LGP lgp = LGP.defaultConfig();
lgp.setRegisterCount(6); // the number of register here is the number of input dimension of the training data times 3
lgp.setCostEvaluator((program, observations)->{
 double error = 0;
 for(Observation observation : observations){
    program.execute(observation);
    error += Math.pow(observation.getOutput(0) - observation.getPredictedOutput(0), 2.0);
 }

 return error;
});

lgp.setDisplayEvery(10); // to display iteration results every 10 generation

Program program = lgp.fit(trainingData);
System.out.println(program);
```
The number of registers of a linear program is set by calling LGP.setRegisterCount(...), the number of registers is usually the a multiple of the 
input dimension of a training data instance. For example if the training data has input (x, y) which is 2 dimension, then the number of registers
may be set to 6 = 2 * 3.

The cost evaluator computes the training cost of a 'program' on the 'observations'.

The last line prints the linear program found by the LGP evolution, a sample of which is shown below (by calling program.toString()):

```
instruction[1]: <If<	r[4]	c[0]	r[4]>
instruction[2]: <If<	r[3]	c[3]	r[0]>
instruction[3]: <-	r[2]	r[3]	r[2]>
instruction[4]: <*	c[7]	r[2]	r[2]>
instruction[5]: <If<	c[2]	r[3]	r[1]>
instruction[6]: </	r[1]	c[4]	r[2]>
instruction[7]: <If<	r[3]	c[7]	r[1]>
instruction[8]: <-	c[0]	r[0]	r[0]>
...
```

### Test the program obtained from the LGP evolution

The best program in the LGP population obtained from the training in the above step can then be used for prediction, as shown by the sample code below:

```java
for(Observation observation : testingData) {
 program.execute(observation);
 double predicted = observation.getPredictedOutput(0);
 double actual = observation.getOutput(0);

 logger.info("predicted: {}\tactual: {}", predicted, actual);
}
```

# Usage of Tree Genetic Programming

Here we will use the "Mexican Hat" symbolic regression introduced earlier to 

### Create and train the TreeGP

 
The sample code below shows how the TreeGP can be created and trained:

```java
import com.github.chen0040.gp.treegp.TreeGP;
import com.github.chen0040.gp.commons.BasicObservation;
import com.github.chen0040.gp.commons.Observation;
import com.github.chen0040.gp.treegp.gp.Population;
import com.github.chen0040.gp.treegp.program.operators.*;

TreeGP tgp = TreeGP.defaultConfig();
tgp.setVariableCount(2); // equal to the number of input dimension of the training data
tgp.setCostEvaluator((program, observations)->{
 double error = 0;
 for(Observation observation : observations){
    program.execute(observation);
    error += Math.pow(observation.getOutput(0) - observation.getPredictedOutput(0), 2.0);
 }

 return error;
});
tgp.setDisplayEvery(10); // to display iteration results every 10 generation

Solution program = tgp.fit(trainingData);
System.out.println(program.mathExpression());
```

The last line prints the TreeGP program found by the TreeGP evolution, a sample of which is shown below (by calling program.mathExpression()):

```
Trees[0]: 1.0 - (if(1.0 < if(1.0 < 1.0, if(1.0 < v0, 1.0, 1.0), if(1.0 < (v1 * v0) + (1.0 / 1.0), 1.0 + 1.0, 1.0)), 1.0, v0 ^ 1.0))
```

### Test the program obtained from the TreeGP evolution

The best program in the TreeGP population obtained from the training in the above step can then be used for prediction, as shown by the sample code below:

```java
for(Observation observation : testingData) {
 program.execute(observation);
 double predicted = observation.getPredictedOutput(0);
 double actual = observation.getOutput(0);

 logger.info("predicted: {}\tactual: {}", predicted, actual);
}
```
