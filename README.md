# Viterbi Part-of-Speech Tagger
Part 1 of Project 1 for CS6320 Natural Language Processing 

## How to run
Please run using Java 8 or above, the default Java 7 on csgrads1 will not run it

run_program.sh must be in the same folder as bigram-spring-boot.jar

Use the bash script run_program.sh to run. It will only work with an absolute file path, no relative file paths.

<smoothing_enabled> = 1 to enable smoothing, 0 to disable it
```
 % bash ./run_program.sh <training_file_absolute_path> <smoothing_enabled>
```

**run_program.sh was written to run on csdgrads1, if you are not using csgrads1 you will need to update it with your machine's java location**