# Acceptance testing with Playwright, Pact, Webmock & Mockserver

```bash
PACT_FOLDER=<absolute path> ./go.sh
```

## Project structure

    go.sh                        # Scripts to run the tests
    ├── acceptance               # Acceptance Tests with Playwright/Python
    │   ├── test_cases           # Test cases derived from AC's
    │   ├── dsl                  # What
    │   ├── drivers              # How
    ├── certifier                # Ruby/Rails app
    ├── mockserver               # 3rd party API stub
    ├── wallet                   # Ruby/Sinatra app
    ├── web-api                  # Java BFF
    ├── web-ui                   # React UI
    └── ...

## Dependencies

- python3
- pip3
- yarn
- wget
- java
- ruby
- docker
- docker-compose

## Env vars

### PACT_FOLDER
The directory to read and write pact files.
