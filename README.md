# Diabetor

(yes the name sucks)

## What is diabetor ?

diabetor implements the protocol defined by the hospital of Aix-en-Provence to manage type 1 diabetes.

diabetor aims to automate the following processes of the protocol:

- punctual adaptation of the quick insulin dose to be taken at each meal, based on the glycemia level at the moment of the meal :white_check_mark:
- adapts the base dose of quick insulin for each meal, based on 3 days of observation :white_check_mark:
- adapts the slow insulin dose to be taken every day (based on 3 days of observation) :white_check_mark:

## How to build

Java 17 is required. I suggest to install the JDK with [SdkMan](https://sdkman.io/).

```bash
./mvnw clean package
```

## How to run diabetor

```bash
java -jar target/diabetor*fat.jar <subcommand>
```

where available subcommands are:
* punctual
* adapt-dose-quick
* adapt-dose-basal

or use the provided scripts:
```bash
./run-punctual.sh
```

```bash
./run-adapt-quick.sh
```

```bash
./run-adapt-basal.sh
```

## Subcommand details

## expected decimal format

All subcommands need glycemia measures. Glycemia measures are expected as decimal numbers. Please use dot as decimal separator.

Valid examples:
* 1.0
* 0.6
* 2.1
* 1

### subcommand: punctual
***punctual*** subcommand will let you know if you need to punctually adapt the quick insulin dose for the meal you're about to eat.

punctual subcommand expects you to enter the glycemia measure you just took before your meal.

### subcommand: adapt-dose-quick

***adapt-dose-quick*** subcommand will let you know if you need to change **your usual quick insulin dose** for a given meal.

This subcommand expects you to enter a list of glycemia measures separated by a coma. At least 3 measures are needed.

For example, if you measured that you glycemia level was 1.2 then 2.4 and finally 0.8 four hours after your three last breakfasts, enter: 1.2,2.4,0.8

### subcommand: adapt-dose-basal

***adapt-dose-basal*** subcommand will let you know if you need to change your **usual basal insulin dose**.

To compute this adaptation, 3 *pairs* of glycemia measures are required. 

For example, if you measured that you glycemia level was 1.2 then 2.4 and finally 0.8 four hours after your last breakfasts, enter: 1.2,2.4,0.8

You will be prompted to enter a glycemia measure pair 3 times.

## How to "install" diabetor :construction:

TODO

```bash
rm -f $HOME/bin/diabetor
./mvnw package
./scripts/jpackage.sh :construction:
ln -s $(pwd)/target/jpackage/diabetor/bin/diabetor $HOME/bin/diabetor
```

### Reference Documentation
For further reference, please consider the following sections:

#### Technical doc

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Official JUnit5 documentation](https://junit.org/junit5/docs/current/user-guide/)
* [Official Mockito documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
