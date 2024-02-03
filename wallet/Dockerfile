FROM ruby:3.3.0-alpine3.18

###########
## SETUP ##
###########
RUN apk update && \
    apk --no-cache add bash build-base curl ruby-dev sqlite-dev sqlite tzdata

RUN addgroup -S app && adduser -S -G app ruby

###########
## BUILD ##
###########
ARG BUNDLE_DEPLOYMENT=true
ENV BUNDLE_DEPLOYMENT ${BUNDLE_DEPLOYMENT}

ARG BUNDLE_WITHOUT=test
ENV BUNDLE_WITHOUT ${BUNDLE_WITHOUT}

ARG PORT=8081
ENV PORT ${PORT}

ARG HOST_PAYMENTS="http://localhost:9090"
ENV HOST_PAYMENTS ${HOST_PAYMENTS}

ARG APP_HOME=/app
ENV APP_HOME ${APP_HOME}
WORKDIR ${APP_HOME}

ADD . $APP_HOME
RUN bundle install
