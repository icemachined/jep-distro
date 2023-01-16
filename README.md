![Build and test](https://github.com/icemachined/jep-distro/workflows/Build%20and%20test/badge.svg)
[![](https://img.shields.io/pypi/pyversions/Jep.svg)](https://pypi.python.org/pypi/jep)
[![](https://img.shields.io/pypi/l/Jep.svg)](http://zlib.net/zlib_license.html)
[![](https://img.shields.io/badge/docs-wiki-orange.svg)](https://github.com/ninia/jep/wiki)
[![](https://img.shields.io/badge/docs-javadoc-orange.svg)](https://ninia.github.io/jep/javadoc)

## Jep binaries distribution archive
This is the build for the binaries distribution package of [JEP](https://github.com/ninia/jep/) project.
Currently you can install jep binaries using ```pip install jep```, but this requires preper build tools for each of the platform because it is distributed in form of source files.
This distro simplifies jep usage. 
You can use this as a maven dependency. 
Unpack to your folder and add the following include paths to JepConifg.
1. Path to platform dependent library (Lunux: libjep.so, Windows: jep.dll, MacOS: libjep.jnilib)
2. Path to folder containing unpacked jep distro.

See [jep-distro-example](https://github.com/icemachined/jep-distro-example) project

## How to perform a release of new jep version
1. Change JEP_VERSION env constant and version in build.gradle.kts to new JEP version
2. Push next tag to this git repo to trigger release build and publish.
3. Go to sonatype staging repository and push a maven central release.