### 1.  Overview

The Metro Token System is a Spring Boot-based application that manages the generation, validation, and usage of tokens for accessing a metro rail network. The system allows users to generate tokens for travel between stations, calculates fares based on distance traveled, and provides entry/exit access control based on the token's validity and journey status.


### 2.Functionality 

The Metro Token System makes it easy for people to use the metro. It does the following:

*   **Token Generation:** Creates digital tokens that users can use to ride the metro.
*   **Fare Calculation:** Calculates the price of a token depending on where you start and where you want to go.
*   **Entry/Exit Access:** Checks if a token is valid when someone enters or exits the metro station, making sure they have paid the right fare.
*   **Station and Device Management:** Provides information about all the metro stations and devices like entry/exit gates.

Essentially, this system handles everything from issuing a metro ticket to making sure people can get on and off the metro correctly.

### 3. Architecture

The system follows a layered architecture, comprising:

*   **Controllers:** Handle incoming HTTP requests and delegate business logic to the service layer.
*   **Services:** Contain the core business logic for token management, gate access, and station data retrieval.
*   **Repositories:** Interact with the database to persist and retrieve data.
*   **Entities:** Represent the database tables as Java objects (e.g., `TokenEntity`, `DeviceEntity`, `StationEntity`).
*   **Domain Objects:** Data transfer objects (DTOs) used to encapsulate data being passed between layers and exposed through the API (e.g., `TokenRequest`, `TokenResponse`, `GateResponse`).
*   **Mappers:** responsible for converting data

### 4. Components

#### 4.1. Controllers

*   **DeviceController:** Manages device-related operations, such as retrieving device types and listing devices at a specific station.
    *   `GET /metro/device/types/list`: Retrieves a list of available device types.
    *   `GET /metro/device/list`: Retrieves a list of devices associated with a particular station, specified by `stationId`.
*   **GateController:** Handles entry and exit requests using tokens and devices.
    *   `POST /metro/gate/getEntry`: Processes an entry request, validating the token and device, and granting entry if valid. Requires `tokenId` and `deviceId` as parameters.
    *   `POST /metro/gate/getExit`: Processes an exit request, validating the token and device, calculating the fare, and granting exit if valid. Requires `tokenId` and `deviceId` as parameters.
*   **StationController:** Provides station-related information.
    *   `GET /metro/station/list`: Retrieves a list of all stations.
    *   `GET /metro/station/toList`: Retrieves a list of destination stations based on a starting `stationId` and `routeNo`.
*   **TokenController:** Manages token generation and retrieval.
    *   `GET /metro/token/getAll`: Retrieves a list of all tokens.
    *   `GET /metro/token/getPrice`: Calculates the token price based on `fromStationId` and `toStationId`.
    *   `POST /metro/token/generate`: Generates a new token based on the provided `TokenRequest`.

#### 4.2. Services

*   **DeviceService:**
    *   `getDeviceTypes()`: Returns a list of available device types.
    *   `stationDevicesList(Integer stationId)`: Retrieves a list of devices for a specific station.
*   **GateService:**
    *   `getEntry(Integer tokenId, Integer deviceId)`: Processes entry requests, updating the token status and recording entry time. Validates device type (entry gate).
    *   `getExit(Integer tokenId, Integer deviceId)`: Processes exit requests, calculating the fare, updating the token status, and recording exit time. Validates device type (exit gate). It also checks for additional fares and updates the token accordingly.
*   **StationService:**
    *   `getDeviceStations()`: Returns a list of stations.
    *   `getToStations(Integer stationId, Integer routeNo)`: Returns a list of stations reachable from a starting station and route.
*   **TokenService:**
    *   `getAllTokens()`: Returns a list of all tokens.
    *   `getTokenPrice(Integer fromStationId, Integer toStationId)`: Calculates the token price.
    *   `generateToken(TokenRequest tokenRequest)`: Generates a new token and persists it in the database.

#### 4.3. Entities

*   `DeviceEntity`: Represents a metro device (e.g., entry gate, exit gate).
*   `RatesEntity`: Represents fare rates based on distance.
*   `StationEntity`: Represents a metro station.
*   `TokenEntity`: Represents a metro token.

#### 4.4. Domain Objects (Example)

