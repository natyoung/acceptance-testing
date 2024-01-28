#!/usr/bin/env bash

if [[ -z "${PACT_FOLDER}" ]]; then
  echo "PACT_FOLDER is unset"; exit 1;
else
  PACT_FOLDER="${PACT_FOLDER}"
fi

d_check() {
  command -v python3 >/dev/null 2>&1 || { echo >&2 "python3 was not found.  Aborting."; exit 1; }
  command -v pip3 >/dev/null 2>&1 || { echo >&2 "pip3 was not found.  Aborting."; exit 1; }
  command -v yarn >/dev/null 2>&1 || { echo >&2 "yarn was not found.  Aborting."; exit 1; }
  command -v wget >/dev/null 2>&1 || { echo >&2 "wget was not found.  Aborting."; exit 1; }
  command -v java >/dev/null 2>&1 || { echo >&2 "java was not found.  Aborting."; exit 1; }
  command -v ruby >/dev/null 2>&1 || { echo >&2 "ruby was not found.  Aborting."; exit 1; }
  command -v docker >/dev/null 2>&1 || { echo >&2 "docker is not running.  Aborting."; exit 1; }
}

setup()
{
  d_check
  docker pull pactfoundation/pact-stub-server
  cd ./acceptance
  python3 -m pip install --user virtualenv
  python3 -m venv env
  pip3 install -r requirements.txt
  playwright install
  cd ../web-ui
  yarn install
  cd ../web-api
  ./gradlew clean
  cd ../wallet
  bundle install
  cd ..
}

test_client()
{
  cd ./web-ui
  yarn audit && yarn lint && yarn test
  PORT=3000 yarn run dev &
  local client_pid=$!
  ../waitfor.sh http://localhost:3000 -t 20 -- echo "client started"
  ../waitfor.sh http://localhost:8080/wallets/ea4ceefe-cfe6-49e8-a36d-1a889c780bb4 -t 20 -- echo "stubs started"
  cd ../acceptance 
  pytest --browser firefox
  cd ..
  kill ${client_pid}
}

test_wallet()
{
  cd wallet
  bundle exec rake test
  APP_ENV=test PACT_DO_NOT_TRACK=true bundle exec rake pact:verify
  cd ..
}

test_certifier()
{
  cd certifier
  bundle exec rake test
  APP_ENV=test PACT_DO_NOT_TRACK=true bundle exec rake pact:verify
  cd ..
}

test_api()
{
  cd wallet
  bundle exec rackup -p 8081 &
  local wallet_pid=$!
  cd ../web-api
  ./gradlew clean
  ./gradlew test
  cd ..
  kill ${wallet_pid}
}

generate_pacts()
{
  cd web-ui
  yarn run test:cdc
  cd ../web-api
  ./gradlew clean test --tests "org.scrumfall.webapi.contract.providers.*"
  cd ..
}

run_pact_stubs()
{
  docker run --rm -t --name pact-stubs -p 8080:8080 -v "${PACT_FOLDER}:/app/pacts" pactfoundation/pact-stub-server -p 8080 -d pacts --cors &
}

test()
{
cat <<EOF

-----------------
generate_pacts
-----------------
EOF
  generate_pacts
cat <<EOF

-----------------
start pact-stubs
-----------------
EOF
  run_pact_stubs
cat <<EOF

-----------------
test_client
-----------------
EOF
  test_client
cat <<EOF

-----------------
test_api
-----------------
EOF
  test_api
cat <<EOF

-----------------
test_wallet
-----------------
EOF
  test_wallet
cat <<EOF

-----------------
test_certifier
-----------------
EOF
  test_certifier
cat <<EOF

-----------------
stop pact-stubs
-----------------
EOF
  docker stop pact-stubs
}

print_usage()
{
cat <<EOF

    Usage: PACT_FOLDER=<path> $0 [option]

    setup               setup
    generate_pacts      generate the contracts to PACT_FOLDER
    test                run all tests

EOF
}

payment_stub()
{
  cd mockserver
  docker-compose up
}

case $1 in
setup)
setup
;;
test)
test
;;
payment_stub)
payment_stub
;;
run_pact_stubs)
run_pact_stubs
;;
generate_pacts)
generate_pacts
;;
*)
print_usage
;;
esac
