# Story Points Wallet

## Dependencies

- Ruby 3.2.2

#### Contract test
```shell
APP_ENV=test PACT_DO_NOT_TRACK=true bundle exec rake pact:verify         
```

#### Wallet
```shell
bundle exec rackup -p 8081
```
