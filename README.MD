# IDEA EDU Course ...

Implemented in the Java Desktop Application Developer Track of hyperskill.org's JetBrain Academy.

Another Swing project to stay a bit in touch with layouts and graphical stuff.

## Technology / External Libraries

- POJO Java 20,
- javax.swing frontend, SwingWorker
- with Lombok annotation processors,
- Junit 5 with 
- Mockito (mockito-inline) testing.

## Repository Contents

The sources of main project tasks (5 stages) and unit, mockito testing.

## Program description

A Swing GUI version of a desktop calculator - not more not less :-)

Have fun!

## Project completion

Project was completed on 07.08.23

## Progress

02.08.23 Project started - Initial repo setup.

02.08.23 Stage 1 completed - Setup a swing Desktop Calculator UI using BoxLayout. Perform simple summing.

02.08.23 Stage 2 completed - Calculator performs 4 base operations, no decimal point button. EquationSolver class added
to model-package.

03.08.23 Stage 3 completed - Add decimal point, C and Del buttons. An equation label now shows the typed equation and
press on "=" solves and displays the result. Equation parsing and Dot before line operations is handled.

06.08.23 Stage 4 completed - Add syntax checks while typing for the growing equation and when solving. Add a 
EquationTextResolver class to handle this. Also validate the equation on "=" press, mark in RED if syntax fails 
or division by zero. Further resolve _abbreviated decimals_ (as `.2`) when operator or "=" is entered. 

07.08.23 Final stage 5 completed - Add smart parentheses handling (with just one button) as well as exponentiation and 
square root buttons. Equation Solver uses recursion and starts resolving inner parentheses. The operations are performed 
according to precedence from left to right using end-recursions and Regexp-matchers.