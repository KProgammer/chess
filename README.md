# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

## Phase 2 Sequence Diagram
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAHZM9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiAPRZiKikcpkAtthgqjCQMFBRMZlQmHnmMAC0AHzklDRQAFwwANoACgDyZAAqALowWT4GUAA6aADeAEQzlMFFKMvdyzDLADR7uOrJ0Bzbuwd7KEXASAgXewC+mMJdLe1snNw9MCtrUA2Wx2e0Oy2OqlOUHOIKuyxudwesJeXy4lA+HREKl61WisUoAApIni6pEAI4+NRgACUr06ogxsnkShU6l69hQYAAqrMCQCgbSmYplGpym0jDpemQAKIAGWlcFGMH5wE2MAAZt4isrZpgcqN0uVUuw0eUkOqKqlKIYYjBgDA9BwqigKVSYKofCBUnbymgIMxsD4wAQHDAkEl7Sq1WBjcx1RAEAgIMlyjHDFAfNwNEKWaKWuK3qJeo7na7YnSsYZmuLUT9esTapRIh6EFha+jq+03pReos9pt1MAOds9tKoN5fiXqhTYrsXt3YJ34MhzL0ACxOJwLFYD1RD4Gj8fQYvAJ3TymzmAvfWGmDG75qMMWtM69aqm3lYAIaqnsLyADW6CYDmIrqPmXb0tidrfigv4VMAgFoBWVAMku7a-A2+JQM2mZtiaPzgZiXS9v2or7iOyxjhOvRfj+HB-gh6BzshHbio0GDrk4ADM26kYOw69JRR6-LRsH0fBiHMTkAByiSGBAz5WjAlATka+gwBAyg+OOKBOqoEDOiS6IvgCepZAahhRh+MB+nGvicGGSQvlo8h6AY7oGc52huemnLaWgvqZggdoObijZQKmSnYAgwCWLQIGsmKBaQSgvRoEFyGoe0CWir0IA-pkPKElZhwQlCHCHAi9yCtouZgeKxi9AAktJMrhEqJUwGVZyVbc9wwC1oxDK+9SFiojK1aBqh5QVKAKEGqR8rMArAZNiUYo1A2tdK7UjUChzAAtowQIhW1DXaC2rcyU2EWNqUXTGx2IZl41ofhPYRDUWE4a2mDobdnQff8y3vrsgmgnsh2PSdTEwODyzzoDi5sSuHEwAATJuvGrCDmwXHCUOpE9TE7Mx6AcKYXi+P4ATQOwHIwLKEDREkARpBk2TmVa7nIGgj54IYJmzMpGCUOGjjhpalm44YJpdfokJnDADSo8wyXUB9gwjBMUwGOoCRoNjVn42C3XQo8iMa4unzvb8fY42+eOk6bCvlfjV5-bbGJ3b0SYswSTMs+SF40i9VbZWtuUwBy3K8lZNXXetDWSuQcoKh1MsalqI3Kzl9XqyhUFWWZTWKdLjuGCgAAetTlMkhjJPcwVpk5VgVAZ6rhk6aZILAZscC4V3Ckn7SbTK8qKvLJxK5qEDagCys5GQ4bjS+-dhmaaAgNA1TgDA9f7030dIDQUvai+hPQEgABe8SJO3iGD3nSUQZWaUZXdhHob0gfhj9eEPmRq-Yifw+J7gEoeaiypggLSvtfXSuwPYLkIuxMAnEACM2NdzkUElRY80DL5QBvggj2ORS5SynoraEG8YDbx0nvA+jdEzH1Pmmc+SlCE3zvk5GGaAn6R3zsAosD0ia8LDl-W2P9mZ-zULhT2gCAZWxIg7QEoNnaQyOrw92lt3hLlQb0TGW4lgqKBBRQmxM0DaLMJwSm3g-CBC8CgdAjNma+GYGzdImQVb5DVsAzW0g06jGlJMLIetVAGwWOYsRyCaySJERY+RaJrZEWEX7NxAdXFBmDlSWkn9xTP16BwFA3BMjzRjASKJiEE7DzzMnKUadJ6VOcbPeeuol4r0FkpSAp1kjqVsiIuBul94NyPtUMA-kQoENgUQ+BToVLQH4YnWpBdhHpUTOIt6gCpH2Dcf-RJBElwLmUdgiBQkoE+BgTGQZ5wYBIKRig1WGCsFkVOXg34FzOGzKklkchL5unOI4BAR81daiHBbkYYpnIbTMDluCsZEz2DKRKGAMIyBYiLJqYIlJUEmlIWfhtFOAT5RBPibwrOc8RpDzqi-bF901kIA2bErZLidlZNkb9f6hyka9h0axdo+iMZYyWC8cmtjqaBGqE6WUtQYAAHF3zlA8RzbxTQC6a1ldKHWWR7DvkiZo56MSbbMtxfsjsQioJorADqgcAdajyoHNk2IuSUoTSWWyGAlr7VqAqfq9A1TqUEvqRPJUuLyWtNoO0renTBZkt6b6f0AyZlDMYaMvygJJkfOmcQuZwkMUBq5W-GyH8XWbKST-O1Cq9mcrVXbMBODIH4MzVcpNNy7lWweT4p5Sw62vOEr0JtqRrnfOXlGihAIzSfizbM4Zh9mGWugsFa1ag803QLYXe6Jr8V1NTsG6O74mrSDDZSrdKyoJLoPVS1do8U7j3TvvTxKAiqqLVC049AiaU+3vRzJ9QJL0jwlEGu9OgYogH-D+98R7TInvNfdYDoAwMyz-bU69gHJ5LukhB19UH32KPXeyd8GHNhmRHavJSVAvwXjvCEQwOgnFJA9FNdUQUwjwsBLpQ4KbmGsYjB6mVCk90DkHnkplZbGYVodeygBSTcNAz2Eu1QI4+grHPdIEc6D0ZcTXGCdmhUZYmz2HB0D4GnbLDhOh98pMngwHGLyoBy5O2CqMcphVinnObAPWpjTWm9g6cfXp9RyxDMIYrvp5Y5nNiWesyKmxng7E00DFAaKhg4CzTlRBpVXjUHe25f0YYYwQlLr1dDRC2NwupVJm23RRrRP2zK-p3FKIvanvuvlWCmQl0EhS21lAXrHWhzyRHN101aGzS9T64rfqkP1RQzuu9oasNtKyCR6NOc402QTZ89jM6mHBW4xmy5g6W3KVzVNj9KV37rOE9Vus8BRvvirU1mtxyXkHjOY2g7Q7bm2Y7auGAG5MHduWCc17bz+0fZbcOjpY7hZrc206TjwVWtyFlgJlAK6k7NZor6vFOHt0tTakqMrkHFvLaltUfe6l7RThdCHQ4DHvSU6SAO65qObIQYR86cZ6bEXU7LMweZUB0fLJgyeM8NOqSMuux9LryPeuSdNXZo5oCgcvYoqDh0p5Swh0Qd9vRjy-tCvtsDtXfaNdi755D0dL4l1UfKAxxKTHExhCR5keHIyuNpqckpFTQusWfpU5L1gcSZeZDly2KTBynvK5U55zTlW+X2d+4Y0r+7VO9HU3H6LFNYvioCHFRMyYYAACkICSy9YEILKrSBR8GFyLVhWDsWOxtFWKlA4AJmgBcAA6iwJqQSsgACFZQKDgAAaTBDH9PXn4-JO-qSkrgOW9xSgO3pMUBu+9-70Pkfo+zOp9j2uD2nKRcwAAFal4cO+AkJfwxy7586ysrrMXDZjmUxauL-VXoA7Nxp2PifxVx0xyLXWUjVI3LlgDW36Thw43d120932zh2OwnCExLRExuxvzQDDzkWrT8VrRV34hB1N2Zwhy+xYjswFX+2eQIJN3OXB2zW+V+SUgXltGgNoUSBDEpFO1w2ERNQGxkHfV6HP1vyvxU0-3-U2nxx2iVB7z72lEH2HzH0ckqBU3-1gBLjLnJ1hzNy1ypDp09AZ0-FRwPUtDkCMCBVUDQAAHJ+ca5YgYDZ04CucIwkhecQ4kCFkuC11hFXCJcrsg9mUMCsCOVHtcDntqDcFTcfDLwZ8fs0YNwnMe1CCoEoiwAGCy5WdRI4JjRUxTguoYpl9VBDg9tEVMjxIAJnEBdfcztC1SiGIDVUCpdfhAj7t5ccCiJZN8DwEkj8FaiJJYZdcUYHMNweJAdjcIioFejyjLFSEfl0ibdsiHQ6N3RPQHdmMz8L8ts9s-R3DYA5ZEUbhShUUZVwx8RTwqjuCoJ6VA859mjNgHsFE10gYBj+V9dk9hVrFs8qZ7EAgvBYplxYJdjsBopCBuEUgH0q9fF2jfg+giVNVgkphjAyCMQ58QBuA8AFdstC1USATOs0TsJxcnUNlBtn88o8SCQxDkNv9YSSUvxF0FVPCZtqTpQF0RpsxcdGTAlmTaT590ANBQCVtgAgTkAQBQSFjaNnF7dRRHcEBnc8Tk1YDOcEUkgDiUVeN0VPCgCri-C59EAAT7jpNHi7ZnjE80Y3jFgs9MAgA
