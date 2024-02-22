# Acceptance Tests

```
User story -> AC -> Test Cases -> DSL -> Drivers -> System under test
```

- DSL: What
- Driver: How

### Installing

```
py -m pip install --user virtualenv
py -m venv env
source env/bin/activate
playwright install
```

### Run ATs

```
pytest
```

## User Story: View wallet balance

```
    As a Scrumfall practitioner
    I want to access my points wallet
    So that I can check the balance
```

#### Acceptance Criteria 1

```
    Given that I have a points wallet (User ID: ea4ceefe-cfe6-49e8-a36d-1a889c780bb4, Bal: 100)
    When I check the wallet
    Then I should know the current balance (100)
```

## User Story: Add to wallet balance

```
    As a Scrumfall practitioner 
    I want to add story points to my wallet
    So that I can buy a certificate
```

#### Acceptance Criteria 1

```
    Given that I have a wallet (User ID: ea4ceefe-cfe6-49e8-a36d-1a889c780bb4, Bal: 100)
    When I add an amount (900)
    Then the balance should be updated (1000)
```

## User Story: Buy a certificate

```
    As a Scrumfall practitioner
    I want to buy a Scrumfall Master certificate
    So that I can print it out
```

#### Acceptance Criteria 1

```
    Given that I have a user id (User ID: ea4ceefe-cfe6-49e8-a36d-1a889c780bb4)
    When I view the certification page
    Then I can see the certification price (100)
```

#### Acceptance Criteria 2

```
    Given that I have enough funds in my wallet (100)
    When I buy a new certification
    Then I can view the certificate
```

#### Acceptance Criteria 3 (TODO)

```
    Given that I have bought a certification (User ID: ea4ceefe-cfe6-49e8-a36d-1a889c780bb4, Bal: 100)
    When I check my points balance
    Then I should see the updated balance (0)
```
