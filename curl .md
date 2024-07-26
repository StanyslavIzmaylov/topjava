$ curl -X GET http://localhost:8080/topjava/rest/meals
$ curl -X GET http://localhost:8080/topjava/rest/meals/100003
$ curl -X DELETE http://localhost:8080/topjava/rest/meals/100003
$ curl -d '{
"dateTime": "2024-07-25T14:15",
"description": "test",
"calories": 99
}' -H 'Content-Type: application/json;charset=UTF-8'  http://localhost:8080/topjava/rest/meals
$ curl -d '{
"dateTime": "2024-07-25T12:15",
"description": "update",
"calories": 99
}' -H 'Content-Type: application/json;charset=UTF-8' -X PUT http://localhost:8080/topjava/rest/meals/100003

$ curl -X
GET http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-29&startTime=00:0&endDate=2020-01-31&endTime=23:0


