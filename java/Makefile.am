ModulationAssemblyController_jar_CLASSPATH = $(CLASSPATH_SOFTPKG_DEP)$(OSSIE_HOME)/lib/CFInterfaces.jar:$(OSSIE_HOME)/lib/log4j-1.2.15.jar:$(OSSIE_HOME)/lib/ossie.jar

ModulationAssemblyController.jar: $(ModulationAssemblyController_jar_SOURCES)
	mkdir -p bin
	$(JAVAC) -cp $(ModulationAssemblyController_jar_CLASSPATH) -d bin $(ModulationAssemblyController_jar_SOURCES)
	$(JAR) cf ./ModulationAssemblyController.jar -C bin .

clean-local:
	rm -rf bin

distclean-local:
	rm -f config.*
	rm -rf autom4te.cache
	rm -f acinclude.m4
	rm -f aclocal.m4
	rm -f configure
	rm -f depcomp
	rm -f install-sh
	rm -f ltmain.sh
	rm -f Makefile.in
	rm -f missing
	rm -rf .deps

ossieName = ModulationAssemblyController
bindir = $(prefix)/dom/components/ModulationAssemblyController/java/
bin_PROGRAMS = ModulationAssemblyController.jar
ModulationAssemblyController_jar_SOURCES := $(shell find ./src -name "*.java")

xmldir = $(prefix)/dom/components/ModulationAssemblyController/
dist_xml_DATA = ../ModulationAssemblyController.scd.xml ../ModulationAssemblyController.prf.xml ../ModulationAssemblyController.spd.xml

domdir = $(prefix)/dom/components/ModulationAssemblyController/java/
dist_dom_SCRIPTS = startJava.sh
