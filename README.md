# Snake

2-ulotteinen Snake-peli.

## Dokumentaatio

[Vaatimusmäärittely](documentation/requirements-specification.md)  
[Työaikakirjanpito](documentation/hours-record.md)  
[Muutosloki](documentation/changelog.md)  
[Arkkitehtuurikuvaus](documentation/architecture.md)

## Releaset

[Viikko 5](https://github.com/steeric1/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan ajamalla hakemistossa `Snake`
```
mvn test
```

Testikattavuusraportti luodaan komennolla
```
mvn test jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto `Snake/target/site/jacoco/index.html`

###  Suoritettavan jar-binäärin luominen

```
mvn package
```
Komento luo hakemistoon `Snake/target` jar-binäärin `Snake-1.0-SNAPSHOT.jar`

### Checkstyle

Tiedostoon [checkstyle.xml](Snake/checkstyle.xml) määritellyt koodin laadun testit suoritetaan komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
Jos koodin laadusta löytyy virheitä, ne voi lukea avaamalla selaimella tiedoston `Snake/target/site/checkstyle.html`
