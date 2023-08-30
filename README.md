# Note microservice
**Note micro-service is an API that manages the patient notes from the Clinic.
Thanks to this API, you can do create, read, update  and delete patient notes.**

## Technical Stack
Note micro-service is built with the followings technologies :

- Java Jdk 17, spring boot 3.0.1
- MongoDB for the database (Nosql)
- Maven
- Fein (cloud) in order to connect this microservice with others.


# Setting up the microservice
To make this Api work correctly, you must follow the following steps :
- Create a MongoDB database named `clinic` for example 
- In the new MongoDB database, create a collection named : `notes`

That's all ! You can now add notes.

# API Specifications
**Microservice url is configured to be accessed on `localhost:9090/note/`**. <br>
Knowing that a micro-sercvice is an application that can work separatly without any other support. 
That is to say, note micro-service can be started and work fine with or without patient micro-service. However
the purpose behind note micro-service, is to work in harmony with patient micro-service. 

## **GET `/note/patient/{patientId}`**
Get every notes related to a given patient (recognizable by its ID). Response is composed of multiple notes.

### **Successful response example**
**URL : `localhost:9090/note/patient/1**
```json
[
    {
        "id": "6372910a44d5562320acad2e",
        "date": "2022-11-14T19:03:38.096+00:00",
        "patientId": "1",
        "content": "Patient has overwieght"
    },
    {
        "id": "637290f244d5562320acad2d",
        "date": "2022-11-14T19:03:14.847+00:00",
        "patientId": "1",
        "content": "Patient declares : i am a smoker"
    }
]
```

### **404 Not Found**
```json
{
    "time": "2022-11-15T17:54:39.082+00:00",
    "status": "NOT_FOUND",
    "message": "Patient with given ID was not found"
}
```

## **GET `/note/{Id}`**
This route gets you a single note, based on a note ID.

### **Successful response example**
**URL : `localhost:9090/note/6372910a44d`**
```json
{
    "id": "6372910a44d",
    "date": "2022-11-14T19:03:38.096+00:00",
    "patientId": "1",
    "content": "Patient has anoverweight"
}
```

## **POST `/note/`**
This route permits you to create a note for a patient. Patient must exist in order to add notes.

### **Successful content example**
**URL : `localhost:9090/note`**
```json
{
  "patientId": "1",
  "content": "Patient declares: i am a smoker"
}
```

### **400 Bad Request**
```json
{
    "time": "2022-11-15T17:56:33.676+00:00",
    "status": "BAD_REQUEST",
    "message": " error: The following field must be filled"
}
```


## **PUT `/note/update/{Id}`**
This route updates a note based on its ID. Patient must exist in order to update the note, and the note must be linked to the given patientID. <br>


### **Successful content example**
**URL : `localhost:9090/note/update/6372910a44`**
```json
{
  "patientId": "1",
  "content": "Patient has an overwieght"
}
```

### **400 Bad Request**
```json
{
    "time": "2022-11-15T17:56:33.676+00:00",
    "status": "BAD_REQUEST",
    "message": "error : the file conetnt must be filled"
}
```

### **404 Not Found**
```json
{
    "time": "2022-11-15T17:57:02.045+00:00",
    "status": "NOT_FOUND",
    "message": "Patient with given ID was not found"
}
```

## **DELETE `/notes/{noteId}`**
This route delete a single note based on its ID. If the operation is successful, a boolean set to true is returned.

### **Successful response example**
**URL : `localhost:9002/api/notes/637290f244d5562320acad2d`**
```json
true
```

### **404 Not Found**
```json
{
    "time": "2022-11-15T17:59:38.803+00:00",
    "status": "NOT_FOUND",
    "message": "Note with given ObjectID was not found"
}
```

## **DELETE `/note/{Id}/`**
This route deletes a note by it's id

### **Successful response example**
**URL : `localhost:9090/note/RD11971`**
```json
  message : la note est supprimée
```

### **404 Not Found**
```json
{
    "time": "2022-11-15T18:01:32.798+00:00",
    "status": "NOT_FOUND",
    "message": "note id was not found"
}
```

## **DELETE `/note/patient/{patientId}/`**
This route deletes a note based on a patient id. the notes attached to a patient will be deleted. 
Patient must exist in order for this route to work properly

### **Successful response example**
**URL : `localhost:9090/patient/1`**
```json
  message : la note du patient {1} est supprimer"
```

## Note test-jacoco

![test notes](https://github.com/HamzaBenalia/Note-Micro-Service/assets/99022185/93b55f31-ade0-4bbd-9aa5-bc7b02825cee)



