# mathtree

# Vision

Mathtree speeds up problem-solving by revealing what mathematical tools that rely on a given set of assumptions.
People who can describe the assumptions of their problem can quickly find existing and relevant mathematical tools to apply.
They avoid rederiving the theory when the mathematical tool Mathtree finds is the solution.
When it is not the complete solution, Mathtree helps people define the gap between existing mathematical tools and their problems' solutions, leading to refined research questions.
By eliminating duplicated efforts of people in different fields, and presenting a unified perspective on gaps in existing research, people can effectively build on each others' research and get clarity about the most important questions to ask to solve the global community's problems.

Mathtree works by documenting assumptions and linking them to the mathematical tools that rely on them.
It is a tree-like network where the most fundamental assumptions will be connected to most mathematical tools, and more uncommon assumptions will be linked to a few.
How an assumption that is described in different fields may be different, but these can be linked as equivalent assumptions.
Each assumption is an equivalence class of assumption descriptions, which allows Mathtree to reveal mathematical tools developed in different fields that rely on the same assumptions.
In some cases, these mathematical tools will themselves be equivalent.

To develop Mathtree, we document instances of mathematical tools and their applications by the assumptions used.
These instances are leaves, and the assumptions are branches that connect the leaves.
Has the number of assumptions documented grows, eventually different fields will become linked.

## Clojure

FIXME: explanation

Run the project directly, via `:exec-fn`:

    $ clojure -X:run-x
    Hello, Clojure!

Run the project, overriding the name to be greeted:

    $ clojure -X:run-x :name '"Someone"'
    Hello, Someone!

Run the project directly, via `:main-opts` (`-m mathtree.mathtree`):

    $ clojure -M:run-m
    Hello, World!

Run the project, overriding the name to be greeted:

    $ clojure -M:run-m Via-Main
    Hello, Via-Main!

Run the project's tests (they'll fail until you edit them):

    $ clojure -T:build test

Run the project's CI pipeline and build an uberjar (this will fail until you edit the tests to pass):

    $ clojure -T:build ci

This will produce an updated `pom.xml` file with synchronized dependencies inside the `META-INF`
directory inside `target/classes` and the uberjar in `target`. You can update the version (and SCM tag)
information in generated `pom.xml` by updating `build.clj`.

If you don't want the `pom.xml` file in your project, you can remove it. The `ci` task will
still generate a minimal `pom.xml` as part of the `uber` task, unless you remove `version`
from `build.clj`.

Run that uberjar:

    $ java -jar target/net.clojars.mathtree/mathtree-0.1.0-SNAPSHOT.jar
