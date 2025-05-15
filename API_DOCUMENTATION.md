# HATUMA Equipment Management System API Documentation

## Base URL
All API endpoints are prefixed with `/api`

## Authentication APIs

### Register User
- **Endpoint:** `/api/auth/register`
- **Method:** POST
- **Request Body:**
```json
{
    "email": "string",
    "password": "string",
    "firstName": "string",
    "lastName": "string",
    "fullName": "string",
    "department": "string",
    "phoneNumber": "string",
    "userType": "ADMIN" | "STAFF"
}
```
- **Response:**
```json
{
    "id": "long",
    "email": "string",
    "fullName": "string",
    "userType": "string",
    "department": "string",
    "phoneNumber": "string"
}
```

### Login
- **Endpoint:** `/api/auth/login`
- **Method:** POST
- **Request Body:**
```json
{
    "email": "string",
    "password": "string"
}
```
- **Response:**
```json
{
    "token": "string",
    "user": {
        "id": "long",
        "email": "string",
        "fullName": "string",
        "userType": "string"
    }
}
```

## User Management APIs

### Get All Users
- **Endpoint:** `/api/users`
- **Method:** GET
- **Response:**
```json
[
    {
        "id": "long",
        "email": "string",
        "fullName": "string",
        "firstName": "string",
        "lastName": "string",
        "department": "string",
        "phoneNumber": "string",
        "userType": "string",
        "isActive": "boolean"
    }
]
```

### Get User by ID
- **Endpoint:** `/api/users/{id}`
- **Method:** GET
- **Response:**
```json
{
    "id": "long",
    "email": "string",
    "fullName": "string",
    "firstName": "string",
    "lastName": "string",
    "department": "string",
    "phoneNumber": "string",
    "userType": "string",
    "isActive": "boolean"
}
```

### Update User
- **Endpoint:** `/api/users/{id}`
- **Method:** PUT
- **Request Body:**
```json
{
    "firstName": "string",
    "lastName": "string",
    "department": "string",
    "phoneNumber": "string"
}
```
- **Response:** Updated user object

### Delete User
- **Endpoint:** `/api/users/{id}`
- **Method:** DELETE
- **Response:** 204 No Content

## Equipment APIs

### Get All Equipment
- **Endpoint:** `/api/equipment`
- **Method:** GET
- **Response:**
```json
[
    {
        "id": "long",
        "name": "string",
        "type": "string",
        "description": "string",
        "status": "AVAILABLE" | "IN_USE" | "MAINTENANCE" | "OUT_OF_STOCK",
        "quantity": "integer",
        "location": "string"
    }
]
```

### Get Equipment by ID
- **Endpoint:** `/api/equipment/{id}`
- **Method:** GET
- **Response:**
```json
{
    "id": "long",
    "name": "string",
    "type": "string",
    "description": "string",
    "status": "string",
    "quantity": "integer",
    "location": "string"
}
```

### Create Equipment
- **Endpoint:** `/api/equipment`
- **Method:** POST
- **Request Body:**
```json
{
    "name": "string",
    "type": "string",
    "description": "string",
    "status": "AVAILABLE" | "IN_USE" | "MAINTENANCE" | "OUT_OF_STOCK",
    "quantity": "integer",
    "location": "string"
}
```
- **Response:** Created equipment object

### Update Equipment
- **Endpoint:** `/api/equipment/{id}`
- **Method:** PUT
- **Request Body:**
```json
{
    "name": "string",
    "type": "string",
    "description": "string",
    "status": "string",
    "quantity": "integer",
    "location": "string"
}
```
- **Response:** Updated equipment object

## Equipment Request APIs

### Get All Requests
- **Endpoint:** `/api/requests`
- **Method:** GET
- **Response:**
```json
[
    {
        "id": "long",
        "equipment": {
            "id": "long",
            "name": "string"
        },
        "requester": {
            "id": "long",
            "fullName": "string"
        },
        "purpose": "string",
        "duration": "integer",
        "status": "PENDING" | "APPROVED" | "REJECTED" | "RETURNED",
        "requestDate": "datetime",
        "approvalDate": "datetime",
        "returnDate": "datetime",
        "returnCondition": "GOOD" | "DAMAGED" | "NEEDS_MAINTENANCE"
    }
]
```

### Create Request
- **Endpoint:** `/api/requests`
- **Method:** POST
- **Request Body:**
```json
{
    "equipmentId": "long",
    "requesterId": "long",
    "purpose": "string",
    "duration": "integer"
}
```
- **Validation:**
  - `equipmentId`: Required, must exist
  - `requesterId`: Required, must exist
  - `purpose`: Required, cannot be blank
  - `duration`: Required, must be at least 1 hour
- **Response:** Created request object with initial status "PENDING"
- **Error Responses:**
  - 404: Equipment or requester not found
  - 400: Invalid request data or equipment not available
  - 500: Server error

### Update Request Status
- **Endpoint:** `/api/requests/{id}/status`
- **Method:** PUT
- **Request Body:**
```json
{
    "status": "APPROVED" | "REJECTED" | "RETURNED",
    "returnCondition": "string" // Required only when status is RETURNED
}
```
- **Success Response:** Updated request object
- **Error Response:** (400 Bad Request)
```json
{
    "message": "string", // Detailed error message explaining why the status change is not allowed
    "currentStatus": "string", // Current status of the request
    "requestedStatus": "string" // Status that was requested
}
```
- **Status Transition Rules:**
  - PENDING → APPROVED: Valid
  - PENDING → REJECTED: Valid
  - APPROVED → RETURNED: Valid
  - All other transitions: Invalid

### Get User's Requests
- **Endpoint:** `/api/requests/user/{userId}`
- **Method:** GET
- **Response:** Array of request objects for the specified user

### Get Pending Requests
- **Endpoint:** `/api/requests/pending`
- **Method:** GET
- **Response:** Array of pending request objects

## Equipment Return APIs

### Create Equipment Return
- **Endpoint:** `/api/returns`
- **Method:** POST
- **Request Body:**
```json
{
    "requestId": "long",
    "condition": "GOOD" | "DAMAGED" | "NEEDS_MAINTENANCE",
    "notes": "string"
}
```
- **Response:**
```json
{
    "id": "long",
    "request": {
        "id": "long",
        "equipment": {
            "id": "long",
            "name": "string"
        }
    },
    "returnDate": "datetime",
    "condition": "string",
    "notes": "string"
}
```

### Get Return by Request ID
- **Endpoint:** `/api/returns/request/{requestId}`
- **Method:** GET
- **Response:** Return object for the specified request

## Notes:
1. All endpoints require authentication except `/api/auth/login` and `/api/auth/register`
2. Dates are returned in ISO 8601 format
3. All IDs are of type Long
4. Equipment status changes are handled automatically when requests are approved/returned
5. Error responses follow the standard Spring Boot format:
```json
{
    "timestamp": "ISO-8601 datetime",
    "status": "HTTP status code",
    "error": "Error description",
    "message": "Detailed error message",
    "path": "Request path"
}
```
