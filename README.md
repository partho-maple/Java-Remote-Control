# Java Remote Control

Java Remote Control is a remote control program that can display a screen of another computer (via internet or network) on your screen. The program allows you to use your mouse and keyboard to control the other PC remotely. It means that you can work on a remote computer, as if you were sitting in front of it, right from your current location. The program allows you to work with different remote computers simultaneously, from anywhere in the world. The program is so fast and comfortable that you can even forget that you are working on a emote computer!


# Project Implementation Details

The whole software is made of java. We mainly used Java RMI (Remote Method Invocation) API. We also used Swing, SSL, Logging, and Thread APIs in this project. The heart of this software is RMI (Remote Method Invocation). The Admin and Host communicate each other through their Registry. A common RMI architecture has been used on this software.



# Basic Features

* Logon, logoff, restart and shutdown a remote computer remotely.
* Support of simultaneous multiple connections
* File transfer between computers.
* It has a large number of security features that can be used to restrict remote access to
the computer. Among them:
  - SSL encryption of all data streams.
  - Password protection.
  - Using Master key for the safe storage on your computer of the Access Passwords for
connection to the remote Hosts.
  - Logging feature for determination who has connected and when.
* 2 modes of working with a remote screen: View only and Full control.
* 2 modes of a remote screen displaying: Full screen and Scaled mode. Scaled mode allows
you to you see the remote screen in a window scaled to the defined size.
* High-resolution modes are supported.
* Java Remote Control allows you to select any color depth for displaying a remote
screen. Also the program can automatically detect optimal color depth for the best
performance.




# What makes this program different from others

* User-friendly interface.
* High performance.
* File transfer between computers.
* Low network load, due to optimized data compression.
* High safety and security. Authentication protocol and a encryption algorithm make the
program usage absolutely safe.
* Multiple and simultaneous connections. It makes Remote Desktop Control very efficient
for network management. With this program, the network administrator can efficiently
control different remote PCs simultaneously. Moreover, two or more administrators can
control one remote computer at the same time.
* It is bidirectional.




It has two modules.

1) MARC-Admin Module: It is the administrator part of this software. With the help of this part one can control any remote PC easily on which the MARC-Host Module is installed.

2) MARC-Host Module: It is needed to be installed on PC which will be controlled remotely by another Admin PC.











N.B.  It was one of my university project.




