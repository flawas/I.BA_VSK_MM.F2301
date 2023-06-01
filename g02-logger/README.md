# Logger

Implementation des Loggers der Gruppe g02 im Modul VSK FS23.

### Buildstatus

* [![Build Status](https://jenkins-vsk.el.eee.intern/jenkins/buildStatus/icon?job=g02-logger)](https://jenkins-vsk.el.eee.intern/jenkins/job/g02-logger/)

> Hinweis: Buildstatus nur innerhalb HSLU-Netz (oder per VPN) sichtbar!

### Docker Image Release (latest)

```bash
mvn -pl logger-server -Ddocker.tag=latest jib:build
```