*   `TokenRequest`: Represents the request payload for generating a new token (includes fromStationId, toStationId).
*   `TokenResponse`: Represents the response containing token details.
*   `GateResponse`: Represents the response from entry/exit gate requests (includes accessAllowed flag and message).
*   `DeviceResponse`: Represents the device details
*   `DeviceTypeResponse`: Represents type of device like entry/exit
*   `DeviceStationResponse`: Represents list of device stations
*   `MasterStationResponse`: Represents master stations

### 5. API Endpoints

| Method | Endpoint | Description | Request Body | Response Body |
| --- | --- | --- | --- | --- |
| GET | `/metro/device/types/list` | Retrieves a list of device types | None | List&lt;DeviceTypeResponse&gt; |
| GET | `/metro/device/list` | Retrieves a list of devices for a given station ID | `stationId` | List&lt;DeviceResponse&gt; |
| POST | `/metro/gate/getEntry` | Processes a metro entry request | `tokenId`, `deviceId` | GateResponse |
| POST | `/metro/gate/getExit` | Processes a metro exit request | `tokenId`, `deviceId` | GateResponse |
| GET | `/metro/station/list` | Retrieves a list of all metro stations | None | List&lt;DeviceStationResponse&gt; |
| GET | `/metro/station/toList` | Retrieves a list of destination stations, based on a `stationId` and `routeNo` | `stationId`, `routeNo` | List&lt;MasterStationResponse&gt; |
| GET | `/metro/token/getAll` | Retrieves a list of all tokens | None | List&lt;TokenResponse&gt; |
| GET | `/metro/token/getPrice` | Retrieves token price, based on `fromStationId` and `toStationId` | `fromStationId`, `toStationId` | TokenRequest |
| POST | `/metro/token/generate` | Generates a new metro token | TokenRequest | TokenResponse |

### 6. Database Schema (Conceptual)

*   **tokens:** `token_id`, `from_station_id`, `to_station_id`, `price`, `generated_at`, `entry_station_id`, `exit_station_id`, `entry_time`, `exit_time`, `token_status`, `travelled_km`, `exit_price`, `exit_price_paid`
*   **stations:** `station_id`, `station_name`, `route_no`, `root_distance`
*   **rates:** `rate_id`, `min_km`, `max_km`, `price`
*   **devices:** `device_id`, `station_id`, `device_type_id`, `device_no`, `device_in_use`

### 7. Error Handling

The system implements basic error handling within the `GateService` to ensure the process is smooth. The `isInvalidDevice()` validates if the device is not fit for the specific operation, and `getDeviceErrorMessage` provides an error message to the user.

### 8. Functional Requirements

*   **Token Generation:**
    *   Generate unique metro tokens based on the origin and destination stations.
    *   Calculate token prices based on the distance between stations using predefined fare rates.
    *   Store token details, including origin, destination, price, and generation timestamp.
*   **Station Management:**
    *   Retrieve a list of all metro stations.
    *   Retrieve a list of destination stations based on the origin station and route.
*   **Device Management:**
    *   Retrieve a list of available device types (entry/exit gates).
    *   Retrieve a list of devices associated with a specific station.
*   **Gate Access Control:**
    *   Validate tokens for entry and exit access.
    *   Record entry and exit timestamps.
    *   Calculate the distance traveled and any additional fare required upon exit.
    *   Implement token expiration logic (3-hour validity).
    *   Deny access if a user tries to enter without buying a token or exit without entering.
*   **Token Information:**
    *   Retrieve all token information.

### 9. Technical Design

*   **Technology Stack:**
    *   Java 17
    *   Spring Boot
    *   Spring Data JPA
    *   MySQL
    *   Maven
*   **Architecture:**
    *   RESTful API architecture.
    *   Model-View-Controller (MVC) pattern.
    *   Service-oriented architecture

### 10. Implementation Details

*   **Token Generation:**
    *   Generates a unique token number by concatenating station abbreviations, date, and time (Example).
    *   Stores the token in the tokens table.
*   **Gate Access Control:**
    *   Validates the token status and expiration time.
    *   Updates the token status and timestamps upon entry and exit.
    *   Calculates the distance traveled and any additional fare required.
*   **Fare Calculation:**
    *   Calculates the distance between stations using the `rootDistance` field.
    *   Retrieves the appropriate fare rate from the `rates` table.

### 11. Future Enhancements

*   Implement user authentication and authorization.
*   Add support for different token types (e.g., daily passes, monthly passes).
*   Integrate with a payment gateway for online token purchases.
*   Implement real-time monitoring of gate access.
*   Add proper exception handling and logging.
