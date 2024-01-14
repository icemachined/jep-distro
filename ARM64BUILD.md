# How to build jep binaries for arm64 architecture
## Prerequisites
You need to have on your Mac with M processors:

1. XCode (installed from AppStore for example)
2. brew - command line package manager ( see [instruction](https://docs.brew.sh/Installation) )
3. pyenv - python distro manager (`brew install pyenv`)
4. JDK (better latest LTS version)

## Build steps for version of python 3.x
1. `pyenv install 3.x`
2. `export PATH=~/.pyenv/versions/3.x.x/bin:$PATH`
3. `pip3 install setuptools wheel numpy`
4. `pip3 install jep==<JEP_VERSION> --no-build-isolation --log jep.log`
   here we switch off isolation because for jep to find includes in installed numpy 
   it needs to be able to find it. And this also requires 
   manual installation setuptools and wheel on previous step 
5. `grep "numpy include found at" jep.log`
   Check that numpy support was really switched on.
   Note that --log option only appends data to the file without cleaning
6. `cp ~/.pyenv/versions/3.x.x/lib/python3.x/site-packages/jep/libjep.jnilib /Users/<name>/IdeaProjects/jep-distro/jep/arm64/<JEP_VERSION>/cp3.x/libjep.arm64.jnilib`

- Perform all above steps for each versions of python where 3.x is in (3.6, 3.7, 3.8, 3.9, 3.10, 3.11, 3.12)

- After you build all binaries, add them to git version control and push to GitHub repo.
