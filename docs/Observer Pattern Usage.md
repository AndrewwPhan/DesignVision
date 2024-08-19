# Useage of observer pattern implemented as a service

- NOTE: This is currently a work in progress, needs cleaning up and made to directly reflect existing architecture
- Observed class extends Observable (eg CurrentUser)
- Class to act as observer implements IObserver
- observer overrides update method to deal with an updated state of observable notification
- NOTE: This needs to be ported over to plantuml so it can be rendered in GitHub (easiest place to use it from?)

## Observer Pattern (WIP)

``` mermaid
    classDiagram
    
    class Observable {
        +register()
        +unregister()
        +notifyObservers()
    }
    
    class Observer {
        +update()
    }
    
    class ConcreteObservable {
        +notifyObservers()
    }

    class ConcreteObserver {
        +update()
    }
    
    Observable --o Observer
    Observable <|-- ConcreteObservable
    Observer <|.. ConcreteObserver
```
