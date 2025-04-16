# MarksManager

## Liste des routes

### Students
- POST /students
- PUT /students/:id
- GET /students
- GET /students/:id
- DELETE /students/:id

### Courses
- POST /courses
- PUT /courses/:id
- GET /courses
- GET /courses/:id
- DELETE /courses/:id

### Grades
- POST /grades
- PUT /grades/:id
- GET /grades/course/:id
- GET /grades/student/:id
- DELETE /grades/:id

### Reports
- GET /reports/course/:id
- GET /reports/student/:id
  
## Exemple Requêtes Postman

### Students - POST & POST

```
{
    "firstName": "John",
    "lastName": "Doe",
    "age": 19,
    "studentMail": "john.doe@test.fr",
    "courses": [
        {
            "name": "JAVA"
        }
    ]
}

OU

{
    "firstName": "John",
    "lastName": "Doe",
    "age": 19,
    "studentMail": "john.doe@test.fr",
    "courses": [
        {
            "id": "1"
        }
    ]
}

OU

{
    "firstName": "John",
    "lastName": "Doe",
    "age": 19,
    "studentMail": "john.doe@test.fr",
    "courses": []
}
```

### Course - POST

```
{
    "name": "JAVA",
    "description": "Cours de Java",
    "durationInHours": 15
}
```

### Course - PUT

```
{
    "name": "JAVA",
    "description": "Cours de Java",
    "durationInHours": 16,
    "students": [
        {
            "id": "1"
        }
    ]
}

OU

{
    "name": "JAVA",
    "description": "Cours de Java",
    "durationInHours": 16,
    "students": [
        {
            "firstName": "John",
            "studentMail": "john.doe@test.fr"
        }
    ]
}
```

### Grade - POST & PUT

```
{
    "grade": 15,
    "courseId": 1,
    "studentId": 1
}
```
