# Wallet API Documentation

Welcome to the API documentation for the **Wallet** project. This document provides details about the available API endpoints and how to interact with them.

### Future improvements
- unit testing
- authentication
- add a user service
- splitting the application into separate microservices
- auto generated api documentation. 
- error handling is mainly done in service layer. might want to move that to controller layer for doc generation

## How to Run and Use the Project

Follow these steps to run and use the Wallet project:

1. **Clone the Repository**: Clone this repository to your local machine using the following command:
    ```sh
    git clone https://github.com/johanomg/wallet.git
    ```

2. **Navigate to Project Directory**: Change your working directory to the cloned repository:
    ```sh
    cd wallet-project
    ```

3. **Build and Run**: Build and run the project using Gradle:
    ```sh
    ./gradlew bootRun
    ```

4. **Access the API**: Once the application is running, you can access the API using the base URL mentioned earlier (`http://localhost:8080/v1/`).

5. **Test Endpoints**: Use API testing tools like Postman or cURL to test the various endpoints described in this documentation.

6. **Using IntelliJ IDEA**: Alternatively, you can open the project directly in IntelliJ IDEA:
    - Open IntelliJ IDEA.
    - Choose "Open" from the welcome screen or use `File > Open` from the menu.
    - Navigate to the `wallet-project` directory and select the project's `build.gradle` file.
    - The project will be imported, and you can run it by selecting the main class and clicking the "Run" button.

## Base URL

All API requests should be made to the following base URL: 
http://localhost:8080/v1/

## Endpoints

### Transactions

#### Get All Transactions

Get a list of all transactions.

    URL: /transactions
    Method: GET
    Response: List of Transaction objects

#### Get Transactions by Account Number

Get a list of transactions associated with a specific account.

    URL: /transactions/byAccountNumber/{accountNumber}
    Method: GET
    Path Parameters:
        {accountNumber}: The account number to retrieve transactions for.
    Response: List of Transaction objects

#### Make a Deposit

Make a deposit transaction.

    URL: /transactions/deposits
    Method: POST
    Request Parameters:
        accountNumber: The account number to deposit into.
        amount: The amount to deposit.
    Response: Transaction object representing the deposit transaction.

#### Make a Withdrawal

Make a withdrawal transaction.

    URL: /transactions/withdrawals
    Method: POST
    Request Parameters:
        accountNumber: The account number to withdraw from.
        amount: The amount to withdraw.
    Response: Transaction object representing the withdrawal transaction.

#### Make a Transfer

Make a transfer between two accounts.

    URL: /transactions/transfers
    Method: POST
    Request Parameters:
        sourceAccountNumber: The source account number.
        targetAccountNumber: The target account number.
        amount: The amount to transfer.
    Response: Transaction object representing the transfer transaction.

### Accounts
Get All Accounts

Get a list of all accounts.

    URL: /accounts
    Method: GET
    Response: List of Account objects

#### Get Account Balance

Get the balance of a specific account.

    URL: /accounts/{accountNumber}
    Method: GET
    Path Parameters:
        {accountNumber}: The account number to retrieve the balance for.
    Response: The account balance.

#### Create an Account

Create a new account.

    URL: /accounts
    Method: POST
    Request Parameters:
        accountNumber: The account number for the new account.
        balance: The initial balance for the account.
    Response: Account object representing the newly created account.

## Error Handling

In case of errors, the API responds with appropriate HTTP status codes and error messages. Common error codes include:

    400 Bad Request: Invalid request or parameters.
    404 Not Found: Resource not found.
    500 Internal Server Error: Server error.

Please refer to the respective endpoint descriptions for more details on possible error scenarios.