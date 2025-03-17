+++
date = '2025-02-26T14:43:17-07:00'
draft = true
title = 'Flow Matching for Generative Modeling'
+++

Problems identified:

1. **Uniqueness:** There are many probability paths \( p_t\pn{\vect{x}} \) such that \( p_1\pn{\vect{x}} \approx q\pn{\vect{x}} \).
    * Trying to learn a vector field \( \vect{v}_t \) to generate _a_ probability path without more contraints to specify _what_ probability path causes issuse because a machine learning model may try to converge toward approximating a different probability path each batch or epoch.
1. If a particular \( p_t\pn{\vect{x}} \) was given, we do not automatcially know what the vector field \( \vect{u}_t \) that generates \( p_t\pn{\vect{x}} \) is, meaning we cannot use it in a loss function to approximate it with a machine learning model.

Solution:

Given \( \vect{x}_0 \sim p_0\pn{\vect{x}} \) and \( \vect{x}_1 \sim p_1\pn{\vect{x}} \), a _conditional_ probability path \( p_t\pn{\vect{x}|\vect{x}_1} \) can be constructed where the generating _conditional_ vector field, denoted \( \vect{u}_t\pn{\vect{x}|\vect{x}_1} \), is known and is computable.[^what-is-a-conditional-vector-field]
By aggregating the conditional vector fields, an expression for a vector field \( \vect{u}_t \) that generates some probability path \( p_t\pn{\vect{x}} \) satisfying the required boundary conditions is found.
Furthermore, the probability path is the marginal of the conditional probability paths.
Unfortunately, this expression is also intractable, but it turns out that training a machine learning model to approximate \( \vect{u}_t\pn{\vect{x}|\vect{x}_1} \) is the same as training it to approximate \( \vect{u}_t\pn{\vect{x}} \).

Assumptions:

* data-are-independent-identically-distributed.md
* data-can-be-represented-by-vectors-in-Rd.md
* discrete-time-steps-are-positive.md
* probability-path-densities-are-always-greater-than-zero.md

[^what-is-a-conditional-vector-field]: The vector field \( \vect{u}_t\pn{\vect{x}|\vect{x}_1} \) is called the _conditional_ vector field because it generates the conditional probability path.

**Proof read later:**

Suppose we have a samples
${ data(datum: datumIdx) }_(datumIdx = 1)^dataCount$
drawn from a data domain #dataDomain following some unknown probability distribution #probData.
We want to learn a probability distribution #probDataApprox that approximates #probData that allows us to generate samples
$data() ~ probDataApprox$.

