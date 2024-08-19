# User Data

## Concept Notes

- "Patch Editor" and "Patch Sets" add/manage Patch list owned by the current user
- "Render Review" add/manage Reviews list owned by current user
- "CurretUser" singleton has method to save off current user data
- "MainController" fires off the CurrentUser save method when a new menu item is clicked
- No other controller needs to be concerned with when/how user data is saved

## DataSaver

- [] Improve names

```mermaid
    classDiagram
    IDataSaver <|.. UserSaver
    IDataSaver <|.. ReviewSaver
    IDataSaver <|.. PatchSaver
```

## User Patches

```mermaid
    classDiagram

    PatchEditorController --> ClickPatch
    PatchEditorController --> CurrentUser

    PatchSetController --> CurrentUser
    PatchSetController --o ViewPatch

    CurrentUser --> User
    User --o IPatch
        
    IPatch <|.. Patch

    class User {
        -patches: IPatch[]
        -reviews: IReview[]
        -othersFields...
        +otherMethods...()
    }

    class IPatch {
        +getColours(): Color[]
        +getHashes(): String[]
    }

    class UserDataAccess {
        +createTable()
        +create(userId, data: String)
        +retrieve(userId, data: String)
        +update(userId, data: String)
        +delete(userId, data: String)
    }
```

## Concerned classes and connections

```mermaid
    classDiagram
    
    IPatch o-- User
    IReview o-- User
    
    CurrentUser --> User

    IPatch <|.. Patch
    Patch <-- ViewPatch
    
    ViewPatch o-- PatchSetController
    ViewPatch <|-- ClickPatch

    User <-- PatchSetController
    User <-- PatchEditController

    ClickPatch <-- PatchEditController
    
class IReview {
  +serialize(): String
  +setResult(x,y,value)
  +getComment(): String
  +setComment(String)
}

class User {
  -patches: IPatch[]
  -reviews: IReview[]
  -othersFields...
  +otherMethods...()
}

class IPatch {
  +getColours(): Color[]
  +getHashes(): String[]
}


```
