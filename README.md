# Snake

2-ulotteinen Snake-peli.

## Dokumentaatio

[Vaatimusmäärittely](documentation/requirements-specification.md)  
[Työaikakirjanpito](documentation/hours-record.md)  
[Muutosloki](documentation/changelog.md)  
[Arkkitehtuurikuvaus](documentation/architecture.md)  
[Käyttöohje](documentation/manual.md)  
[Testausdokumentti](documentation/testing.md)

## Releaset

[Viikko 5](https://github.com/steeric1/ot-harjoitustyo/releases/tag/viikko5)  
[Viikko 6](https://github.com/steeric1/ot-harjoitustyo/releases/tag/viikko6)  
[Loppupalautus](https://github.com/steeric1/ot-harjoitustyo/releases/tag/loppupalautus)

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

Tiedostoon [checkstyle.xml](Snake/checkstyle.xml) määritellyt koodin laadun testit suoritetaan ajamalla hakemistossa `Snake` komento
```
mvn jxr:jxr checkstyle:checkstyle
```
Jos koodin laadusta löytyy virheitä, ne voi lukea avaamalla selaimella tiedoston `Snake/target/site/checkstyle.html`

### JavaDocs

Projektin JavaDocs-sivun voi generoida sijaintiin `Snake/target/site/apidocs` ajamalla hakemistossa `Snake` komento
```
mvn javadoc:javadoc
```
Dokumentaation saa auki avaamalla em. kansiosta sivun `index.html`
