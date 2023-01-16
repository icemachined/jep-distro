![Build and test](https://github.com/icemachined/jep-distro/workflows/Build%20and%20test/badge.svg)
.. image:: https://img.shields.io/pypi/pyversions/Jep.svg
    :target: https://pypi.python.org/pypi/jep

.. image:: https://img.shields.io/pypi/l/Jep.svg
    :target: https://pypi.python.org/pypi/jep

.. image:: https://img.shields.io/pypi/v/Jep.svg
    :target: https://pypi.python.org/pypi/jep

.. image:: https://img.shields.io/badge/docs-wiki-orange.svg
    :target: https://github.com/ninia/jep/wiki

.. image:: https://img.shields.io/badge/docs-javadoc-orange.svg
    :target: https://ninia.github.io/jep/javadoc

## Jep binaries distribution archive
This is the build for the binaries distribution package of [JEP](https://github.com/ninia/jep/) project.
Currently you can install jep binaries using ```pip install jep```, but this requires preper build tools for each of the platform because it is distributed in form of source files.
This distro simplifies jep usage. 
You can use this as a maven dependency. 
Unpack to your folder and add the following include paths to JepConifg.
1. Path to platform dependent library (Lunux: libjep.so, Windows: jep.dll, MacOS: libjep.jnilib)
2. Path to folder containing unpacked jep distro. 

## How to perform a release of new jep version
1. Change JEP_VERSION env constant to new JEP version
2. Push next tag to this git repo to trigger release build and publish.
3. Go to sonatype staging repository and push a maven central release.