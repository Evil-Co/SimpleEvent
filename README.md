Simple Event Library [![Build Status](http://assets.evil-co.com/build/JSE-MASTER.png)](http://www.evil-co.com/ci/browse/JSE-MASTER)
====================
The Simple Event library for Java allows you to quickly integrate a functional
event system into any Java application.

It is as simple as extending the IEvent interface (and optionally the ICancellableEvent)
and creating an instance of EventManager.

Handlers can be created by extending IHandler or using the handler annotation (`@EventHandler`)
on a method which's first and only argument is the event type to listen for.

Please refer to the source code for more information on the different options in each
implementation.

Compiling
---------

You need to have Maven installed (http://maven.apache.org). Once installed,
simply run:

	mvn clean install

Maven will automatically download dependencies for you. Note: For that to work,
be sure to add Maven to your "PATH".

Contributing
------------

We happily accept contributions. The best way to do this is to fork Simple Event
on GitHub, add your changes, and then submit a pull request. We'll look at it,
make comments, and merge it into our master if everything works out.

By submitting code, you agree to license your code under the Apache 2.0 License.