The goal of flow matching is to learn a function that allows us generate a sample #latentVar from a known distribution #probLatent (i.e., a Gaussian) and transport it to a sample of #probDataApprox.
The flow matching approach is to learn a vector field #probVecField that transports #probLatent to #probDataApprox.
To learn #probVecField, we need to observe it for samples
$data() ~ probDataApprox$
and
$latentVar ~ probLatent$,
as well the samples when they are being transported (i.e., #dataPath() for $t in [0, 1]$).
Since #data() and #latentVar are random variables, #dataPath() are also random variables, which we say are distributed following some probability distribution #probPath().
Therefore, #dataPath() for $t in [0, 1]$ follows the probability distribution #probPath().
// Therefore, as #latentVar is transported to #data() along the trajectory #dataPath(), the probability distribution #probLatent is transported to #probDataApprox by the trajectory of probability distributions #probPath().
The trajectory of a probability distribution being transported by a vector field can be expressed using the continuity equation:
$
( diff probPath() ) / ( diff t ) = nabla_dataPath(t: "") dot ( probVecField probPath() )
$
The continuity equation encodes the requirement that probability mass is preserved for all time.
Let #probVecField be the true vector field and #probVecFieldLearned be our machine learning model.

We would like to train our model with the loss function
$
lossFunc = expectation_(t,probPath()(dataPath(t: ""))) [ norm(probVecField(t, dataPath(t: "")) - probVecFieldLearned(t, dataPath(t: "")))^2 ]
$
// #TODO[(#dataPath() is a random variable so it needs to be drawn from its corresponding probability distribution; this is why we want the probability path to satisfy the continuity equation, the masses at times between 0 and 1 must be probabilities since #dataPath() is a random variable!)]
but we do not have access to #probVecField to regress #probVecFieldLearned onto.

We solve this issue by using our dataset samples
${ data(datum: datumIdx) }_(datumIdx = 1)^dataCount$
for conditioning.
The distribution #probDataApprox can be written as the maginalization
$
probDataApprox(dataPath(t: "")) = integral probDataApprox(dataPath(t: "")|data())probData(data()) dif data()
$
Since,
$probDataApprox(dataPath(t: "")|data())$
can be the transportation target of some distribution
$probLatent(dataPath(t: "")|data())$,
we can express its transport using the continuity equation again:
$
diff / (diff t) probPath()(dataPath(t: "")|data()) = nabla_dataPath(t: "") dot (probVecField(t, dataPath(t: "")|data())probPath()(dataPath(t: "")|data()))
$
where
$probVecField(t, dataPath(t: "")|data())$
is some vector field for the conditional probability;
hence, @lipmanFlowMatchingGenerative2023 calls it the "conditional vector field."
Note that we can choose both
$probDataApprox(dataPath(t: "")|data())$
and
$probVecField(dataPath(t: "")|data())$.
By writing the marginalization integral, we declare that there is a dependence on #data(), and then we must choose how the form of the conditioning (how the observation of #data() affects #probDataApprox).
These choices will be explored later.

It turns out that the marginalized vector field #probVecField can be written in terms of the conditional vector fields generated by our data samples
${ data(datum: datumIdx) }_(datumIdx = 1)^dataCount$.
We make the ansatz
$
probVecField(dataPath(t: "")) = integral probVecField(dataPath(t: "")|data()) w(dataPath(t: ""), data()) dif data()
$
for some weighting function
$w(dataPath(t: ""), data())$.
To find this weighting function, use the continuity equation associated with the marginalized #probDataApprox:
$
(diff probPath()) / (diff t) & = diff / (diff t) integral probPath()(dataPath(t: "")|data())probData(data()) dif data() \
& = integral (diff / (diff t) probPath()(dataPath(t: "")|data()))probData(data()) dif data() \
& = integral (nabla_dataPath(t: "") dot (probVecField(dataPath(t: "")|data())probPath()(dataPath(t: "")|data())))probData(data()) dif data() \
& = nabla_dataPath(t: "") dot integral probVecField(dataPath(t: "")|data())probPath()(dataPath(t: "")|data())probData(data()) dif data() \
& = nabla_dataPath(t: "") dot probPath()(dataPath(t: "")) integral probVecField(dataPath(t: "")|data())underbrace((probPath()(dataPath(t: "")|data())probData(data())) / (probPath()(dataPath(t: ""))), w(dataPath(t: ""), data())) dif data() \
& = nabla_dataPath(t: "") dot (probVecField probPath())
$
Unfortunately, we still do not know #probData and
$probPath()(dataPath(t: ""))$,
and cannot compute the integrals over all possible data samples #data().
We cannot determine the marginalized vector field #probVecField even though we can choose
$probDataApprox(dataPath(t: "")|data())$
and
$probVecField(dataPath(t: "")|data())$.
Amazingly, the loss which regresses the machine learning model onto the conditional vector field
$
lossFunc & = expectation_(t,probData(data()),probPath()(dataPath(t: "")|data())) [ norm(probVecField(t, dataPath(t: "")|data()) - probVecFieldLearned(t, dataPath(t: "")))^2 ] \
& = expectation_(t,probData(data()),probLatent(latentVar)) [ norm(probVecField(t, #dataPath()) - probVecFieldLearned(t, dataPath()))^2 ] \
& = expectation_(t,probData(data()),probLatent(latentVar)) [ norm(dot(#dataPath()) - probVecFieldLearned(t, dataPath()))^2 ] \
$
has the same minima as the originally desired loss.
This means that we can simply train the machine learning model to predict the conditional vector field and it will transfer to predicting the marginalized vector field.

Sampling is done by sampling
$#latentVar ~ #probLatent$
and then integrate it using #probVecFieldLearned to time $t = 1$, resuting in the sample
$data() ~ #probDataApprox$.
This works because the two losses given have the same minima.
