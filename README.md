How to Run

To run the microservices for user administration please follow the next steps:

1. Open a couple of windows terminal or bash emulators
2. On one of them build the project by running:  'mvn clean package'
3. Wait for the test to run succesfully, then run: 'java -jar target/users-microservice-code-challenge-1.0-SNAPSHOT.jar registration'
4. Once step 3 finishes with the registration server, run in other terminal: 'java -jar target/users-microservice-code-challenge-1.0-SNAPSHOT.jar users'
6. Once USERS_SERVICE is registered, run on other terminal the following curl's:

#### Retrieving a single User####

curl -X GET \
  http://localhost:2222/enterprise/users/getUser/0 \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 99a8ddd0-d1de-4284-89ed-b8a2957236f1,1886f26f-f388-4d72-acce-f5be9e96bef6' \
  -H 'cache-control: no-cache'

#### Retrieving list of users (only Active) #####

curl -X GET \
  http://localhost:2222/enterprise/users/listUsers \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 99a8ddd0-d1de-4284-89ed-b8a2957236f1,fc7a1060-3869-43f4-9800-de30b3825aa7' \
  -H 'cache-control: no-cache'

#### Adding a User ####

curl -X POST \
  http://localhost:2222/enterprise/users/addUser \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 99a8ddd0-d1de-4284-89ed-b8a2957236f1,4f837078-7ea8-494c-ba13-34f555d1a1ee' \
  -H 'cache-control: no-cache' \
  -d '{
    "firstName": "Test",
    "lastName": "Add",
    "dateOfBirth": "2010-12-4"
}
'

#### Deleting a User (deactivating it) ######

curl -X DELETE \
  http://localhost:2222/enterprise/users/deleteUser/6 \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 99a8ddd0-d1de-4284-89ed-b8a2957236f1,21260915-77c3-4dd3-ad4f-653ad9151a4b' \
  -H 'cache-control: no-cache' \
  -d '{
    "firstName": "Test",
    "lastName": "Add",
    "dateOfBirth": "2010-12-4"
}
'

#### Updating a User #####

curl -X PUT \
  http://localhost:2222/enterprise/users/updateUser/6 \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 99a8ddd0-d1de-4284-89ed-b8a2957236f1,72f9a922-0e32-4166-ba6d-635cacee2a98' \
  -H 'cache-control: no-cache' \
  -d '{
    "firstName": "Test 2",
    "lastName": "Update 3",
    "dateOfBirth": "2010-12-4"
}
'